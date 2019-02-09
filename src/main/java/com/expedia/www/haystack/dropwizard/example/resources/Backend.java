/*
 * Copyright 2018 Expedia, Inc.
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 *
 */
package com.expedia.www.haystack.dropwizard.example.resources;

import com.codahale.metrics.annotation.Timed;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import lombok.Data;
import org.eclipse.microprofile.opentracing.Traced;

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
    public Saying sayHello(@QueryParam("name") String name) {
        final String value = String.format("Hello, %s!", name);
        return new Saying(counter.incrementAndGet(), value);
    }

    @GET
    @Timed
    @Path("/ignored")
    @Traced(false)
    public Saying sayHelloNotTracked(@QueryParam("name") String name) {
        return sayHello(name);
    }

    @Data
    private class Saying {
        private final long id;
        private final String saying;
    }
}
