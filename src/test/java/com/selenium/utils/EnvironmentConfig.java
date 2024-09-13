package com.selenium.utils;

import com.selenium.testng.elite.enums.BrowserName;
import com.selenium.testng.elite.enums.EnvironmentType;
import com.selenium.testng.elite.enums.PlatformName;
import com.selenium.testng.elite.utils.Constant;
import com.selenium.testng.elite.utils.PathHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Locale;
import lombok.Getter;

@Getter
public class EnvironmentConfig {

  private final BrowserName browser;
  private final PlatformName platform;
  private final boolean headless;
  private final EnvironmentType environment;

  public EnvironmentConfig() {

    // Global config
    var globalDotenv = Dotenv.configure().ignoreIfMissing().load();
    browser = BrowserName.valueOf(globalDotenv.get("BROWSER").toUpperCase(Locale.ROOT));
    platform = PlatformName.valueOf(globalDotenv.get("PLATFORM").toUpperCase(Locale.ROOT));
    environment = EnvironmentType.valueOf(globalDotenv.get("ENV").toUpperCase(Locale.ROOT));

    // read data according to environment
    var environmentBasedConfig =
        Dotenv.configure()
            .directory(PathHelper.getConfigFilePath())
            .filename(environment.toString().toLowerCase() + ".env")
            .load();

    // Setting up constants variable according to environment
    Constant.loadConstants(environmentBasedConfig);

    headless = Boolean.parseBoolean(globalDotenv.get("HEADLESS"));
  }
}
