package com.selenium.testng.elite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.selenium.testng.elite.enums.EnvironmentType;
import com.selenium.testng.elite.utils.Constant;
import com.selenium.testng.elite.utils.ExtentManager;
import com.selenium.testng.elite.utils.Log;
import com.selenium.testng.elite.utils.PathHelper;
import com.selenium.utils.DriverFactory;
import com.selenium.utils.EnvironmentConfig;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

  protected WebDriver driver;
  protected static EnvironmentConfig environmentConfig;
  protected ThreadLocal<Log> log = new ThreadLocal<>();
  protected ExtentReports extent;
  protected ExtentTest extentTest;
  protected static String baseUrl;

  @BeforeSuite
  public void beforeSuite() {
    var dotenv = Dotenv.configure().ignoreIfMissing().load();
    environmentConfig = new EnvironmentConfig(dotenv);
    baseUrl =
        environmentConfig.getEnvironment() == EnvironmentType.TEST
            ? Constant.TEST_URL
            : Constant.STAGE_URL;
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
      captureScreenshot(result);
      log.get().error(result.getThrowable());
      attachScreenshotToReport(result);
    } else log.get().info();
    extent.flush();
    driver.quit();
  }

  private void setUpReportAndLogger(ITestResult result) {
    extent = ExtentManager.getInstance();
    String testCaseName = result.getMethod().getMethodName();
    extentTest = extent.createTest(testCaseName, "Description of " + testCaseName);
    log.set(new Log(testCaseName, extentTest));
  }

  private void captureScreenshot(ITestResult result) throws IOException {
    var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    var screenShotPath = PathHelper.getFilePath(result.getName());
    FileUtils.copyFile(screenshot, new File(screenShotPath));
    FileUtils.copyFile(screenshot, new File(PathHelper.getPathForImageReport(result.getName())));
    System.out.println("Screenshot: file://" + PathHelper.getEncodedPath(screenShotPath));
  }

  private void attachScreenshotToReport(ITestResult result) {
    extentTest.fail(
        MediaEntityBuilder.createScreenCaptureFromPath(result.getName() + ".png").build());
  }
}
