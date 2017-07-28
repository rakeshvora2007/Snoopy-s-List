package com.snoopyslist.rest;

import com.snoopyslist.DBservice.DBService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Create post request
 *      Mandatory:
 *          userId - Int
 *          title - String
 *          animalName - String
 *          animalType - String
 *          gender - String
 *          age - String
 *          size - String
 *          description - String
 *          address - String
 *          city - String
 *          zipCode - String
 *       Optional:
 *          picture - String
 *
 *      Response:
 *          Success
 *          Fail
 *
 *  PUT Post request
 *
 *
 *  Delete Post request
 */
@Path("secured/pet-post")
public class PostRest {
    @Inject
    DBService dbService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject createPost(@HeaderParam("userId") int userId, JsonObject jsonObj) {

        //check input contain key and has value

        checkVariable(jsonObj, "title", "title is mandatory");
        checkVariable(jsonObj, "animalType", "type of animal is mandatory");
        checkVariable(jsonObj, "animalName", "Name of animal is mandatory");
        checkVariable(jsonObj, "gender", "gender of animal is mandatory");
        checkVariable(jsonObj, "age", "age of animal is mandatory");
        checkVariable(jsonObj, "size", "size of animal is mandatory");
        checkVariable(jsonObj, "description", "Description is mandatory");
        checkVariable(jsonObj, "address", "Address is mandatory");
        checkVariable(jsonObj, "city", "city is mandatory");
        checkVariable(jsonObj, "zipCode", "zip code is mandatory");

        jsonObj = addJsonObject(jsonObj, "userId", userId);

        //create post and return result into boolean
        JsonObject jsonResult = dbService.createPetPost(jsonObj);

        return jsonResult;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject editPost(JsonObject jsonObj) {
        //check input contain key and has value
        checkVariable(jsonObj, "postId", "userId is mandatory");
        checkVariable(jsonObj, "title", "title is mandatory");
        checkVariable(jsonObj, "animalType", "type of animal is mandatory");
        checkVariable(jsonObj, "animalName", "Name of animal is mandatory");
        checkVariable(jsonObj, "gender", "gender of animal is mandatory");
        checkVariable(jsonObj, "age", "age of animal is mandatory");
        checkVariable(jsonObj, "size", "size of animal is mandatory");
        checkVariable(jsonObj, "description", "Description is mandatory");
        checkVariable(jsonObj, "address", "Address is mandatory");
        checkVariable(jsonObj, "city", "city is mandatory");
        checkVariable(jsonObj, "zipCode", "zip code is mandatory");

        boolean editResult = dbService.updatePetPostById(jsonObj);

        String status = "failure";

        if (editResult == true)
            status = "success";
        String error = "";

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)
                .build();

        return jsonResponse;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject deletePost(JsonObject jsonObj) {

        //check input contain key and has value
        checkVariable(jsonObj, "postId", "postId is mandatory");

        boolean deleteResult = dbService.deletePetPostById(jsonObj.getJsonNumber("postId").intValue());

        String status = "failure";

        if (deleteResult == true)
            status = "success";
        String error = "";

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)
                .build();

        return jsonResponse;
    }

    public void checkVariable(JsonObject jsonObj, String key, String message) {
        if(!jsonObj.containsKey(key) || jsonObj.isNull(key)) {
            throw new WebApplicationException(Response.
                    status(Response.Status.BAD_REQUEST)
                    .header("x-error-message", message)
                    .build());
        }
    }

    public JsonObject addJsonObject(JsonObject source, String key, int value) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add(key, value);
        source.entrySet().forEach(e -> builder.add(e.getKey(), e.getValue()));
        return builder.build();
    }
}
