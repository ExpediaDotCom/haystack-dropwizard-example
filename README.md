## Table of Contents

- [Haystack-dropwizard-starter](#Haystack-dropwizard-starter)
- [About this example](#about-this-example)
  * [Instrumentation](#instrumentation)
- [Running this example](#running-this-example)
  * [Build](#build)
  * [Run with no backend](#run-with-no-backend)
  * [Run with Haystack server](#run-with-haystack-server)

## Haystack-dropwizard-example

In this example, the two Dropwizard services call one from another to show a client and server working with tracing instrumentation. Telemetry from this application is recorded in [Haystack](http://expediadotcom.github.io/haystack/) server, a distributed tracing system. Haystack will allow one to see the service call dependency and how long it took for the whole operation to complete. Here is what a sample output in the UI will look like: 

![haystack-ui](doc/screenshot-traces.png)

## About this example

This is a very basic example that can output the instrumentation to console and/or a Haystack server. This is an example application written with two simple Dropwizard Services to show how a dropwizard application can be instrumented with [haystack-client-java dropwizard integration](https://github.com/ExpediaDotCom/haystack-client-java/tree/master/integrations/dropwizard). 

* Service one:  Backend.  This listens on port 9091 and exposes one endpoint : http://localhost:9091/api - when invoked, it returns a simple string like `Hello, Haystack!`

* Service two:  Frontend:  This listens on port 9090 and exposes one endpoint: http://localhost:9090/hello  - this in turn calls the endpoint exposed by `Backend` and proxy the response

### Instrumentation

If one peeks into the code, both [Frontend.java](src/main/java/com/expedia/www/haystack/dropwizard/example/resources/Frontend.java) and [Backend.java](src/main/java/com/expedia/www/haystack/dropwizard/example/resources/Backend.java) are simple Dropwizard resources with no additional instrumentation code.  Most of the instrumentation is done in the [HelloWorldApplication.java](src/main/java/com/expedia/www/haystack/dropwizard/example/HelloWorldApplication.java) by the dependencies `haystack-client-dropwizard` and `opentracing-jaxrs2`, that is included in the [pom.xml](pom.xml). For more information how this library works, one can check the documentations at [https://github.com/opentracing-contrib/java-jaxrs](https://github.com/opentracing-contrib/java-jaxrs) and [https://github.com/ExpediaDotCom/haystack-client-java/tree/master/integrations/dropwizard](https://github.com/ExpediaDotCom/haystack-client-java/tree/master/integrations/dropwizard)

## Running this example

### Build

Required:

* Java 1.8


Build:

```bash
./mvnw clean compile
```

There are two modes to run the application. One with no `Haystack` server, where the instrumentation is simply logged to the console and with Haystack server.

### Run with no backend

In this mode, the application runs with the configuration in [config_frontend.yml](config_frontend.yml) and [config_backend.yml](config_backend.yml) which configures the instrumentation to use a simple logger as the dispatcher.

To run the example in this mode, execute 

```bash
./mvnw exec:java -Dexec.args="server config_frontend.yml"
./mvnw exec:java -Dexec.args="server config_backend.yml"

```

and send a sample request

```bash
curl http://localhost:9090/hello
```

With that, one will see two lines in the console log of `Frontend`. One for the request it received from the `curl` with `span.kind=server` and one for the request it sent to the backend with `span.kind=client`

Front end:

```
   INFO  [2019-02-08 09:56:48,150] dispatcher: {},c0fc5228-4422-4305-8dad-b6aa3d6016af,40cb7928-aa03-4bb1-9650-d78ff3329016,ea12803c-9955-4c2e-9be2-f208232b7d4a,false,GET,{http.status_code=200, span.kind=client, http.url=http://localhost:9091/api?name=Haystack, peer.hostname=localhost, peer.port=9091, http.method=GET},[],[child_of,{},c0fc5228-4422-4305-8dad-b6aa3d6016af,ea12803c-9955-4c2e-9be2-f208232b7d4a,<null>,false],245000,1549619808150000,1549619807905000,true,[]
   INFO  [2019-02-08 09:56:48,230] dispatcher: {},c0fc5228-4422-4305-8dad-b6aa3d6016af,ea12803c-9955-4c2e-9be2-f208232b7d4a,<null>,false,/hello,{http.status_code=200, span.kind=server, http.url=http://localhost:9090/hello, http.method=GET},[],[],427000,1549619808230000,1549619807803000,true,[]
```

And one line in the backend console log for the request it received from the front end application. Another point to note will be the same ids between client and server.

```
   INFO  [2019-02-08 09:56:48,146] dispatcher: {},c0fc5228-4422-4305-8dad-b6aa3d6016af,40cb7928-aa03-4bb1-9650-d78ff3329016,ea12803c-9955-4c2e-9be2-f208232b7d4a,false,sayHello,{http.status_code=200, span.kind=server, http.url=http://localhost:9091/api?name=Haystack, http.method=GET},[],[child_of,{},c0fc5228-4422-4305-8dad-b6aa3d6016af,40cb7928-aa03-4bb1-9650-d78ff3329016,ea12803c-9955-4c2e-9be2-f208232b7d4a,true],88000,1549619808146000,1549619808058000,true,[]
```


### Run with Haystack server

To start haystack and agent locally, one can follow the instructions at [https://github.com/mchandramouli/haystack-docker#to-start-traces-and-trends](https://github.com/mchandramouli/haystack-docker#to-start-traces-and-trends)
 
After starting Haystack server, run this example with the following commands. This starts the application with the configuration in [config_frontend.yml](config_frontend.yml) and [config_backend.yml](config_backend.yml)

```bash
./mvnw exec:java -Dexec.args="server config_frontend.yml"
./mvnw exec:java -Dexec.args="server config_backend.yml"
```

and send a sample request

```bash
curl http://localhost:9090/hello
```

And then open Haystack UI at [http://localhost:8080/](http://localhost:8080/) and search for `serviceName=Frontend` to see the traces. (see screenshot above)

One can also use the sample script we have to send more requests to the sample application and see metrics such as count, duration histogram etc in Haystack UI under trends.

```bash
./run.sh
```

Screenshot of the trends view with computed metrics:


![haystack-ui](doc/screenshot-trends.png)

   
