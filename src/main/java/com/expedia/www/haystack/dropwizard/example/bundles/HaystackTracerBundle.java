package com.expedia.www.haystack.dropwizard.example.bundles;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.opentracing.Tracer;
import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature;
import io.opentracing.contrib.jaxrs2.server.ServerTracingDynamicFeature;
import io.opentracing.contrib.jaxrs2.server.SpanFinishingFilter;
import io.opentracing.noop.NoopTracerFactory;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.apache.commons.lang3.Validate;

public class HaystackTracerBundle<T extends Traceable> implements ConfiguredBundle<T> {

    @Override
    public void run(T traceable, Environment environment) {
        Validate.notNull(traceable);
        Validate.notNull(environment);

        final Tracer tracer = traceable.getTracerFactory().build(environment);

        // This registers a server tracing feature with the jersey environment so that the
        // incoming calls to the service are traced
        final ServerTracingDynamicFeature tracingDynamicFeature = new ServerTracingDynamicFeature
                .Builder(tracer)
                .withTraceSerialization(false).build();
        environment.jersey().register(tracingDynamicFeature);

        environment.servlets()
                .addFilter("SpanFinishingFilter", new SpanFinishingFilter(tracer))
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        
        environment.jersey().property(Tracer.class.getName(), tracer);
    }

    @Override
    public void initialize(Bootstrap bootstrap) {
        //no-op
    }

    public ClientTracingFeature clientTracingFeature(Environment environment) {
        Tracer tracer = environment.jersey().getProperty(Tracer.class.getName());
        if (tracer == null) {
            tracer = NoopTracerFactory.create();
        }

        return new ClientTracingFeature.Builder(tracer)
                .withTraceSerialization(false)
                .build();
    }
}
