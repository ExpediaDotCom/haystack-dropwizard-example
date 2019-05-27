package com.expedia.www.haystack.dropwizard.example;

import com.expedia.haystack.dropwizard.bundle.HaystackTracerBundle;
import com.expedia.www.haystack.dropwizard.example.resources.Backend;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BackendApplication extends Application<HelloWorldConfiguration> {

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
        environment.jersey().register(new Backend());
    }
}
