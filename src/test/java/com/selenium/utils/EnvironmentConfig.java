package com.selenium.utils;

import com.selenium.testng.elite.enums.BrowserName;
import com.selenium.testng.elite.enums.PlatformName;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

import java.util.Locale;

@Getter
public class EnvironmentConfig {

  private final BrowserName browser;
  private final PlatformName platform;
  private final boolean headless;

  public EnvironmentConfig(Dotenv dotenv) {
    browser = BrowserName.valueOf(dotenv.get("BROWSER").toUpperCase(Locale.ROOT));
    platform = PlatformName.valueOf(dotenv.get("PLATFORM").toUpperCase(Locale.ROOT));
    headless = Boolean.parseBoolean(dotenv.get("HEADLESS"));
  }
}
