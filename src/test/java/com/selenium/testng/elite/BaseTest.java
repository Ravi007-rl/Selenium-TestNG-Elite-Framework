package com.selenium.testng.elite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.selenium.testng.elite.utils.*;
import com.selenium.utils.DriverFactory;
import com.selenium.utils.EnvironmentConfig;
import elementHelper.web.FileHelper;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

  protected WebDriver driver;
  protected static EnvironmentConfig environmentConfig;
  protected ThreadLocal<Log> log = new ThreadLocal<>();
  protected ExtentReports extent;
  protected ExtentTest extentTest;
  protected static String baseUrl;
  private static final List<String> failedTests = new ArrayList<>();
  private static final List<String> passedTests = new ArrayList<>();

  @BeforeSuite
  public void beforeSuite() {
    environmentConfig = new EnvironmentConfig();
    baseUrl = Constant.BASE_URL;
  }

  @BeforeMethod
  public void setUp(ITestResult result) throws Exception {
    setUpReportAndLogger(result);
    driver = DriverFactory.getDriver(environmentConfig);
    log.get().info("Browser opened: " + environmentConfig.getBrowser().toString());

    log.get().info("Navigate to: " + baseUrl);
    driver.get(baseUrl);
  }

  @AfterMethod
  public void tearDown(ITestResult result) throws IOException {
    if (result.getStatus() == ITestResult.FAILURE) {
      log.get().error(result.getThrowable());
      captureScreenshotAndAttachScreenshotToReport(result);
      failedTests.add(result.getName());
    } else {
      log.get().info();
      passedTests.add(result.getName());
    }
    extent.flush();
    driver.quit();
  }

  @AfterSuite
  public void afterSuite() {
    FileHelper.deleteAllFiles();
    ResultMaker.CreateFileForResult(failedTests, passedTests);
    openExtentReport(environmentConfig.isWantToOpenReports());
  }

  private void setUpReportAndLogger(ITestResult result) {
    extent = ExtentManager.getInstance();
    String testCaseName = result.getMethod().getMethodName();
    extentTest = extent.createTest(testCaseName, "Description of " + testCaseName);
    log.set(new Log(testCaseName, extentTest));
  }

  private void captureScreenshotAndAttachScreenshotToReport(ITestResult result) throws IOException {
    var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    var screenShotPath = PathHelper.screenShotFilePath(result.getName());

    FileUtils.copyFile(screenshot, new File(screenShotPath));
    extentTest.fail(
        MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
    System.out.println(
        "Screenshot: file://" + PathHelper.getEncodedPathForScreenShot(screenShotPath));
  }

  private void openExtentReport(boolean wantToOpenReports) {
    if (wantToOpenReports) {
      try {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new File(PathHelper.getPathForReport()).toURI());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
