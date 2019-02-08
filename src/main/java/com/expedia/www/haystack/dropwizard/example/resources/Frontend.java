package com.expedia.www.haystack.dropwizard.example.resources;

import com.codahale.metrics.annotation.Timed;
import io.opentracing.Tracer;
import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature;
import org.eclipse.microprofile.opentracing.Traced;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ragsingh on 04/02/19.
 */

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class Frontend {
    private final String backendUrl = "http://localhost:9091/api";
    private final Client client;

    public Frontend(Client client) {
        this.client = client;
    }

    @GET
    @Timed
    @Traced(operationName = "/hello")
    public String sayHelloToServer(@QueryParam("name") Optional<String> name) {
        return  client.target(backendUrl).queryParam("name", name.orElse("Haystack")).request().get(String.class);
    }

}
