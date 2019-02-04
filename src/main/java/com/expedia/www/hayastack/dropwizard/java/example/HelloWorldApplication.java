package com.expedia.www.hayastack.dropwizard.java.example;

import com.expedia.www.hayastack.dropwizard.java.example.health.TemplateHealthCheck;
import com.expedia.www.hayastack.dropwizard.java.example.resources.HelloWorldResource;
import com.expedia.www.hayastack.dropwizard.java.example.resources.UntracedResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import io.opentracing.Tracer;
import io.opentracing.contrib.jaxrs2.server.ServerTracingDynamicFeature;
import io.opentracing.contrib.jaxrs2.server.SpanFinishingFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by ragsingh on 01/02/19.
 */
public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void run(HelloWorldConfiguration helloWorldConfiguration,
                    Environment environment) throws Exception {

        Tracer tracer = helloWorldConfiguration.getTracer().build(environment);
        final ServerTracingDynamicFeature tracingDynamicFeature = new ServerTracingDynamicFeature
                .Builder(tracer).build();
        environment.jersey().register(tracingDynamicFeature);

        environment.servlets().addFilter("SpanFinishingFilter", new SpanFinishingFilter(tracer))
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        final HelloWorldResource helloWorldResource = new HelloWorldResource(
                helloWorldConfiguration.getTemplate(),
                helloWorldConfiguration.getDefaultName());

        environment.jersey().register(helloWorldResource);

        environment.jersey().register(new UntracedResource(
                helloWorldConfiguration.getTemplate(),
                helloWorldConfiguration.getDefaultName()));


        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(helloWorldConfiguration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}
