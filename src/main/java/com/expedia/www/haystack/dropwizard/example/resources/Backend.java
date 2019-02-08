package com.expedia.www.haystack.dropwizard.example.resources;

import com.codahale.metrics.annotation.Timed;
import com.expedia.www.haystack.dropwizard.example.entities.Saying;
import org.eclipse.microprofile.opentracing.Traced;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class Backend {
        private final AtomicLong counter;

    public Backend() {
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    @Traced(operationName = "sayHello")
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format("Hello, %s!", name.orElse("Harry"));
        return new Saying(counter.incrementAndGet(), value);
    }

    @GET
    @Timed
    @Path("/ignored")
    @Traced(false)
    public Saying sayHelloNotTracked(@QueryParam("name") Optional<String> name) {
        return sayHello(name);
    }

}
