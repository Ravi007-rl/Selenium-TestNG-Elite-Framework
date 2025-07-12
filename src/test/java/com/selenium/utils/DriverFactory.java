package com.selenium.utils;

import com.selenium.testng.elite.enums.PlatformName;
import com.selenium.testng.elite.utils.PathHelper;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

  public static WebDriver getDriver(EnvironmentConfig environmentConfig) throws Exception {

    WebDriver driver = null;
    if (environmentConfig.getPlatform() == PlatformName.WEB) {

      switch (environmentConfig.getBrowser()) {
        case CHROME -> driver = setUpChromeDriver(environmentConfig);
        case FIREFOX -> driver = setUpFireFoxDriver(environmentConfig);
        case EDGE -> driver = setEdgeDriver(environmentConfig);
        case SAFARI -> driver = new SafariDriver();
        default -> throw new Exception("Please select valid browser");
      }
      if (environmentConfig.isHeadless())
        driver.manage().window().setSize(new Dimension(1920, 1080));
      else
        driver.manage().window().maximize();
    }
    return driver;
  }

  private static WebDriver setUpChromeDriver(EnvironmentConfig environmentConfig) {
    var options = new ChromeOptions();

    // Download file settings
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("download.default_directory", PathHelper.getDownloadFolderPath());
    prefs.put("download.prompt_for_download", false);
    prefs.put("plugins.always_open_pdf_externally", true);
    options.setExperimentalOption("prefs", prefs);

    options.addArguments("--disable-notifications");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu");
    if (environmentConfig.isHeadless()) options.addArguments("--headless", "start-maximized");
    return new ChromeDriver(options);
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
    if (environmentConfig.isHeadless()) options.addArguments("--headless", "--width=1920", "--height=1080");
    return new FirefoxDriver(options);
  }

  private static WebDriver setEdgeDriver(EnvironmentConfig environmentConfig) {

    var options = new EdgeOptions();
    Map<String, Object> edgePrefs = new HashMap<>();
    edgePrefs.put("download.default_directory", PathHelper.getDownloadFolderPath());
    edgePrefs.put("download.prompt_for_download", false);
    edgePrefs.put("plugins.always_open_pdf_externally", true);
    options.setExperimentalOption("prefs", edgePrefs);

    options.addArguments("--disable-notifications");
    if (environmentConfig.isHeadless()) options.addArguments("--headless", "start-maximized");
    return new EdgeDriver(options);
  }
}
