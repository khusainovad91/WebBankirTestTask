package ru.adel.mocks.mixed;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class ApiUiMock {
    private String endpoint;
    public ApiUiMock(String endpoint) {
        this.endpoint = endpoint;
    }

    public void mockApiUsers(String userName, String id) {
        stubFor(post(endpoint)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
            {
              "id": "%s",
              "name": "%s"
            }
        """.formatted(id, userName))
                ));
    }

    public void mockApiUsersWithEmail(String userName, String email) {
        stubFor(post(endpoint)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
            {
              "email": "%s",
              "name": "%s"
            }
        """.formatted(email, userName))
                ));
    }

    public void mockUiData(String userName, String id) {
        stubFor(get(endpoint + "/" + id)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":\""+ id +"\",\"name\":\"" + userName + "\"}")
                ));
    }

    public void mockApiDelete(String id) {
        stubFor(delete(endpoint + id)
                .willReturn(aResponse()
                        .withStatus(204)));
    }

    public void mockUiDelete(String id) {
        stubFor(get(endpoint + "/" + id)
                .willReturn(aResponse()
                        .withStatus(404)));
    }

    public void mockTriggered(String trigger) {
        stubFor(post(endpoint)
                .withRequestBody(containing(trigger))
                .willReturn(aResponse().withStatus(400)));
    }
}
