package com.selenium.testng.elite.webTest;

import com.selenium.testng.elite.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class LoginTest extends BaseTest {

  @Test
  public void LoginTest_VerifyThatUserIsAbleToLogin() {

    log.info("Browser opened: " + environmentConfig.getBrowser().toString());
    driver.get("https://www.baeldung.com/spring-boot-properties-env-variables");
    fail("Fail the test case");
    log.info("This is my step ");
  }
}
