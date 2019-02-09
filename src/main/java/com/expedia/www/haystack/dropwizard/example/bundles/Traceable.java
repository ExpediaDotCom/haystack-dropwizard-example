package com.expedia.www.haystack.dropwizard.example.bundles;

import com.expedia.haystack.dropwizard.configuration.TracerFactory;

public interface Traceable {
    TracerFactory getTracerFactory();
}