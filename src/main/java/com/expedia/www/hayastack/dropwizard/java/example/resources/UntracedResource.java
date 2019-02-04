package com.expedia.www.hayastack.dropwizard.java.example.resources;

import org.eclipse.microprofile.opentracing.Traced;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ragsingh on 04/02/19.
 */

@Path("/do-not-trace-me")
@Produces(MediaType.APPLICATION_JSON)
@Traced(false)
public class UntracedResource extends HelloWorldResource {

    public UntracedResource(String template, String defaultName) {
        super(template, defaultName);
    }

}
