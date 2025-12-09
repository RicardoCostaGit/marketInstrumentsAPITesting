package com.market.api.api.trades;

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
 * API tests for Trade endpoints using RestAssured
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Trade API Tests")
class TradeApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/trades";
    }

    @Test
    @DisplayName("GET /api/trades - Should return all trades")
    void testGetAllTrades() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", hasSize(greaterThan(0)))
                .body("data[0].id", notNullValue())
                .body("data[0].userId", notNullValue())
                .body("data[0].instrumentId", notNullValue())
                .body("data[0].quantity", notNullValue())
                .body("data[0].side", notNullValue())
                .body("data[0].timestamp", notNullValue());
    }

    @Test
    @DisplayName("GET /api/trades/{id} - Should return specific trade")
    void testGetTradeById() {
        String tradeId = "33333333-aaaa-bbbb-cccc-000000000001";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + tradeId)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.id", equalTo(tradeId))
                .body("data.userId", equalTo("22222222-aaaa-bbbb-cccc-000000000001"))
                .body("data.instrumentId", equalTo("11111111-aaaa-bbbb-cccc-000000000002"))
                .body("data.quantity", equalTo(2))
                .body("data.side", equalTo("BUY"));
    }

    @Test
    @DisplayName("GET /api/trades/{id} - Should return 404 for non-existent trade")
    void testGetTradeById_NotFound() {
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
    @DisplayName("POST /api/trades - Should create new trade")
    void testCreateTrade() {
        String requestBody = """
                {
                    "userId": "22222222-aaaa-bbbb-cccc-000000000001",
                    "instrumentId": "11111111-aaaa-bbbb-cccc-000000000003",
                    "quantity": 5,
                    "side": "BUY"
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
                .body("data.userId", equalTo("22222222-aaaa-bbbb-cccc-000000000001"))
                .body("data.instrumentId", equalTo("11111111-aaaa-bbbb-cccc-000000000003"))
                .body("data.quantity", equalTo(5))
                .body("data.side", equalTo("BUY"))
                .body("data.timestamp", notNullValue());
    }

    @Test
    @DisplayName("POST /api/trades - Should fail with non-existent user")
    void testCreateTrade_InvalidUser() {
        String requestBody = """
                {
                    "userId": "99999999-aaaa-bbbb-cccc-000000000000",
                    "instrumentId": "11111111-aaaa-bbbb-cccc-000000000002",
                    "quantity": 5,
                    "side": "BUY"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("User not found"));
    }

    @Test
    @DisplayName("POST /api/trades - Should fail with non-existent instrument")
    void testCreateTrade_InvalidInstrument() {
        String requestBody = """
                {
                    "userId": "22222222-aaaa-bbbb-cccc-000000000001",
                    "instrumentId": "99999999-aaaa-bbbb-cccc-000000000000",
                    "quantity": 5,
                    "side": "BUY"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("Instrument not found"));
    }

    @Test
    @DisplayName("POST /api/trades - Should fail with missing required fields")
    void testCreateTrade_ValidationError() {
        String invalidRequestBody = """
                {
                    "userId": "22222222-aaaa-bbbb-cccc-000000000001"
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
    @DisplayName("POST /api/trades - Should fail with negative quantity")
    void testCreateTrade_NegativeQuantity() {
        String requestBody = """
                {
                    "userId": "22222222-aaaa-bbbb-cccc-000000000001",
                    "instrumentId": "11111111-aaaa-bbbb-cccc-000000000002",
                    "quantity": -5,
                    "side": "BUY"
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
    @DisplayName("GET /api/trades - Should return trades with both BUY and SELL sides")
    void testGetAllTrades_VerifyTradeSides() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("data.side", hasItems("BUY", "SELL"));
    }
}
