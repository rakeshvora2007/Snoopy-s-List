package com.snoopyslist.rest;

import com.snoopyslist.DBmodels.PetPost;
import com.snoopyslist.DBservice.DBService;
import com.snoopyslist.model.PetSearch;
import com.snoopyslist.model.Thumbnail;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Pet-search Jax RS return list of thumbnail of the search result in JSON format
 */

/**
 * search get request
 *      Mandatory:
 *          zipCode - String
 *      Optional:
 *          title - String
 *          animalType - String
 *          animalName - String
 *          gender - String
 *          age - String
 *          size - String
 *      Response:
 *          list<PetSearch>
 */
@Path("pet-search")
public class PetSearchRest
{

    @Context
    UriInfo uri;

    @Inject
    DBService dbService;

    // http://localhost:8080/snoopyslist/rest/pet-search?location=12345&animal=dog&breed=&age=baby&gender=male&distance=25&size=small&name=
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PetSearch getPetSearch (@Context UriInfo info) {
        //get URL query string
        String zipCodeStr = info.getQueryParameters().getFirst("zipCode"),
                animalTypeStr = info.getQueryParameters().getFirst("animalType"),
                animalNameStr = info.getQueryParameters().getFirst("animalName"),
                ageStr = info.getQueryParameters().getFirst("age"),
                genderStr = info.getQueryParameters().getFirst("gender"),
                distanceStr = info.getQueryParameters().getFirst("distance"),
                sizeStr = info.getQueryParameters().getFirst("size"),
                pageNumberStr = info.getQueryParameters().getFirst("pageNumber");

        //set default distance to 10
        if(distanceStr == null || "".equals(distanceStr)) {
            distanceStr = "10";
        }

        JsonObject jsonObject = (JsonObject) Json.createObjectBuilder()
                .add("zipCode", zipCodeStr)
                .add("animalType", setNullToEmpty(animalTypeStr))
                .add("animalName", setNullToEmpty(animalNameStr) )
                .add("age", setNullToEmpty(ageStr))
                .add("gender", setNullToEmpty(genderStr))
                .add("distance", distanceStr)
                .add("size", setNullToEmpty(sizeStr))
                .add("pageNumber", setNullToEmpty(pageNumberStr))
                .build();

        List<PetPost> petPosts = dbService.searchPetPost(jsonObject);
        int searchResultNumber = petPosts.size();

        //create list of thumbnail object
        List<Thumbnail> thumbnailList = new ArrayList<>();

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

        //petSearch object
        PetSearch petSearch = new PetSearch(searchResultNumber, thumbnailList);

        return petSearch;
    }

    public String setNullToEmpty(String value) {
        if(value == null) {
            return "";
        } else {
            return value;
        }
    }

}
