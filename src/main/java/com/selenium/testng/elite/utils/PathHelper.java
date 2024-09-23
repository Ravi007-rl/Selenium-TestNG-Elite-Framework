package com.selenium.testng.elite.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PathHelper {

  public static String screenShotFilePath(String testName) {
    return System.getProperty("user.dir") + "/target/test-results/" + testName + "/screenshot.png";
  }

  public static String getEncodedPathForScreenShot(String path) {
    var encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8);
    return encodedPath.replace("%2F", "/").replace("%20", " ");
  }

  public static String getPathForReport() {
    return System.getProperty("user.dir") + "/target/html-report/SparkReport.html";
  }

  public static String getConfigFilePath() {
    return System.getProperty("user.dir") + "/src/main/resources/config/";
  }

  public static String getTestResultTextFilePath() {
    return System.getProperty("user.dir") + "target/surefire-reports/TestSuite.txt";
  }
}
