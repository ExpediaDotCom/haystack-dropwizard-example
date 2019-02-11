package com.expedia.www.haystack.dropwizard.example;

import com.expedia.haystack.dropwizard.bundle.HaystackTracerBundle;
import com.expedia.www.haystack.dropwizard.example.resources.Frontend;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class FrontendApplication extends Application<HelloWorldConfiguration> {

    // bundle that initializes an instance of io.opentracing.Tracer
    private final HaystackTracerBundle<HelloWorldConfiguration> haystackTracerBundle = new HaystackTracerBundle<>();

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // the following line initializes server tracing and entertains @Traced
        // annotations on Resource methods
        bootstrap.addBundle(this.haystackTracerBundle);
    }

    @Override
    public void run(HelloWorldConfiguration helloWorldConfiguration,
                    Environment environment) {
        // the following line registers ClientTracingFeature to trace all
        // outbound service calls
        final Client client = ClientBuilder.newBuilder()
                .register(this.haystackTracerBundle.clientTracingFeature(environment))
                .build();

        environment.jersey().register(new Frontend(client));
    }
}
