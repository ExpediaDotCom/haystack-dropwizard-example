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
package com.expedia.www.haystack.dropwizard.example;

import com.expedia.www.haystack.dropwizard.example.bundles.HaystackTracerBundle;
import com.expedia.www.haystack.dropwizard.example.resources.Backend;
import com.expedia.www.haystack.dropwizard.example.resources.Frontend;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    // bundle that initializes an instance of io.opentracing.Tracer
    private final HaystackTracerBundle<HelloWorldConfiguration> haystackTracerBundle = new HaystackTracerBundle<>();

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // the following line initializes server tracing and entertains @Traced
        // annotations on Resource methods
        bootstrap.addBundle(this.haystackTracerBundle);
    }

    @Override
    public void run(HelloWorldConfiguration helloWorldConfiguration,
                    Environment environment) {
        final String serviceName = helloWorldConfiguration.getTracerFactory().getServiceName().toLowerCase();

        if ("frontend".equalsIgnoreCase(serviceName)) {
            // the following line registers ClientTracingFeature to trace all
            // outbound service calls
            final Client client = ClientBuilder.newBuilder()
                    .register(this.haystackTracerBundle.clientTracingFeature(environment))
                    .build();

            environment.jersey().register(new Frontend(client));
        }
        else {
            environment.jersey().register(new Backend());
        }
    }
}
