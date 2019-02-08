package com.expedia.www.haystack.dropwizard.example;

import com.expedia.haystack.dropwizard.configuration.TracerFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by ragsingh on 01/02/19.
 */
public class HelloWorldConfiguration extends Configuration {

    @NotEmpty
    private String template;

    @NotEmpty
    private String serviceType;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    private TracerFactory tracerFactory;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

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

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }
}
