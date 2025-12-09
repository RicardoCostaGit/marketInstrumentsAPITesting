# MarketInstrumentsAPI

A Spring Boot REST API with mock data for QA testing and automation practice.

## Overview

This API provides endpoints for managing financial market data:
- **Instruments** (FOREX, CRYPTO, STOCK, CFD, OPTIONS)
- **Users** (mock trader accounts)
- **Trades** (buy/sell transactions)

## Technology Stack

- Java 17
- Spring Boot 3.2.1
- Maven
- Swagger / OpenAPI 3
- JUnit 5, RestAssured, Selenium WebDriver
- Lombok, MapStruct, Jackson

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Run the Application

```bash
cd MarketInstrumentsAPI
mvn spring-boot:run
```

The API will start on **http://localhost:8080**

### Access Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

## API Endpoints

### Instruments
- `GET /api/instruments` - Get all instruments
- `GET /api/instruments/{id}` - Get instrument by ID
- `POST /api/instruments` - Create new instrument
- `PUT /api/instruments/{id}` - Update instrument
- `DELETE /api/instruments/{id}` - Delete instrument

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID

### Trades
- `GET /api/trades` - Get all trades
- `GET /api/trades/{id}` - Get trade by ID
- `POST /api/trades` - Create new trade

## Data Models

### Instrument
```json
{
  "id": "uuid",
  "name": "Bitcoin",
  "type": "CRYPTO",
  "symbol": "BTCUSD",
  "price": 43210.55
}
```
Types: `FOREX`, `CRYPTO`, `STOCK`, `CFD`, `OPTION_CALL`, `OPTION_PUT`

### User
```json
{
  "id": "uuid",
  "username": "john_trader",
  "country": "US",
  "balance": 50000.00
}
```

### Trade
```json
{
  "id": "uuid",
  "userId": "uuid",
  "instrumentId": "uuid",
  "quantity": 10,
  "side": "BUY",
  "timestamp": "2024-01-15T10:30:00"
}
```
Sides: `BUY`, `SELL`

## Running Tests

### All Tests
```bash
mvn test
```

### API Tests Only
```bash
mvn test -Dtest="*ApiTest"
```

### Selenium Tests
Note: The application must be running first.

```bash
# Terminal 1
mvn spring-boot:run

# Terminal 2
mvn test -Dtest="SwaggerUiSeleniumTest"
```

## Project Structure

```
MarketInstrumentsAPI/
├── src/
│   ├── main/
│   │   ├── java/com/market/api/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── model/           # Domain models
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   └── mapper/          # MapStruct mappers
│   │   └── resources/
│   │       ├── application.yml
│   │       └── mockdata/        # JSON data files
│   └── test/
│       └── java/com/market/api/
│           ├── api/             # RestAssured tests
│           └── selenium/        # WebDriver tests
└── pom.xml
```

## Example Requests

### Get All Instruments
```bash
curl http://localhost:8080/api/instruments
```

### Create New Instrument
```bash
curl -X POST http://localhost:8080/api/instruments \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Litecoin",
    "type": "CRYPTO",
    "symbol": "LTCUSD",
    "price": 85.50
  }'
```

### Create New Trade
```bash
curl -X POST http://localhost:8080/api/trades \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "22222222-aaaa-bbbb-cccc-000000000001",
    "instrumentId": "11111111-aaaa-bbbb-cccc-000000000002",
    "quantity": 5,
    "side": "BUY"
  }'
```

## Mock Data

The application loads mock data from JSON files at startup:
- 10 Instruments (Bitcoin, Ethereum, Apple, Tesla, etc.)
- 8 Users (from different countries)
- 10 Trades (various buy/sell transactions)

All data is stored in-memory using `ConcurrentHashMap`.

## Architecture

```
Controller → Service → Repository → In-Memory Storage
     ↓          ↓
    DTO      Domain Model
```

Key design principles:
- Separation of concerns (Controller, Service, Repository layers)
- DTO pattern (no entity exposure in API responses)
- Global exception handling
- Input validation with Jakarta Bean Validation
- Comprehensive Swagger/OpenAPI documentation

## Error Handling

All errors return a consistent JSON structure:

```json
{
  "success": false,
  "message": "Error description",
  "data": null
}
```

HTTP Status Codes:
- `200 OK` - Successful GET/PUT/DELETE
- `201 Created` - Successful POST
- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Unexpected errors

## Build Commands

```bash
# Clean build
mvn clean install

# Run tests
mvn test

# Package JAR
mvn package

# Run packaged JAR
java -jar target/market-instruments-api-1.0.0.jar
```

## Testing

The project includes comprehensive tests:
- **22 RestAssured API tests** (positive and negative scenarios)
- **7 Selenium WebDriver tests** (Swagger UI validation)

Test coverage includes:
- CRUD operations
- Validation error handling
- 404 Not Found scenarios
- Edge cases (negative prices, invalid data)

## License

MIT License - Free to use for learning and testing purposes.
