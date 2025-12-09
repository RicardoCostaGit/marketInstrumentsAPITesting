package com.market.api.api.users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * API tests for User endpoints using RestAssured
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("User API Tests")
class UserApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/users";
    }

    @Test
    @DisplayName("GET /api/users - Should return all users")
    void testGetAllUsers() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", hasSize(greaterThan(0)))
                .body("data[0].id", notNullValue())
                .body("data[0].username", notNullValue())
                .body("data[0].country", notNullValue())
                .body("data[0].balance", notNullValue());
    }

    @Test
    @DisplayName("GET /api/users/{id} - Should return specific user")
    void testGetUserById() {
        String userId = "22222222-aaaa-bbbb-cccc-000000000001";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + userId)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.id", equalTo(userId))
                .body("data.username", equalTo("john_trader"))
                .body("data.country", equalTo("US"))
                .body("data.balance", equalTo(50000.00f));
    }

    @Test
    @DisplayName("GET /api/users/{id} - Should return 404 for non-existent user")
    void testGetUserById_NotFound() {
        String nonExistentId = "99999999-aaaa-bbbb-cccc-000000000000";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + nonExistentId)
                .then()
                .statusCode(404)
                .body("success", equalTo(false))
                .body("message", containsString("not found"));
    }

    @Test
    @DisplayName("GET /api/users - Should return users with different countries")
    void testGetAllUsers_VerifyDiversity() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("data.country", hasItems("US", "BR", "CN", "GB"));
    }
}
