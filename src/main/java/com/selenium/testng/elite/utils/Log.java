package com.selenium.testng.elite.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class Log {
  private final Logger logger;
  private int stepCounter = 1;
  private final String testCaseName;
  private final ExtentTest extentTest;

  public Log(String testCaseName, ExtentTest extentTest) {
    this.testCaseName = testCaseName;
    this.extentTest = extentTest;
    this.logger = configureLogger(testCaseName);
    logTestCaseName();
  }

  private synchronized Logger configureLogger(String testCaseName) {
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    Configuration config = context.getConfiguration();
    String logFilePath =
        Paths.get("target", "test-results", testCaseName, "logfile.log").toString();

    PatternLayout layout =
        PatternLayout.newBuilder().withPattern("%d [%t] %-5level: %msg%n%throwable").build();

    FileAppender fileAppender =
        FileAppender.newBuilder()
            .setName(testCaseName + "FileAppender")
            .withFileName(logFilePath)
            .setLayout(layout)
            .setImmediateFlush(true)
            .build();
    fileAppender.start();

    ConsoleAppender consoleAppender =
        ConsoleAppender.newBuilder()
            .setName(testCaseName + "ConsoleAppender")
            .setLayout(layout)
            .build();
    consoleAppender.start();

    LoggerConfig loggerConfig =
        new LoggerConfig(testCaseName, org.apache.logging.log4j.Level.INFO, false);
    loggerConfig.addAppender(fileAppender, null, null);
    loggerConfig.addAppender(consoleAppender, null, null);
    config.addLogger(testCaseName, loggerConfig);
    context.updateLoggers();

    return LogManager.getLogger(testCaseName);
  }

  private void logTestCaseName() {
    logger.info("Test Case: {}", testCaseName);
    extentTest.log(Status.INFO, "Test Case: " + testCaseName);
  }

  public void info(String message) {
    logger.info("Step {}: {}", stepCounter++, message);
    extentTest.log(Status.INFO, "Step " + (stepCounter - 1) + ": " + message);
  }

  public void info() {
    logger.info("Test Passed!!!");
    extentTest.log(Status.PASS, "Test Passed!!!");
  }

  public void error(Throwable message) {
    logger.error(message);
    extentTest.log(Status.FAIL, message);
  }
}
