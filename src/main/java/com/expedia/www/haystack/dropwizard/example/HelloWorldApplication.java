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

public class HelloWorldApplication {
    public static void main(String[] args) throws Exception {
        final String config = String.format("%s_%s.yml", (Object[]) args);
        if ("frontend".equalsIgnoreCase(args[0])) {
            new FrontendApplication().run("server", config);
        }
        else {
            new BackendApplication().run("server", config);
        }
    }
}
