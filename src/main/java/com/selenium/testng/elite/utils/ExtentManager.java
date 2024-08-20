package com.selenium.testng.elite.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentManager {
  private static ExtentReports extent;

  public static synchronized ExtentReports getInstance() {
    if (extent == null) {
      extent = new ExtentReports();
      ExtentSparkReporter sparkReporter =
          new ExtentSparkReporter(PathHelper.getPathForReport())
              .viewConfigurer()
              .viewOrder()
              .as(
                  new ViewName[] {
                    ViewName.DASHBOARD, ViewName.TEST, ViewName.EXCEPTION,
                  })
              .apply();
      extent.attachReporter(sparkReporter);
    }
    return extent;
  }
}
