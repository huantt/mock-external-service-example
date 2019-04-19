import com.google.common.net.MediaType
import com.huantt.service.external.CatService
import org.bson.Document
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response

/**
 * @author huantt on 2019-04-19
 */
class MockServiceTest extends Specification {

    @Shared
    ClientAndServer mockServer

    @Unroll
    void "Find cat's action"() {
        setup:
        mockFindWebsiteAPI()

        when:
        Document response = CatService.getCatAction(name)

        then:
        response.get("data",Document.class).getString("action") == action

        where:
        name      | action
        "doremon" | "fly"
        "mèo nhà" | "meow"
    }

    @Ignore
    private void mockFindWebsiteAPI() {
        final int SERVER_PORT = 8080
        final String SERVER_HOST = "localhost"
        mockServer = mockServer ?: ClientAndServer.startClientAndServer(SERVER_PORT)
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
    }

}
