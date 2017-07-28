package com.snoopyslist.DBservice;

import com.snoopyslist.DBmodels.PetDetails;
import com.snoopyslist.DBmodels.PetPost;
import com.snoopyslist.DBmodels.User;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by rui on 4/27/17.
 */
@Named
@RequestScoped
public class DBService
{
    @Resource
    private UserTransaction ut;

    @PersistenceContext(unitName = "snoopyslistPU")
    private EntityManager em;

    private String ApiKey = "b0tTeKaV0TBh3kc7nbH3JB82ueccgr9srCmGnwBnqdFtxRBU3iXMT6Pok2AAtJ2V";

    final int pageSize = 30;

    public DBService()
    {}

    public DBService(EntityManager em)
    {
        this.em = em;
    }

    //User DAO

    public boolean checkUserEmail(String email) {
        boolean checkStatus = false;

        TypedQuery<User> query = em.createNamedQuery("User.checkEmail", User.class);
        query.setParameter("email", email);

        List<User> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            checkStatus = true;
        }

        return checkStatus;
    }

    public User checkUserLogin (String email, String password) {
        boolean checkStatus = false;

        TypedQuery<User> query = em.createNamedQuery("User.checkUserLogin", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        List<User> resultList = query.getResultList();

        if(resultList.size() <= 0) {
            return null;
        }

        return resultList.get(0);
    }

    public List<User> getAllUsers()
    {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public User getUserById( int id )
    {
        System.out.print("ID: "+id);
        TypedQuery<User> query = em.createNamedQuery("User.findById", User.class);
        query.setParameter("id", id);
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public boolean createUser(JsonObject jsonObj) throws NullPointerException
    {
        boolean result = false;
        try
        {
            User user = new User();
            user.setName(jsonObj.getString("firstName"));
            user.setLastName(jsonObj.getString("lastName"));
            user.setEmail(jsonObj.getString("email"));
            user.setPassword(jsonObj.getString("password"));
            user.setPhoneNumber(jsonObj.getString("phoneNumber"));

            //check if website contain in json
            if(jsonObj.containsKey("website"))
                user.setWebsite(jsonObj.getString("website"));

            //assign enum for userType
            if(jsonObj.getInt("userType") == 0)
                user.setType(User.Type.PERSON);
            else
                user.setType(User.Type.SHELTER);

            ut.begin();
            em.merge(user);
            em.flush();
            result = true;
            ut.commit();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return result;
    }

    public void updateUserInfoById(int id , JsonObject jsonObj) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException, NotSupportedException {

        User updateUser = getUserById(id);
        updateUser.setName(jsonObj.getString("name"));
        updateUser.setLastName(jsonObj.getString("lastName"));
        updateUser.setEmail(jsonObj.getString("email"));
        updateUser.setPassword(jsonObj.getString("password"));
        updateUser.setPhoneNumber(jsonObj.getString("email"));
        updateUser.setWebsite(jsonObj.getString("website"));
        if(jsonObj.getInt("userType") == 0)
            updateUser.setType(User.Type.PERSON);
        updateUser.setType(User.Type.SHELTER);
        ut.begin();
        em.merge(updateUser);
        em.flush();
        ut.commit();
    }

    public void deleteUserById(int id ) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException, NotSupportedException {
        User userToRemove = getUserById(id);
        ut.begin();
        for(PetPost petPost : userToRemove.getPosts())
            em.remove(petPost);
        em.remove(userToRemove);
        em.flush();
        ut.commit();
    }

    //petPost DAO

    public List<PetPost> searchPetPost (JsonObject jsonObject) {

        List<PetPost> resultList = new ArrayList<>();

        JsonObject jsonResult = null;

        String zipCode = jsonObject.getString("zipCode"),
                distance = jsonObject.getString("distance");

        try {
            //send request to zipcodeapi.com API for JSON zip code Radius
            Client client = ClientBuilder.newClient();
            jsonResult = client.target("https://www.zipcodeapi.com/rest/" + ApiKey + "/radius.json/" + zipCode + "/" + distance + "/mile")
                    .request(MediaType.APPLICATION_JSON)
                    .get(JsonObject.class);

            client.close();

            List<String> zipCodeRadius = new ArrayList<String>();;

            //loop each zip Code radius and put into list of Zipcodes
            for (JsonValue zip: jsonResult.getJsonArray("zip_codes")) {
                JsonObject radiusJsonZipCode = (JsonObject) zip;

                String radiusZipCode = radiusJsonZipCode.getString("zip_code");

                zipCodeRadius.add(radiusZipCode);
            }

            String sqlQuery = "SELECT p FROM PetPost p WHERE p.zipCode IN( :zipCode )";
            Set<String> keysQuery = new HashSet<String>();;
            Set keys = jsonObject.keySet();

            Iterator a = keys.iterator();
            while(a.hasNext()) {
                //dynamtic key and value
                String tempKey = (String)a.next();
                String tempValue = jsonObject.getString(tempKey);

                //add query if value is not equal ""
                if(!tempValue.equals("") && !tempKey.equals("distance") && !tempKey.equals("pageNumber")) {
                    keysQuery.add(tempKey);
                    switch (tempKey) {
                        case "animalType":
                            sqlQuery += " AND p.animalType = :animalType";
                            break;

                        case "animalName":
                            sqlQuery += " AND p.details.animalName = :animalName";
                            break;

                        case "age":
                            sqlQuery += " AND p.details.age = :age";
                            break;

                        case "gender":
                            sqlQuery += " AND p.details.gender = :gender";
                            break;

                        case "size":
                            sqlQuery += " AND p.details.size = :size";
                            break;
                    }
                }
            }

            //create Query
            Query query = em.createQuery(sqlQuery);

            //loop each non-empty key value to query
            Iterator iter = keysQuery.iterator();
            while (iter.hasNext()) {
                //get key
                String tempKey = (String) iter.next();

                //convert age to integer and add to query
                if (tempKey.equals("age"))
                    query.setParameter(tempKey, Integer.valueOf(jsonObject.getString(tempKey)));

                //get zip code radius
                else if (tempKey.equals("zipCode")){
                    query.setParameter(tempKey, zipCodeRadius);
                }

                //add other key value
                else
                    query.setParameter(tempKey, jsonObject.getString(tempKey));
            }

            resultList.addAll(query.getResultList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public PetPost getPetPostById(int postId)
    {
        TypedQuery<PetPost> query = em.createNamedQuery("PetPost.findById", PetPost.class);
        query.setParameter("id", postId);

        List<PetPost> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<PetPost> getAllPetPosts()
    {
        TypedQuery<PetPost> query = em.createNamedQuery("PetPost.findAll", PetPost.class);

        List<PetPost> resultList = query.getResultList();

        return resultList;
    }

    public List<PetPost> getAnimalType(String animal) {
        TypedQuery<PetPost> query = em.createNamedQuery("PetPost.findAnimalType", PetPost.class);
        query.setParameter("animalType", animal);

        List<PetPost> resultList = query.getResultList();

        return resultList;
    }

    public List<PetPost> getPetPostsbyUserId(int id)
    {
        User user = em.find(User.class, id);
        List<PetPost> userPetPosts= (List<PetPost>) user.getPosts();
        return (List<PetPost>) user.getPosts();
    }

    public byte[] getImageByPostId(int postId) {
        TypedQuery<PetPost> query = em.createNamedQuery("PetPost.findById", PetPost.class);
        query.setParameter("id", postId);

        List<PetPost> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }

        PetPost petPost = resultList.get(0);

        return petPost.getDetails().getPicture();
    }



    public JsonObject createPetPost(JsonObject jsonObj) throws NullPointerException
    {
        String result = "failure";
        int postId = 0;

        try
        {
            int userId = 0;
            //convert string to int if type is String
            if (jsonObj.getValueType().equals(JsonValue.ValueType.STRING))
            {
                userId = Integer.parseInt(jsonObj.getString("userId"));
            } else {
                userId = jsonObj.getInt("userId");
            }

            User owner = em.find(User.class, userId);
            if(owner != null)
            {
                //create petPost and put value
                PetPost petPost = new PetPost();
                petPost.setTitle(jsonObj.getString("title"));
                petPost.setAnimalType(jsonObj.getString("animalType"));
                petPost.setAddress( jsonObj.getString("address"));
                petPost.setCity( jsonObj.getString("city"));
                petPost.setZipCode( jsonObj.getString("zipCode"));
                petPost.setOwner(owner);

                //create pet Detail for detail of pet post
                PetDetails petDetails = new PetDetails();
                petDetails.setAnimalName(jsonObj.getString("animalName"));
                petDetails.setGender(jsonObj.getString("gender"));

                JsonValue.ValueType type = jsonObj.get("age").getValueType();
                System.out.print(type);

                //convert string to int if type is String
                if (jsonObj.get("age").getValueType().equals(JsonValue.ValueType.STRING))
                {
                    petDetails.setAge(Integer.parseInt(jsonObj.getString("age")));
                } else {
                    petDetails.setAge(jsonObj.getInt("age"));
                }

                petDetails.setDescrition( jsonObj.getString("description"));
                if(jsonObj.containsKey("picture")) {
                    petDetails.setPicture( jsonObj.getString("picture").getBytes());
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());
                petPost.setTimeStamp(date);

                petPost.setDetails(petDetails);
                ut.begin();
                em.persist(petPost);
                owner.setPost(petPost);
                em.merge(owner);
                em.flush();
                ut.commit();
                result = "success";
                postId = petPost.getId();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("result", result)
                .add("postId", postId)
                .build();

        return jsonResponse;
    }

    public boolean updatePetPostById(JsonObject jsonObj )
    {
        boolean result = false;
        try {
            PetPost postToUpdate = em.find(PetPost.class, jsonObj.getJsonNumber("postId").intValue());

            postToUpdate.setTitle(jsonObj.getString("title"));
            postToUpdate.setAnimalType(jsonObj.getString("animalType"));
            postToUpdate.setAddress( jsonObj.getString("address"));
            postToUpdate.setCity( jsonObj.getString("city"));
            postToUpdate.setZipCode( jsonObj.getString("zipCode"));

            PetDetails petDetails = new PetDetails();

            petDetails.setAnimalName(jsonObj.getString("animalName"));
            petDetails.setGender(jsonObj.getString("gender"));
            //convert string to int if type is String
            if (jsonObj.getValueType().equals(JsonValue.ValueType.STRING))
            {
                petDetails.setAge(Integer.parseInt(jsonObj.getString("age")));
            } else {
                petDetails.setAge(jsonObj.getInt("age"));
            }
            petDetails.setDescrition(jsonObj.getString("description"));
            if(jsonObj.containsKey("picture")) {
                petDetails.setPicture( jsonObj.getString("picture").getBytes());
            }

            postToUpdate.setDetails(petDetails);
            ut.begin();
            em.merge(postToUpdate);
            em.flush();
            ut.commit();
            result = true;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public boolean deletePetPostById(int id )
    {
        boolean result = false;
        try {
            PetPost postToDelete = em.find(PetPost.class, id);
            ut.begin();
            em.remove(postToDelete);
            em.flush();
            ut.commit();
            result = true;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return result;
    }

    //image DAO

    public boolean insertPicturePetPostById(int id, byte[] bytes) throws NullPointerException, HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException, NotSupportedException {

        PetPost petPost = em.find(PetPost.class, id);

        if(petPost == null) {
            return false;
        }

        PetDetails petDetail = petPost.getDetails();

        petDetail.setPicture(bytes);
        petPost.setDetails(petDetail);
        ut.begin();
        em.merge(petPost);
        em.flush();
        ut.commit();

        return true;
    }
}
