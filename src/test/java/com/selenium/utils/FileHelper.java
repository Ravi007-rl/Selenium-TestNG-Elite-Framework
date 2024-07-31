package com.selenium.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FileHelper {

  public static String getFilePath(String testName) {
    return System.getProperty("user.dir") + "/target/test-results/" + testName + "/screenshot.png";
  }

  public static String getEncodedPath(String path) {
    var encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8);
    return encodedPath.replace("%2F", "/").replace("%20", " ");
  }

  public static String getLogFilePath(String testName) {
    return System.getProperty("user.dir") + "/target/test-results/" + testName + "/logfile.log";
  }
}
