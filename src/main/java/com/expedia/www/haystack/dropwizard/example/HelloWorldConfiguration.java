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
import com.expedia.www.haystack.dropwizard.example.bundles.Traceable;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HelloWorldConfiguration extends Configuration implements Traceable {

    @Valid
    @NotNull
    private TracerFactory tracerFactory;

    @JsonProperty("tracer")
    public TracerFactory getTracerFactory() {
        return tracerFactory;
    }

    @JsonProperty("tracer")
    public void setTracerFactory(TracerFactory tracerFactory) {
        this.tracerFactory = tracerFactory;
    }
}
