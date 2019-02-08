package com.expedia.www.haystack.dropwizard.example.resources;

import com.codahale.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class Frontend {
    private final static String backendUrl = "http://localhost:9091/api";
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
