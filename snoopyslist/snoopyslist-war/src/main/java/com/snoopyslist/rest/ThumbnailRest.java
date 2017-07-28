package com.snoopyslist.rest;

import com.snoopyslist.DBmodels.PetPost;
import com.snoopyslist.DBservice.DBService;
import com.snoopyslist.model.Thumbnail;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * thumbnail Jax RS return thumbnail list of animal type
 *
 * Get Request
 *      input: URL Query
 *          /animal/dog
 *
 *      output:
 *          list<Thumbail>
 */
@Path("animal")
public class ThumbnailRest {

    @Context
    UriInfo uri;

    @Inject
    DBService dbService;

    @GET
    @Path("{animal}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Thumbnail> getPetByCategory (@PathParam("animal") String animal) {
        List<PetPost> petPosts = dbService.getAnimalType(animal);



        List<Thumbnail> thumbnailList = new ArrayList<Thumbnail>();

        for (PetPost s: petPosts) {
            String imageUri = uri.getBaseUri() + "image/" + s.getId();

            Thumbnail thumbnail = new Thumbnail(
                    s.getId(),
                    s.getTitle(),
                    s.getAnimalType(),
                    s.getDetails().getAnimalName(),
                    s.getDetails().getAge(),
                    s.getDetails().getGender(),
                    s.getDetails().getSize(),
                    imageUri
            );

            thumbnailList.add(thumbnail);
        }

        return thumbnailList;
    }
}
