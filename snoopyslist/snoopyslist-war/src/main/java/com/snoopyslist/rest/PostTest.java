package com.snoopyslist.rest;

import com.snoopyslist.DBmodels.PetPost;
import com.snoopyslist.DBmodels.User;
import com.snoopyslist.DBservice.DBService;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.*;
import javax.transaction.NotSupportedException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by rui on 4/25/17.
 */
@Path("/test")
public class PostTest {

    @Inject
    private DBService dbService;

//    @GET
//    @Path("/search")
//    public JsonObject searchPost() {
//        return (JsonObject) dbService.searchPetPost();
//    }

//    @GET
//    @Path("/search")
//    public List<PetPost> searchPost() {
//        return (JsonObject) dbService.searchPetPost();
//    }

    @Path("/createUser")
    @POST
    public Response createPost(@FormParam("id") int id, @FormParam("name") String name) {
//          dbService.createUser();

        return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject updateUser(JsonObject jsonObj) {
        //check input is empty


        try {
            dbService.updateUserInfoById(jsonObj.getInt("id"), jsonObj);
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (javax.transaction.NotSupportedException e) {
            e.printStackTrace();
        }

        String status = "success";
        String error = "";

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)

                .build();

        return jsonResponse;
    }


    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject deleteUser(JsonObject jsonObj) {
        try {
            dbService.deleteUserById(jsonObj.getInt("id"));
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        }

        String status = "success";
        String error = "";

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)
                .build();

        return jsonResponse;
    }

    @Path("/createPetPost")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject createPost(JsonObject jsonObj) {
        dbService.createPetPost(jsonObj);

        String status = "success";
        String error = "";

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)
                .build();

        return jsonResponse;
    }

    @Path("/getUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return dbService.getAllUsers();
    }

    @Path("/getPosts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PetPost> getPosts() {
        return dbService.getAllPetPosts();
    }

    @GET
    @Path("/getPostsbyUserId/{id}")
    public List<PetPost> getBook(@PathParam("id") int id) {
        // search my database and get a string representation and return it
        return dbService.getPetPostsbyUserId(id);
    }

    //test with this url
    //http://localhost:8080/snoopyslist/rest/test/fileUpload/2
//    @POST
//    @Path("/fileUpload/{id}")
//    //@Consumes( {"application/x-www-form-urlencoded", "multipart/form-data"})
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public JsonObject uploadImage(MultipartFormDataInput input
//            //@FormDataParam("file") InputStream file,
//            //@FormDataParam("file") FormDataContentDisposition fileInputDetails

//    @POST
//    @Path("/upload")
//    @Consumes( MediaType.MULTIPART_FORM_DATA )
//    @Produces( MediaType.APPLICATION_JSON )
//    public JsonObject loadApplicationTemplate(
//            @FormDataParam("file") InputStream uploadedInputStream,
//            @FormDataParam("file") FormDataContentDisposition fileInputDetails
//
//    ) throws IOException {
//       InputStream file = null;
//        try {
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            int read = 0;
//            byte[] bytes = new byte[1024];
//            while ((read = file.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            System.out.println(out.toByteArray().toString());
//            //System.out.println(fileDetail.toString());
//            dbService.insertPicturePetPostById(2, out.toByteArray());
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        String status = "success";
//        String error = "";
//        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
//                .add("status", status)
//                .add("error", error)
////                .add("id", id)
//                .build();
//
//        return jsonResponse;
//    }

    @POST
    @Path("/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@PathParam("id") Integer id, MultipartFormDataInput input)
    {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                try {
                    dbService.insertPicturePetPostById(id, bytes);
                } catch (HeuristicRollbackException e) {
                    e.printStackTrace();
                } catch (RollbackException e) {
                    e.printStackTrace();
                } catch (HeuristicMixedException e) {
                    e.printStackTrace();
                } catch (SystemException e) {
                    e.printStackTrace();
                } catch (NotSupportedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }

        String status = "success";
        String error = "";
        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)
//                .add("id", id)
                .build();

        return Response.ok().entity(jsonResponse).build();
    }



    @GET
    @Path("/image/{id}")
    @Produces("image/jpg")
    public Response getFullImage(@PathParam("id") int id) throws FileNotFoundException {

        byte[] imageBytes = dbService.getImageByPostId(id);

//        PetDetails petDetails = dbService.getImageByPostId(id);
//        byte[] image = petDetails.getPicture();
//
//
////        return Response.ok().entity(new StreamingOutput(){
////            @Override
////            public void write(OutputStream output)
////                    throws IOException, WebApplicationException {
////                output.write(imageBytes);
////                output.flush();
////            }
////        }).build();
//
//
//        File file1 = new File("C:\\Users\\Tony\\Desktop\\cat-care_cat-nutrition-tips_overweight_body4_left.jpg");
//
//        FileInputStream fis = new FileInputStream(file1);
//        //System.out.println(file.exists() + "!!");
//        //InputStream in = resource.openStream();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        byte[] buf = new byte[1024];
//        try {
//            for (int readNum; (readNum = fis.read(buf)) != -1;) {
//                bos.write(buf, 0, readNum); //no doubt here is 0
//                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
//                System.out.println("read " + readNum + " bytes,");
//            }
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//        byte[] bytes = bos.toByteArray();
//
//        String imageFile = new String(bytes);
//
//        String imageBytes = new String(image);
//
//        System.out.println(imageFile);
//        System.out.println(imageBytes);
//
//        Response.ResponseBuilder responseBuilder = Response.ok((Object) file1);
//        responseBuilder.header("Content-Disposition", "attachment; filename=\"MyPngImageFile.png\"");
//        return responseBuilder.build();

        return Response.ok(new ByteArrayInputStream(imageBytes)).build();
    }


//    @POST
//    @Path("upload/{id}")
//    @Consumes( "multipart/form-data")
//    public void addBlob(@PathParam("id") Integer id, @FormDataParam("file") InputStream uploadedInputStream) throws IOException {
//        E24ClientTemp entityToMerge = find(id);
//        try {
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            int read = 0;
//            byte[] bytes = new byte[1024];
//            while ((read = uploadedInputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            entityToMerge.setTestBlob(out.toByteArray());
//            super.edit(entityToMerge);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

