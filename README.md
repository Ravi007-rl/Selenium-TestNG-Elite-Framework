# Selenium TestNG Elite Framework

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Installation Instructions](#installation-instructions)
4. [Usage](#usage)
5. [What Framework Users Need to Do](#what-framework-users-need-to-do)
6. [GitHub Actions Workflow](#github-actions-workflow)
7. [Contact Information](#contact-information)

## Overview
The Selenium TestNG Elite Framework is a robust and scalable test automation framework designed to simplify the process of writing and running automated tests for web applications. This framework leverages [Selenium WebDriver](https://www.selenium.dev/documentation/) for browser automation and [TestNG](https://testng.org/) for test management.

The framework is designed to be user-friendly, allowing users to read 2-3 pre-written test cases and start writing their own test cases without starting from scratch.

## Features
- **Environment Configuration**: Uses [Dotenv](https://www.dotenv.org/) for managing environment variables. You can change browsers and switch between headed and headless modes from the command line or the .env file.
- **Multiple Browser Support**: A browser factory is created so when you pass the browser name from the command line or `.env` file, it will launch that browser.
- **Parallel Execution**: Run tests in parallel to save time. This feature works at the class level, and the thread count is hard-coded to 5 in the `testng.xml` file. You can change it, or in the future, it will be made dynamic so you can pass this from the command line.
- **Screenshot Capture**: Automatically captures screenshots on test failure and stores them in `target/test-result/{testcase name}/screenshot.png` format.
- **Logs**: Integrated with [Log4j](https://logging.apache.org/log4j/2.x/) for better logging of test case steps with auto-increment steps. Users don’t have to write “Step 1: Navigate to base URL”. Simply write “Navigate to base URL” and the step count is auto-added. Logs are displayed in the console as well as stored in target/test-result/{testcase name}/logfile.log format.
- **Supports Multiple Environments**: The framework supports multiple environments like Dev, QA, and Prod.
- **Extent Report**: Integrated with ExtentReports to generate beautiful HTML reports.
- **Random Data Generation**: Uses the [Faker library](https://faker.readthedocs.io/en/master/) for random data generation. There is also a `RandomHelper` class that gives you random elements from a list and generates random strings.
- **Element Helper Classes**:
    - **WaitHelper**: Uses explicit waits to get elements in a particular state like visible or clickable.
    - **SeleniumHelper**: Improves interaction with elements to remove flakiness. For example, `driver.findElement(By.xpath()).isDisplayed()` will return `true` if the element is displayed, but if not, it throws a NoSuchElementException. The helper method will return false instead.
    - **JavaScriptHelper**: Interacts with elements using JavaScript.
- **Integrated Surefire Plugin**: Using this plugin, you can run test cases from the CLI. It also generates a failed test cases XML file when any test case fails, so you can run only the failed test cases.
- **Mojo Plugin**: Uses a custom Mojo plugin to send emails with attachments. The plugin triggers the `emailHelper.sendEmailWithAttachment` method to send the Extent report to the user.
- **Debug Mode**: If you set `DEBUG=true` in the .env file, elements will be bordered in red before interaction.
- **Headless Mode**: If you set `HEADLESS=true` in the .env file, the browser will be launched in headless mode.
- **GitHub Actions**: This framework supports GitHub Actions. You can run the tests from the GitHub Actions workflow.


## Installation Instructions
1. **Clone the repository:**
    ```sh
    git clone https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework.git
    cd Selenium-TestNG-Elite-Framework
    ```

2. **Install dependencies:**
    ```sh
    mvn clean install -DskipTests
    ```

3. **Configure environment variables:**
   Create a .env file in the root directory and add the following:
    ```env
    BROWSER=chrome
    PLATFORM=web
    HEADLESS=false
    ```

## Usage
### Running Tests
To run the tests, use the following Maven command:
```sh
mvn clean test -DsuiteXmlFile="testng.xml"
```

## What Framework Users Need to Do
- **Create DataObject**: The DataObject defines the shape of the object. For example, for a login form requiring email and password fields, create a class for that and shape the object accordingly.
- **DataFactory**: Use this to create data for your test cases.
- **Page Object Model**: The backbone of the framework. Use the By locator strategy. Users just need to create By locators and then interact with elements using the enhanced SeleniumHelper class.
- **Test File**: Where you can put your test cases.

For reference, 20 test cases have been automated to demonstrate how anyone can automate test cases.

## GitHub Actions Workflow

This repository contains a [GitHub Actions](https://github.com/features/actions) workflow for running automation test cases using [Selenium](https://www.selenium.dev/documentation/webdriver/support_features/expected_conditions/App) and [TestNG](https://testng.org/testng-1.0.dtd">). The workflow supports multiple environments and browsers, and it sends the test results via email.

### Workflow Overview

The workflow is triggered manually using the `workflow_dispatch` event. It allows you to specify the environment, browser, and email address to receive the test results. The workflow performs the following steps:

1. Checks out the repository.
2. Sets up Java [JDK](https://www.geeksforgeeks.org/jar-files-java/).
3. Sets up [Maven](http://maven.apache.org/surefire/maven-surefire-plugin/).
4. Sets up the specified browser (Chrome, Firefox, or Edge).
5. Installs dependencies without running tests.
6. Runs the tests.
7. Sends the test results via email.
8. Re-runs failed tests if any.
9. Re-sends the test results for the re-run tests via email.

### Inputs

The workflow accepts the following inputs:

- **Environment**: The environment to run the tests in. Options are `Test` and `Stage`. Default is `Test`.
- **Browser**: The browser to run the tests on. Options are `Chrome`, `Firefox`, and `Edge`. Default is `Chrome`.
- **Email**: The email address to receive the test results. This input is required.

### Usage

To manually trigger the workflow, follow these steps:

1. Go to the "Actions" tab in your GitHub repository.
2. Select the "Manual workflow for automation testcases" workflow.
3. Click the "Run workflow" button.
4. Fill in the required inputs (Environment, Browser, Email).
5. Click the "Run workflow" button to start the workflow.

### Workflow Steps

#### 1. Checkout Repository

```yaml
- uses: actions/checkout@v2
```

#### 2. Setup Java JDK

```yaml
- name: Setup Java JDK
  uses: actions/setup-java@v4.3.0
  with:
    java-version: 22
    distribution: 'temurin'
```

#### 3. Setup Maven

```yaml
- name: Setup Maven
  uses: stCarolas/setup-maven@v5
```

#### 4. Setup Browser

Depending on the selected browser, one of the following steps will be executed:

```yaml
- name: Set up Chrome
  if: ${{ github.event.inputs.Browser == 'chrome' }}
  uses: browser-actions/setup-chrome@v1.7.2

- name: Set up Firefox
  if: ${{ github.event.inputs.Browser == 'firefox' }}
  uses: browser-actions/setup-firefox@v1.5.2

- name: Set up Edge
  if: ${{ github.event.inputs.Browser == 'edge' }}
  uses: browser-actions/setup-edge@v1.1.1
```

#### 5. Install Dependencies

```yaml
- name: Install dependencies without running tests
  run: mvn clean install -DskipTests
```

#### 6. Run Tests

```yaml
- name: Run tests
  id: Run_Test
  run: |
    export BROWSER=${{ github.event.inputs.Browser }}
    export HEADLESS=true
    export ENV=${{ github.event.inputs.Environment }}
    mvn test -DsuiteXmlFile=testng.xml
```

#### 7. Send Result via Email

```yaml
- name: Send result via email
  if: always()
  run: |
    export EMAIL=${{ github.event.inputs.Email }}
    export SUBJECT="Complete Execution for ${{ github.event.inputs.Environment }} Environment on ${{ github.event.inputs.Browser }} Browser"
    mvn exec:java
  env:
    EMAIL_USER: ${{ secrets.SENDER_EMAIL }}
    EMAIL_PASSWORD: ${{ secrets.SENDER_APP_PASSWORD }}
```

#### 8. Re-run Failed Tests

```yaml
- name: Re-run failed tests
  id: Re_Run_Test
  if: ${{ failure() && steps.Run_Test.outcome == 'failure' }}
  run: |
    export BROWSER=${{ github.event.inputs.Browser }}
    export HEADLESS=true
    export Is_Flaky=true
    export ENV=${{ github.event.inputs.Environment }}
    mvn test -DsuiteXmlFile=target/surefire-reports/testng-failed.xml
```

#### 9. Re-send Result via Email

```yaml
- name: Re-send result via email
  if: ${{ failure() && steps.Run_Test.outcome == 'failure' }}
  run: |
    export EMAIL=${{ github.event.inputs.Email }}
    export SUBJECT="Re-run Failed Test Cases for ${{ github.event.inputs.Environment }} Environment on ${{ github.event.inputs.Browser }} Browser"
    mvn exec:java
  env:
    EMAIL_USER: ${{ secrets.SENDER_EMAIL }}
    EMAIL_PASSWORD: ${{ secrets.SENDER_APP_PASSWORD }}
```

### Secrets

The workflow requires the following secrets to be set in the repository settings:

- **SENDER_EMAIL**: The email address used to send the test results.
- **SENDER_APP_PASSWORD**: The application password for the sender email.

## Contact Information
Ravi (Email: ravi007.rl@gmail.com)