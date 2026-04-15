package ru.adel.tests.api;

import ru.adel.tests.api.accounts.UserApiClient;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.adel.tests.BaseTest;


public class BaseApiTest extends BaseTest {
    protected UserApiClient apiClient;
    private static int timeout = 5000;

    @BeforeAll
    static void setUpConfig() {
        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", timeout)
                .setParam("http.socket.timeout", timeout));
    }
}
