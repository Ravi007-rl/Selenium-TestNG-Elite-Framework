# Selenium TestNG Elite Framework

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Installation Instructions](#installation-instructions)
4. [Usage](#usage)
5. [Project Structure](#project-structure)
6. [What Framework Users Need to Do](#what-framework-users-need-to-do)
7. [Contributing](#contributing)
8. [Contact Information](#contact-information)

## Overview
The Selenium TestNG Elite Framework is a robust and scalable test automation framework designed to simplify the process of writing and running automated tests for web applications. This framework leverages [Selenium WebDriver](https://www.selenium.dev/documentation/) for browser automation and [TestNG](https://testng.org/) for test management.

The framework is designed to be user-friendly, allowing users to read 2-3 pre-written test cases and start writing their own test cases without starting from scratch.

## Features
- **Environment Configuration**: Uses [Dotenv](https://www.dotenv.org/) for managing environment variables. You can change browsers and switch between headed and headless modes from the command line or the `.env` file.
- **Multiple Browser Support**: A browser factory is created so when you pass the browser name from the command line or `.env` file, it will launch that browser.
- **Parallel Execution**: Run tests in parallel to save time. This feature works at the class level, and the thread count is hard-coded to 5 in the `testng.xml` file. You can change it, or in the future, it will be made dynamic so you can pass this from the command line.
- **Screenshot Capture**: Automatically captures screenshots on test failure and stores them in `target/test-result/{testcase name}/screenshot.png` format.
- **Logs**: Integrated with [Log4j](https://logging.apache.org/log4j/2.x/) for better logging of test case steps with auto-increment steps. Users don’t have to write “Step 1: Navigate to base URL”. Simply write “Navigate to base URL” and the step count is auto-added. Logs are displayed in the console as well as stored in `target/test-result/{testcase name}/logfile.log` format.
- **Supports Multiple Environments**: The framework supports multiple environments like Dev, QA, and Prod.
- **Extent Report**: Integrated with ExtentReports to generate beautiful HTML reports.
- **Random Data Generation**: Uses the [Faker library](https://faker.readthedocs.io/en/master/) for random data generation. There is also a `RandomHelper` class that gives you random elements from a list and generates random strings.
- **Element Helper Classes**:
    - **WaitHelper**: Uses explicit waits to get elements in a particular state like visible or clickable.
    - **SeleniumHelper**: Improves interaction with elements to remove flakiness. For example, `driver.findElement(By.xpath()).isDisplayed()` will return `true` if the element is displayed, but if not, it throws a `NoSuchElementException`. The helper method will return `false` instead.
    - **JavaScriptHelper**: Interacts with elements using JavaScript.
- **Integrated Surefire Plugin**: Using this plugin, you can run test cases from the CLI. It also generates a failed test cases XML file when any test case fails, so you can run only the failed test cases.
- **Debug Mode**: If you set `DEBUG=true` in the `.env` file, elements will be bordered in red before interaction.

## Installation Instructions
1. **Clone the repository:**
    ```sh
    git clone https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework.git
    cd Selenium-TestNG-Elite-Framework
    ```

2. **Install dependencies:**
    ```sh
    mvn clean install
    ```

3. **Configure environment variables:**
   Create a `.env` file in the root directory and add the following:
    ```env
    BROWSER=chrome
    PLATFORM=web
    HEADLESS=false
    ```

## Usage
### Running Tests
To run the tests, use the following Maven command:
```sh
mvn test -DsuiteXmlFile="testng.xml"
```

## Project Structure
```
Selenium-TestNG-Elite-Framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── helpers/
│   │   │           ├── pages/
│   │   │           └── utils/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── tests/
│       │           └── data/
│       └── resources/
├── .env
├── pom.xml
└── README.md
```

## What Framework Users Need to Do
- **Create DataObject**: The DataObject defines the shape of the object. For example, for a login form requiring email and password fields, create a class for that and shape the object accordingly.
- **DataFactory**: Use this to create data for your test cases.
- **Page Object Model**: The backbone of the framework. Use the By locator strategy. Users just need to create By locators and then interact with elements using the enhanced SeleniumHelper class.
- **Test File**: Where you can put your test cases.

For reference, 20 test cases have been automated to demonstrate how anyone can automate test cases.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Contact Information
Ravi (Email: ravi007.rl@gmail.com)
