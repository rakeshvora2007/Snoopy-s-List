package com.snoopyslist.rest;

import com.snoopyslist.DBservice.DBService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Create user Rest request contain JSON format
 *      firstName - String
 *      lastName - String
 *      email - String
 *      password - String
 *      userType - int
 *      phoneNumber - String
 *    Optional:
 *      website - String
 *
 *      Response:
 *          Success
 *          Fail
 */
@Path("user-create")
public class UserCreationRest {
    @Inject
    DBService dbService;

    //create user post
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject createUser(JsonObject jsonObj)  {

        //check input contain key and has value
        checkVariable(jsonObj, "firstName", "first name is mandatory");
        checkVariable(jsonObj, "lastName", "last name is mandatory");
        checkVariable(jsonObj, "email", "last name is mandatory");
        checkVariable(jsonObj, "password", "password is mandatory");
        checkVariable(jsonObj, "phoneNumber", "password is mandatory");
        checkVariable(jsonObj, "userType", "user type is mandatory");

        String status = "failure", error = "";

        //check if email have taken
        boolean emailTaken = dbService.checkUserEmail(jsonObj.getString("email"));
        if (emailTaken == true) {
            error = "Email has been taken choose different email address";
        } else {
            boolean createResult = dbService.createUser(jsonObj);

            if(createResult == true)
                status = "success";
        }

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
}
