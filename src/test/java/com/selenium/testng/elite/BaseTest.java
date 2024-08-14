package com.selenium.testng.elite;

import com.selenium.utils.DriverFactory;
import com.selenium.utils.EnvironmentConfig;
import com.selenium.utils.FileHelper;
import com.selenium.utils.Log;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;

public class BaseTest {

  protected WebDriver driver;
  protected static EnvironmentConfig environmentConfig ;
  protected Log log;

  @BeforeSuite
  public void beforeSuite() {
    var dotenv = Dotenv.configure().ignoreIfMissing().load();
    environmentConfig = new EnvironmentConfig(dotenv);
  }

  @BeforeMethod
  public void setUp(ITestResult result) throws Exception {
    String testCaseName = result.getMethod().getMethodName();
    log = new Log(testCaseName);
    driver = DriverFactory.getDriver(environmentConfig);
  }

  @AfterMethod
  public void tearDown(ITestResult result) throws IOException {
    if (result.getStatus() == ITestResult.FAILURE) {
      var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      var screenShotPath = FileHelper.getFilePath(result.getName());
      FileUtils.copyFile(screenshot, new File(screenShotPath));
      System.out.println("Screenshot: file://" + FileHelper.getEncodedPath(screenShotPath));
      log.error(result.getThrowable());
    } else {
      log.info();
    }
    driver.quit();
  }
}
