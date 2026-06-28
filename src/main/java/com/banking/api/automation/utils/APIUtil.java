package com.banking.api.automation.utils;

import com.banking.api.automation.config.APIConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class APIUtil {

    /**
     * Generic POST Request
     */
    public static Response postRequest(String endpoint, Object requestBody) {
        return given()
                .spec(APIConfig.getRequestSpecification())
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * Generic GET Request
     */
    public static Response getRequest(String endpoint) {
        return given()
                .spec(APIConfig.getRequestSpecification())
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * GET Request with Path Parameter
     */
    public static Response getRequestWithPathParam(String endpoint, String paramName, String paramValue) {
        return given()
                .spec(APIConfig.getRequestSpecification())
                .pathParam(paramName, paramValue)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * GET Request with Query Parameter
     */
    public static Response getRequestWithQueryParam(String endpoint, String paramName, String paramValue) {
        return given()
                .spec(APIConfig.getRequestSpecification())
                .queryParam(paramName, paramValue)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * PUT Request
     */
    public static Response putRequest(String endpoint, Object requestBody) {
        return given()
                .spec(APIConfig.getRequestSpecification())
                .body(requestBody)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    /**
     * DELETE Request
     */
    public static Response deleteRequest(String endpoint) {
        return given()
                .spec(APIConfig.getRequestSpecification())
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

}