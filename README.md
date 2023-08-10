# Playwright Test Framework

# Author: [Roman Muflikhunov]

### https://www.linkedin.com/in/gotoqa/

### Description:

#### This Java-based Opencart shop testing framework:

- Utilizes Playwright for browser automation.
- Employs Maven as a build tool.
- Implements TestNG for testing.
- Executes parallel tests with TestNG.
- Adopts Page Object Model design.
- Enhances reporting with Extent Report.
- Enables logging with Slf4j-api.
- Achieves parallelism using Selenium GRID HUB.
- Utilizes Docker for parallel test execution.

___

### Main libraries used:

- Playwright: Browser automation and testing.
- TestNG: Testing framework with annotations.
- Extentreports: Detailed test reporting tool.
- Slf4j-api: Logging facade for Java.

___

### Playwright Doc

https://playwright.dev/docs/intro

___

### Test Opencart shop

https://gotoqa.ru/opencart/

___

Running tests locally:

### 1 (Maven Runner)

mvn clean test

### 2 (TestNG Runner)

src/test/resources/testrunners/testng_regressions.xml -> Run
___

Running tests utilizes Docker and the Selenium GRID HUB:

### Start and check containers:

docker-compose up -d

SELENIUM_REMOTE_URL=http://localhost:4444/wd/hub mvn test
___

### Aventstack Extent Report

build/TestExecutionReport.html
___
