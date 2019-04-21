# mock-external-service-example
Sometime, your project have to call some external APIs. 

`mockserver-client` is an easy way to mock those services.

# Dependency
```
testCompile 'org.mock-server:mockserver-netty:5.5.1'
testCompile 'org.mock-server:mockserver-client-java:5.5.1'
```

# Mock
```Groovy
final int SERVER_PORT = 8080
ClientAndServer mockServer = ClientAndServer.startClientAndServer()

new MockServerClient(SERVER_HOST, SERVER_PORT).with {
            when(
                    request().withMethod("GET")
                            .withPath("/action")
                            .withQueryStringParameters(["name": ["doremon"]]))
                    .respond(response()
                    .withStatusCode(200)
                    .withBody("""
                        {
                          "data": {
                            "action": "fly"
                          },
                          "status_code": 200,
                          "meta": null
                         }""", MediaType.JSON_UTF_8))

            when(
                    request().withMethod("GET")
                            .withPath("/action")
                            .withQueryStringParameters(["name": ["mèo nhà"]]))
                    .respond(response()
                    .withStatusCode(200)
                    .withBody("""
                        {
                          "data": {
                            "action": "meow"
                          },
                          "status_code": 200,
                          "meta": null
                         }""", MediaType.JSON_UTF_8))
        }

```
