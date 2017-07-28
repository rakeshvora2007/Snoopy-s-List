package com.snoopyslist.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Rakesh J on 5/1/2017.
 */

@Path("secured")
public class SecuredResource {

    @GET
    @Path("message")
    @Produces(MediaType.TEXT_PLAIN)
    public String secureMethod(){
        return "This API is secured";
    }
}
