package com.sapient.auction.common.config;

import com.sapient.auction.user.resource.UserResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by dpadal on 11/11/2016.
 */
@ApplicationPath(value = "/auction")
public class RestConfig extends ResourceConfig{

    public RestConfig() {
        //TODO: add other Resouce classes here with comma separated.
        registerClasses(UserResource.class);
    }
}
