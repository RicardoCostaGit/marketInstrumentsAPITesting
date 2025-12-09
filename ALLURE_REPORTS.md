# Allure Reports Integration

This project uses Allure Framework for comprehensive test reporting.

## Features

- **Rich Test Reports**: Beautiful HTML reports with test execution details
- **Request/Response Logging**: Automatic capture of REST API requests and responses
- **Test Categorization**: Organize tests by Epic, Feature, and Story
- **Severity Levels**: Prioritize tests by severity (BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL)
- **Attachments**: Screenshots, logs, and other files attached to test results
- **Historical Trends**: Track test execution over time

## Running Tests with Allure

### 1. Run Tests and Generate Results

```bash
mvn clean test
```

This will:
- Execute all tests
- Generate Allure results in `target/allure-results/`

### 2. Generate and View Allure Report

```bash
# Generate the HTML report
mvn allure:report

# Serve the report locally (opens in browser)
mvn allure:serve
```

The `allure:serve` command will:
- Generate the report
- Start a local web server
- Automatically open the report in your default browser

### 3. View Existing Report

If you've already generated a report:

```bash
open target/site/allure-maven-plugin/index.html
```

## Allure Annotations Used

### Class-Level Annotations
- `@Epic("Market Instruments API")` - High-level business feature
- `@Feature("Instrument Management")` - Specific feature being tested

### Method-Level Annotations
- `@Story("Create New Instrument")` - User story or test scenario
- `@Severity(SeverityLevel.CRITICAL)` - Test importance level
- `@Description("...")` - Detailed test description
- `@DisplayName("...")` - Human-readable test name

## Report Features

### What You'll See in Allure Reports

1. **Overview Dashboard**
   - Total tests, pass/fail rate
   - Test execution timeline
   - Environment information

2. **Test Suites**
   - Organized by package and class
   - Execution time for each test
   - Status indicators (passed/failed/skipped)

3. **Graphs**
   - Severity distribution
   - Duration trends
   - Categories breakdown

4. **Behaviors**
   - Tests grouped by Epic → Feature → Story
   - Business-oriented view of test coverage

5. **Request/Response Details**
   - Full HTTP request details (headers, body, URL)
   - Complete response data (status, headers, body)
   - Execution time for each API call

## CI/CD Integration

### GitHub Actions Example

```yaml
- name: Run tests
  run: mvn clean test

- name: Generate Allure Report
  run: mvn allure:report

- name: Upload Allure Results
  uses: actions/upload-artifact@v3
  with:
    name: allure-results
    path: target/allure-results
```

## Configuration

Allure configuration is in `src/test/resources/allure.properties`:

```properties
allure.results.directory=target/allure-results
allure.link.issue.pattern=https://github.com/RicardoCostaGit/marketInstrumentsAPITesting/issues/{}
allure.link.tms.pattern=https://github.com/RicardoCostaGit/marketInstrumentsAPITesting/issues/{}
```

## Troubleshooting

### Report not generating?
- Ensure tests ran successfully: `mvn clean test`
- Check `target/allure-results/` directory exists and contains JSON files

### AspectJ weaver errors?
- The AspectJ weaver is configured in `pom.xml` maven-surefire-plugin
- It's required for Allure to capture test execution details

### Want to clear old results?
```bash
rm -rf target/allure-results/*
mvn clean test
```

## Learn More

- [Allure Documentation](https://docs.qameta.io/allure/)
- [Allure JUnit 5](https://docs.qameta.io/allure/#_junit_5)
- [Allure Rest Assured](https://docs.qameta.io/allure/#_rest_assured)
