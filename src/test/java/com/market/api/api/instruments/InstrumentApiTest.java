package com.market.api.api.instruments;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
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
 * API tests for Instrument endpoints using RestAssured with Allure Reports
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Instrument API Tests")
@Epic("Market Instruments API")
@Feature("Instrument Management")
class InstrumentApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/instruments";
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @DisplayName("GET /api/instruments - Should return all instruments")
    @Story("Retrieve All Instruments")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that GET /api/instruments returns a list of all available instruments with valid data structure")
    void testGetAllInstruments() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", hasSize(greaterThan(0)))
                .body("data[0].id", notNullValue())
                .body("data[0].name", notNullValue())
                .body("data[0].type", notNullValue())
                .body("data[0].symbol", notNullValue())
                .body("data[0].price", notNullValue());
    }

    @Test
    @DisplayName("GET /api/instruments/{id} - Should return specific instrument")
    @Story("Retrieve Instrument by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that GET /api/instruments/{id} returns the correct instrument details for a valid ID")
    void testGetInstrumentById() {
        String instrumentId = "11111111-aaaa-bbbb-cccc-000000000002";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + instrumentId)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.id", equalTo(instrumentId))
                .body("data.name", equalTo("Bitcoin"))
                .body("data.type", equalTo("CRYPTO"))
                .body("data.symbol", equalTo("BTCUSD"));
    }

    @Test
    @DisplayName("GET /api/instruments/{id} - Should return 404 for non-existent instrument")
    void testGetInstrumentById_NotFound() {
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
    @DisplayName("POST /api/instruments - Should create new instrument")
    @Story("Create New Instrument")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that POST /api/instruments successfully creates a new instrument with valid data")
    void testCreateInstrument() {
        String requestBody = """
                {
                    "name": "Ripple",
                    "type": "CRYPTO",
                    "symbol": "XRPUSD",
                    "price": 0.65
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("success", equalTo(true))
                .body("message", containsString("created"))
                .body("data.id", notNullValue())
                .body("data.name", equalTo("Ripple"))
                .body("data.type", equalTo("CRYPTO"))
                .body("data.symbol", equalTo("XRPUSD"))
                .body("data.price", equalTo(0.65f));
    }

    @Test
    @DisplayName("POST /api/instruments - Should fail with invalid data (missing required fields)")
    void testCreateInstrument_ValidationError() {
        String invalidRequestBody = """
                {
                    "name": "Invalid Instrument"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody)
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("POST /api/instruments - Should fail with negative price")
    void testCreateInstrument_NegativePrice() {
        String requestBody = """
                {
                    "name": "Invalid Instrument",
                    "type": "STOCK",
                    "symbol": "INVALID",
                    "price": -10.0
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("PUT /api/instruments/{id} - Should update existing instrument")
    void testUpdateInstrument() {
        String instrumentId = "11111111-aaaa-bbbb-cccc-000000000002";
        String updateBody = """
                {
                    "price": 45000.00
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put("/" + instrumentId)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", containsString("updated"))
                .body("data.id", equalTo(instrumentId))
                .body("data.price", equalTo(45000.00f));
    }

    @Test
    @DisplayName("PUT /api/instruments/{id} - Should return 404 for non-existent instrument")
    void testUpdateInstrument_NotFound() {
        String nonExistentId = "99999999-aaaa-bbbb-cccc-000000000000";
        String updateBody = """
                {
                    "price": 100.00
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put("/" + nonExistentId)
                .then()
                .statusCode(404)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("DELETE /api/instruments/{id} - Should delete instrument")
    void testDeleteInstrument() {
        // First create an instrument to delete
        String createBody = """
                {
                    "name": "To Be Deleted",
                    "type": "STOCK",
                    "symbol": "DELETE",
                    "price": 100.00
                }
                """;

        String createdId = given()
                .contentType(ContentType.JSON)
                .body(createBody)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .path("data.id");

        // Now delete it
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/" + createdId)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", containsString("deleted"));

        // Verify it's gone
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + createdId)
                .then()
                .statusCode(404);
    }
}
