package com.expedia.www.haystack.dropwizard.example;

import com.expedia.www.haystack.dropwizard.example.health.TemplateHealthCheck;
import com.expedia.www.haystack.dropwizard.example.resources.Backend;
import com.expedia.www.haystack.dropwizard.example.resources.Frontend;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import io.opentracing.Tracer;
import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature;
import io.opentracing.contrib.jaxrs2.server.ServerTracingDynamicFeature;
import io.opentracing.contrib.jaxrs2.server.SpanFinishingFilter;

import javax.servlet.DispatcherType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.EnumSet;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void run(HelloWorldConfiguration helloWorldConfiguration,
                    Environment environment) throws Exception {

        final Tracer tracer = helloWorldConfiguration.getTracer().build(environment);
        registerTracer(environment, tracer);

        switch (helloWorldConfiguration.getServiceType().toLowerCase()) {
            case "frontend":
                final Client client = ClientBuilder.newBuilder()
                        .register(new ClientTracingFeature.Builder(tracer)
                                        .withTraceSerialization(false)
                                        .build())
                        .build();
                environment.jersey().register(new Frontend(client));
                break;
            case "backend":
                environment.jersey().register(new Backend(
                        helloWorldConfiguration.getTemplate(),
                        helloWorldConfiguration.getDefaultName()));
                break;
            default:
        }
    }

    private void registerTracer(Environment environment, Tracer tracer) {
        final ServerTracingDynamicFeature tracingDynamicFeature = new ServerTracingDynamicFeature
                .Builder(tracer)
                .withTraceSerialization(false).build();
        environment.jersey().register(tracingDynamicFeature);

        environment.servlets()
                .addFilter("SpanFinishingFilter", new SpanFinishingFilter(tracer))
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
