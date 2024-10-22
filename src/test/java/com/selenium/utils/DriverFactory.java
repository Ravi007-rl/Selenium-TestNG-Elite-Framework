package com.selenium.utils;

import com.selenium.testng.elite.enums.PlatformName;
import com.selenium.testng.elite.utils.PathHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

  public static WebDriver getDriver(EnvironmentConfig environmentConfig) throws Exception {

    WebDriver driver = null;
    if (environmentConfig.getPlatform() == PlatformName.WEB) {

      switch (environmentConfig.getBrowser()) {
        case CHROME -> {
          var options = new ChromeOptions();
          options.addArguments("--disable-notifications");
          options.addArguments("--no-sandbox");
          options.addArguments("--disable-dev-shm-usage");
          options.addArguments("--disable-gpu");
          if (environmentConfig.isHeadless()) options.addArguments("--headless");
          driver = new ChromeDriver(options);
        }
        case FIREFOX -> driver = setUpFireFoxDriver(environmentConfig);
        case EDGE -> {
          var options = new EdgeOptions();
          options.addArguments("--disable-notifications");
          if (environmentConfig.isHeadless()) options.addArguments("--headless");
          driver = new EdgeDriver(options);
        }
        case SAFARI -> driver = new SafariDriver();
        default -> throw new Exception("Please select valid browser");
      }
      driver.manage().window().maximize();
    }
    return driver;
  }

  private static WebDriver setUpFireFoxDriver(EnvironmentConfig environmentConfig) {

    // Setup firefox profile
    var profile = new FirefoxProfile();
    profile.setPreference("browser.download.dir", PathHelper.getDownloadFolderPath());
    profile.setPreference("browser.download.folderList", 2);
    profile.setPreference("pdfjs.disabled", true);
    profile.setPreference("browser.download.manager.overwriteOnExist", true);
    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");

    var options = new FirefoxOptions();
    options.setProfile(profile);
    options.addArguments("--disable-notifications");
    if (environmentConfig.isHeadless()) options.addArguments("--headless");
    return new FirefoxDriver(options);
  }
}
