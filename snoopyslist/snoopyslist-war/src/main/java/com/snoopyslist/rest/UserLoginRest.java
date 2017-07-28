package com.snoopyslist.rest;

import com.snoopyslist.DBmodels.User;
import com.snoopyslist.DBservice.DBService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import org.apache.commons.codec.binary.Base64;

/**
 * Input:
 *      Mandatory:
 *          email - String
 *          password - String
 *
 *  Output:
 *      status (success or failure)
 */
@Path("user-login")
public class UserLoginRest {

    @Inject
    DBService dbService;

    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response checkLogin (JsonObject jsonObject) {
        //check input contain key and has value
        checkVariable(jsonObject, "email", "email is mandatory");
        checkVariable(jsonObject, "password", "password is mandatory");

        User user = dbService.checkUserLogin(jsonObject.getString("email"), jsonObject.getString("password"));

        String status = "failure";
        String userId = "-1";
        String email = "";
        String password = "";
        String firstName = "";
        String encodedString = "";

        if(user != null) {
            status = "success";
            email = user.getEmail();
            password = user.getPassword();
            firstName = user.getName();
            String str = email+":"+password;
            // encode data on your side using BASE64
            byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
            encodedString = "Basic "+ new String(bytesEncoded);

        }


        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("encodedString", encodedString)
                .add("firstName", firstName)
                .build();

        return Response.ok().entity(jsonResponse).build();
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
