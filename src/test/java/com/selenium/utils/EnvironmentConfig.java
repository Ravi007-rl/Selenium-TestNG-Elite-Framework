package com.selenium.utils;

import com.selenium.testng.elite.enums.BrowserName;
import com.selenium.testng.elite.enums.EnvironmentType;
import com.selenium.testng.elite.enums.PlatformName;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Locale;
import lombok.Getter;

@Getter
public class EnvironmentConfig {

  private final BrowserName browser;
  private final PlatformName platform;
  private final boolean headless;
  private final EnvironmentType environment;

  public EnvironmentConfig(Dotenv dotenv) {
    browser = BrowserName.valueOf(dotenv.get("BROWSER").toUpperCase(Locale.ROOT));
    platform = PlatformName.valueOf(dotenv.get("PLATFORM").toUpperCase(Locale.ROOT));
    headless = Boolean.parseBoolean(dotenv.get("HEADLESS"));
    environment = EnvironmentType.valueOf(dotenv.get("ENV").toUpperCase(Locale.ROOT));
  }
}
