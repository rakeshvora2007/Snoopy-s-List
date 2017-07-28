package com.snoopyslist.rest;

import com.snoopyslist.DBmodels.PetPost;
import com.snoopyslist.DBservice.DBService;
import com.snoopyslist.model.UserPost;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * return post list from a specific user
 *      POST Request:
 *          userId:
 *
 *      Response:
 *          List<userPost>
 */
@Path("secured/user-posts")
public class UserPostListRest {

    @Inject
    DBService dbService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserPost> getUserPosts(@HeaderParam("userId") int userId) {

        List<UserPost> userPostList = new ArrayList<UserPost>();

        List<PetPost> postListResult = dbService.getPetPostsbyUserId(userId);

        for (PetPost petPost : postListResult) {
            UserPost userPost = new UserPost(petPost.getId(), petPost.getTitle(), petPost.getAnimalType(), petPost.getTimeStamp());
            userPostList.add(userPost);
        }

        return userPostList;
    }
}
