package com.selenium.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;

public class Log {
  private final Logger logger;
  private int stepCounter;
  private final String testCaseName;

  public Log(String testCaseName) {
    this.testCaseName = testCaseName;
    this.stepCounter = 1;
    String logFilePath = "target/test-results/" + testCaseName + "/logfile.log";
    configureLogger(testCaseName, logFilePath);
    this.logger = LogManager.getLogger(testCaseName);
    logTestCaseName();
  }

  private void logTestCaseName() {
    logger.info("Test Case: {}", testCaseName);
  }

  public void info(String message) {
    logger.info("Step {}: {}", stepCounter, message);
    stepCounter++;
  }

  public void info() {
    logger.info("Test Result: Passed..!!!");
  }

  public void error(Throwable message) {
    logger.error(message);
    stepCounter++;
  }

  private void configureLogger(String testCaseName, String logFilePath) {
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    Configuration config = context.getConfiguration();

    // Update the pattern to exclude the logger and thread names
    PatternLayout layout =
        PatternLayout.newBuilder()
            .withPattern("%d %p %m%n") // Modified pattern: timestamp, level, message
            .build();

    RollingFileAppender fileAppender =
        RollingFileAppender.newBuilder()
            .setName(testCaseName + "FileAppender")
            .setLayout(layout)
            .withFileName(logFilePath)
            .withFilePattern(logFilePath + ".%d{yyyy-MM-dd}.%i")
            .withPolicy(
                CompositeTriggeringPolicy.createPolicy(
                    TimeBasedTriggeringPolicy.newBuilder().build(),
                    SizeBasedTriggeringPolicy.createPolicy("10MB")))
            .withAppend(false) // Ensure logs are not appended to existing files
            .build();

    fileAppender.start();
    config.addAppender(fileAppender);

    // Adding ConsoleAppender
    ConsoleAppender consoleAppender =
        ConsoleAppender.newBuilder()
            .setName(testCaseName + "ConsoleAppender")
            .setLayout(layout)
            .build();

    consoleAppender.start();
    config.addAppender(consoleAppender);

    LoggerConfig loggerConfig =
        new LoggerConfig(testCaseName, org.apache.logging.log4j.Level.INFO, false);
    loggerConfig.addAppender(fileAppender, null, null);
    loggerConfig.addAppender(consoleAppender, null, null);
    config.addLogger(testCaseName, loggerConfig);

    context.updateLoggers();
  }
}
