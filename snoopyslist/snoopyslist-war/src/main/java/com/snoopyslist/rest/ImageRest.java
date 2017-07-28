package com.snoopyslist.rest;

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
 * image rest
 *
 *      GET Request path: image/{postId}
 *          Return: bytes image
 *
 *      POST request path: image/{postId}
 *          multipart-form-data: image file
 *
 *          Return: Json(status: {success or failure}, error: )
 *
 *      DELETE request path: image/{postId}
 *
 *          Return: Json(status: {success or failure}, error: )
 */
@Path("image")
public class ImageRest {

    @Inject
    DBService dbService;

    @GET
    @Path("{postId:[0-9]*}")
    @Produces("image/jpg")
    public Response getImage(@PathParam("postId") int postId) throws FileNotFoundException {

        byte[] imageBytes = dbService.getImageByPostId(postId);

        if (imageBytes == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new ByteArrayInputStream(imageBytes)).build();
    }

    @POST
    @Path("{postId:[0-9]*}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@PathParam("postId") Integer postId, MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        boolean insertStatus = false;

        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                try {
                    insertStatus = dbService.insertPicturePetPostById(postId, bytes);
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
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }

        String status = "failure";
        String error = "Can't insert image to database";

        if(insertStatus) {
            //add image string to
            status = "success";
            error = "";
        }

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)
                .build();

        return Response.ok().entity(jsonResponse).build();
    }

    @DELETE
    @Path("{postId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFile(@PathParam("postId") Integer postId) {

        boolean deleteStatus = false;
        try {
            deleteStatus = dbService.insertPicturePetPostById(postId, null);
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

        String status = "failure";
        String error = "Can't delete image in database";

        if(deleteStatus) {
            status = "success";
            error = "";
        }

        JsonObject jsonResponse = (JsonObject) Json.createObjectBuilder()
                .add("status", status)
                .add("error", error)
                .build();

        return Response.ok().entity(jsonResponse).build();
    }
}
