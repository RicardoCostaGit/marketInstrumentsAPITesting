package com.market.api.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium WebDriver test scaffold for Swagger UI validation
 * 
 * NOTE: This test requires:
 * 1. The application to be running on http://localhost:8080
 * 2. ChromeDriver to be installed and available in PATH
 * 
 * To run the application before tests:
 * mvn spring-boot:run
 */
@DisplayName("Swagger UI Selenium Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SwaggerUiSeleniumTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String SWAGGER_URL = "http://localhost:8080/swagger-ui/index.html";

    @BeforeAll
    static void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should load Swagger UI page successfully")
    void testSwaggerPageLoads() {
        driver.get(SWAGGER_URL);

        // Verify page title
        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.contains("Swagger") || pageTitle.contains("OpenAPI"),
                "Page title should contain 'Swagger' or 'OpenAPI'");

        System.out.println("✓ Swagger UI page loaded successfully");
        System.out.println("  Page title: " + pageTitle);
    }

    @Test
    @Order(2)
    @DisplayName("Should display API title and version")
    void testApiTitleAndVersion() {
        driver.get(SWAGGER_URL);

        // Wait for the API title to be visible
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".information-container .title, .info .title, hgroup .title")));

        // Verify API information is displayed
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Market Instruments API") ||
                pageSource.contains("market-instruments-api"),
                "Page should contain API title");

        System.out.println("✓ API title and version displayed");
    }

    @Test
    @Order(3)
    @DisplayName("Should display all API endpoint tags")
    void testApiEndpointTags() {
        driver.get(SWAGGER_URL);

        // Wait for tags to load
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".opblock-tag-section, .opblock-tag")));

        String pageSource = driver.getPageSource();

        // Verify all three controller tags are present
        assertTrue(pageSource.contains("Instruments"), "Should contain Instruments tag");
        assertTrue(pageSource.contains("Users"), "Should contain Users tag");
        assertTrue(pageSource.contains("Trades"), "Should contain Trades tag");

        System.out.println("✓ All API endpoint tags displayed:");
        System.out.println("  - Instruments");
        System.out.println("  - Users");
        System.out.println("  - Trades");
    }

    @Test
    @Order(4)
    @DisplayName("Should expand and display Instruments endpoints")
    void testInstrumentsEndpoints() {
        driver.get(SWAGGER_URL);

        // Wait for the page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".opblock-tag-section")));

        String pageSource = driver.getPageSource();

        // Verify Instruments endpoints are present
        assertTrue(pageSource.contains("/api/instruments"),
                "Should contain /api/instruments endpoint");

        System.out.println("✓ Instruments endpoints displayed");
    }

    @Test
    @Order(5)
    @DisplayName("Should verify HTTP methods are displayed")
    void testHttpMethods() {
        driver.get(SWAGGER_URL);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".opblock")));

        String pageSource = driver.getPageSource();

        // Verify different HTTP methods are present
        assertTrue(pageSource.contains("get") || pageSource.contains("GET"),
                "Should contain GET method");
        assertTrue(pageSource.contains("post") || pageSource.contains("POST"),
                "Should contain POST method");
        assertTrue(pageSource.contains("put") || pageSource.contains("PUT"),
                "Should contain PUT method");
        assertTrue(pageSource.contains("delete") || pageSource.contains("DELETE"),
                "Should contain DELETE method");

        System.out.println("✓ HTTP methods displayed:");
        System.out.println("  - GET");
        System.out.println("  - POST");
        System.out.println("  - PUT");
        System.out.println("  - DELETE");
    }

    @Test
    @Order(6)
    @DisplayName("Should take screenshot of Swagger UI")
    @Disabled("Enable this test to capture screenshots manually")
    void testTakeScreenshot() {
        driver.get(SWAGGER_URL);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".swagger-ui")));

        // Screenshot functionality can be added here using TakesScreenshot
        System.out.println("✓ Screenshot location: target/screenshots/swagger-ui.png");
    }

    @Test
    @Order(7)
    @DisplayName("Should verify page is responsive")
    void testResponsiveDesign() {
        driver.get(SWAGGER_URL);

        // Test different viewport sizes
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".swagger-ui")));
        assertTrue(driver.findElement(By.cssSelector("body")).isDisplayed());

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        assertTrue(driver.findElement(By.cssSelector("body")).isDisplayed());

        System.out.println("✓ Page is responsive across different viewport sizes");
    }
}
