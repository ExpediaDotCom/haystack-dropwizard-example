package com.expedia.www.haystack.dropwizard.example;

import com.expedia.haystack.dropwizard.configuration.TracerFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HelloWorldConfiguration extends Configuration {

    @NotEmpty
    private String serviceType;

    @Valid
    @NotNull
    private TracerFactory tracerFactory;

    @JsonProperty
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @JsonProperty
    public String getServiceType() {
        return serviceType;
    }

    @JsonProperty
    public TracerFactory getTracer() {
        return tracerFactory;
    }

    @JsonProperty
    public void setTracer(TracerFactory tracerFactory) {
        this.tracerFactory = tracerFactory;
    }

}
