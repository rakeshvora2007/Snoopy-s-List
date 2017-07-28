package com.snoopyslist.rest;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.snoopyslist.DBmodels.User;
import com.snoopyslist.DBservice.DBService;
import org.apache.commons.codec.binary.Base64;


/**
 * Created by Rakesh J on 5/1/2017.
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter {

    @Inject
    DBService dbService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    private static final String SECURED_URL_PREFIX = "secured";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
            Cookie cookie = requestContext.getCookies().get("Authorization");
            String authToken = cookie.getValue();
            //List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);

            //if (authHeader != null && authHeader.size() > 0) {
            if(authToken != null && authToken.length() >0 ) {

                //String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                byte[] decodeBase64 = Base64.decodeBase64(authToken);
                String decodedToken = new String(decodeBase64, "UTF-8");
                StringTokenizer tokenier = new StringTokenizer(decodedToken, ":");
                String email = tokenier.nextToken();
                String password = tokenier.nextToken();

                User user = dbService.checkUserLogin(email, password);

                requestContext.getHeaders().add("userId", String.valueOf(user.getId()));

                if (user != null) {
                    return;
                }

            }
            Response unauthorizedStatus = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource.")
                    .build();

            requestContext.abortWith(unauthorizedStatus);

        }

    }
}
