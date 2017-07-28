package com.snoopyslist.rest;


import com.snoopyslist.DBmodels.PetPost;
import com.snoopyslist.DBservice.DBService;
import com.snoopyslist.model.ContractInfo;
import com.snoopyslist.model.PetDetail;
import com.snoopyslist.model.Thumbnail;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Pet-search Jax RS return detail of post contain postId in URL query string request and response JSON format
 *
 * Get Request
 *      input: URL Query
 *          /pet-detail/1234
 *
 *      output:
 *          Thumbnail: {
 *              postId - Int
 *              title - String
 *              animalType - String
 *              animalName - String
 *              age - Int
 *              gender - String
 *              size - String
 *              picture - String
 *          }
 *          ContractInfo: {
 *              phoneNumber - String
 *              address - String
 *              city - Sring
 *              zipCode - String
 *              email - String
 *          }
 *          description - String
 */
@Path("pet-detail")
public class PetDetailRest {

    @Context
    UriInfo uri;

    @Inject
    DBService dbService;

    @GET
    @Path("{identifier:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public PetDetail getPetDetail(@PathParam("identifier") Integer postId) {

        String imageUri = uri.getBaseUri() + "image/" + postId;

        PetPost post = dbService.getPetPostById(postId);

        //databbase match postId
        if(post == null) {
            throw new WebApplicationException(Response.
                    status(Response.Status.BAD_REQUEST)
                    .entity("No result of postId=" + postId)
                    .header("x-error-message", "no petPost with id="+postId)
                    .build());
        } else {
            //thumbnail
            Thumbnail tempThumbnail = new Thumbnail(
                    post.getId(),
                    post.getTitle(),
                    post.getAnimalType(),
                    post.getDetails().getAnimalName(),
                    post.getDetails().getAge(),
                    post.getDetails().getGender(),
                    post.getDetails().getSize(),
                    imageUri
            );

            //contract info
            ContractInfo tempContractInfo = new ContractInfo(
                    post.getOwner().getPhoneNumber(),
                    post.getAddress(),
                    post.getCity(),
                    post.getZipCode(),
                    post.getOwner().getEmail());

            //Petdetail object
            PetDetail detail = new PetDetail(
                    tempThumbnail,
                    tempContractInfo,
                    post.getDetails().getDescrition()
            );

            return detail;
        }
    }


}
