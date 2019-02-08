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
