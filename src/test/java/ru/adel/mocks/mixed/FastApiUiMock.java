package ru.adel.mocks.mixed;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class FastApiUiMock {
    public void mockApiUsers(String userName, String id) {
        stubFor(post("/api/v1/users")
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

    public void mockUiData(String userName, String id) {
        stubFor(get("/api/v1/users/" + id)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":\"123\",\"name\":\"" + userName + "\"}")
                ));
    }

    public void mockApiDelete(String id) {
        stubFor(delete("/api/v1/users/" + id)
                .willReturn(aResponse()
                        .withStatus(204)));
    }

    public void mockUiDelete(String id) {
        stubFor(get("/api/v1/users/" + id)
                .willReturn(aResponse()
                        .withStatus(404)));
    }
}
