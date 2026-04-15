package ru.adel.tests.api.accounts;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApiClient {
    private String baseUrl;
    private String endpoint;

    public UserApiClient(String baseUrl, String endpoint) {
        this.baseUrl = baseUrl;
        this.endpoint = endpoint;
    }

    public Response createUser(Map<String, Object> payload) {
        return given()
                .log().all()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(endpoint);
    }

    public Response getUserById(String userId) {
        return given()
                .log().all()
                .baseUri(baseUrl)
                .pathParam("id", userId)
                .when()
                .get(endpoint + "/{id}");
    }

    public Response deleteUser(String userId) {
        return given()
                .log().all()
                .baseUri(baseUrl)
                .when()
                .delete(endpoint + "/" + userId);
    }
}