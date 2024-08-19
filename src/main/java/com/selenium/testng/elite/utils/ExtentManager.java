package com.selenium.testng.elite.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
  private static ExtentReports extent;

  public static synchronized ExtentReports getInstance() {
    if (extent == null) {
      extent = new ExtentReports();
      ExtentSparkReporter sparkReporter = new ExtentSparkReporter(FileHelper.getPathForReport());
      extent.attachReporter(sparkReporter);
    }
    return extent;
  }
}
