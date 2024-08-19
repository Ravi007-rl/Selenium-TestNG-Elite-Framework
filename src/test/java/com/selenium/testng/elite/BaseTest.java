package com.selenium.testng.elite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.selenium.testng.elite.utils.ExtentManager;
import com.selenium.testng.elite.utils.FileHelper;
import com.selenium.testng.elite.utils.Log;
import com.selenium.utils.*;
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
  protected ExtentTest test;

  @BeforeSuite
  public void beforeSuite() {
    var dotenv = Dotenv.configure().ignoreIfMissing().load();
    environmentConfig = new EnvironmentConfig(dotenv);
  }

  @BeforeMethod
  public void setUp(ITestResult result) throws Exception {
    extent = ExtentManager.getInstance();
    String testCaseName = result.getMethod().getMethodName();
    test = extent.createTest(testCaseName, "Description of " + testCaseName);
    log.set(new Log(testCaseName, test));
    driver = DriverFactory.getDriver(environmentConfig);
    log.get().info("Browser opened: " + environmentConfig.getBrowser().toString());
    driver.get("https://www.baeldung.com/spring-boot-properties-env-variables");
  }

  @AfterMethod
  public void tearDown(ITestResult result) throws IOException {
    if (result.getStatus() == ITestResult.FAILURE) {
      var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

      /*Two screenshot saved.
      1. one for log file
      2. for html report
       */
      var screenShotPath = FileHelper.getFilePath(result.getName());
      FileUtils.copyFile(screenshot, new File(screenShotPath));
      FileUtils.copyFile(screenshot, new File(FileHelper.getPathForImageReport(result.getName())));

      // File path for display that screenshot in console
      System.out.println("Screenshot: file://" + FileHelper.getEncodedPath(screenShotPath));

      log.get().error(result.getThrowable());

      // Attach screenshot to ExtentReports
      test.fail(MediaEntityBuilder.createScreenCaptureFromPath(result.getName() + ".png").build());
    } else log.get().info();

    extent.flush();
    driver.quit();
  }
}
