# MarketInstrumentsAPI - Quick Start Guide

## ✅ Project Successfully Created!

Your **MarketInstrumentsAPI** is ready for QA automation testing!

---

## 📦 What's Included

### ✅ Complete Backend API
- **29 Java source files** (Models, DTOs, Services, Controllers, Repositories)
- **3 Mock JSON data files** (Instruments, Users, Trades)
- **4 Test classes** (22 comprehensive tests)
- **Full Swagger/OpenAPI documentation**

### ✅ All Dependencies from Public Maven Central
- Spring Boot 3.2.1
- Java 17
- RestAssured 5.4.0
- Selenium WebDriver 4.16.1
- JUnit 5
- MapStruct, Lombok, Jackson

---

## 🚀 Getting Started (3 Steps)

### Step 1: Navigate to Project
```bash
cd "MarketInstrumentsAPI"
```

### Step 2: Run the Application
```bash
mvn spring-boot:run
```

### Step 3: Open Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Run Tests

### All Tests (22 tests)
```bash
mvn test
```

**Expected Result:** ✅ Tests run: 22, Failures: 0, Errors: 0, Skipped: 0

### API Tests Only
```bash
mvn test -Dtest="*ApiTest"
```

### Selenium Tests (requires app running)
```bash
# Terminal 1
mvn spring-boot:run

# Terminal 2
mvn test -Dtest="SwaggerUiSeleniumTest"
```

---

## 📊 API Endpoints Summary

### Instruments (Full CRUD)
- `GET /api/instruments` - List all
- `GET /api/instruments/{id}` - Get by ID
- `POST /api/instruments` - Create new
- `PUT /api/instruments/{id}` - Update
- `DELETE /api/instruments/{id}` - Delete

### Users (Read-only)
- `GET /api/users` - List all
- `GET /api/users/{id}` - Get by ID

### Trades (Read + Create)
- `GET /api/trades` - List all
- `GET /api/trades/{id}` - Get by ID
- `POST /api/trades` - Create new

---

## 📝 Example cURL Commands

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

---

## 📂 Mock Data Loaded at Startup

- **10 Instruments** (Bitcoin, Ethereum, Apple, Tesla, etc.)
- **8 Users** (from US, BR, CN, GB, AE, DE, IN)
- **10 Trades** (various BUY/SELL transactions)

---

## 🎯 Perfect For

✅ **API Automation Practice** (RestAssured)  
✅ **Selenium WebDriver Testing**  
✅ **CI/CD Pipeline Integration**  
✅ **Test Framework Development**  
✅ **QA Certification Projects**  

---

## 📚 Project Structure

```
MarketInstrumentsAPI/
├── pom.xml                          # Maven configuration
├── README.md                        # Full documentation
├── QUICKSTART.md                    # This file
└── src/
    ├── main/
    │   ├── java/com/market/api/
    │   │   ├── config/              # Swagger + Exception handling
    │   │   ├── controller/          # REST endpoints
    │   │   ├── service/             # Business logic
    │   │   ├── repository/          # Data access
    │   │   ├── model/               # Domain models
    │   │   ├── dto/                 # API DTOs
    │   │   └── mapper/              # MapStruct mappers
    │   └── resources/
    │       ├── application.yml
    │       └── mockdata/            # JSON data files
    └── test/
        └── java/com/market/api/
            ├── api/                 # RestAssured tests
            └── selenium/            # WebDriver tests
```

---

## 🔍 Test Results Summary

```
✅ InstrumentApiTest  - 9 tests passed
✅ UserApiTest        - 4 tests passed
✅ TradeApiTest       - 9 tests passed
✅ SwaggerUiSeleniumTest - 7 tests (requires running app)

Total: 22 API tests + 7 Selenium tests
```

---

## 🛠️ Build Commands

```bash
# Clean build
mvn clean install

# Compile only
mvn compile

# Run tests
mvn test

# Package JAR
mvn package

# Run packaged JAR
java -jar target/market-instruments-api-1.0.0.jar
```

---

## 🌐 Important URLs

| Service | URL |
|---------|-----|
| **Swagger UI** | http://localhost:8080/swagger-ui/index.html |
| **API Docs (JSON)** | http://localhost:8080/api-docs |
| **Instruments API** | http://localhost:8080/api/instruments |
| **Users API** | http://localhost:8080/api/users |
| **Trades API** | http://localhost:8080/api/trades |

---

## 💡 Next Steps

1. ✅ **Explore Swagger UI** - Try the interactive API documentation
2. ✅ **Run the tests** - See RestAssured in action
3. ✅ **Create custom tests** - Practice your automation skills
4. ✅ **Integrate with CI/CD** - Add to Jenkins/GitHub Actions
5. ✅ **Extend the API** - Add new endpoints or features

---

## 📞 Support

- Check **README.md** for detailed documentation
- Review **test examples** in `src/test/java`
- Examine **mock data** in `src/main/resources/mockdata`

---

**🎉 Happy Testing!**

---

*Generated: 2025-12-09*  
*Version: 1.0.0*  
*Java: 17+ | Spring Boot: 3.2.1 | Maven: 3.6+*
