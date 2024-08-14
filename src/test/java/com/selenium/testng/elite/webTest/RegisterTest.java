package com.selenium.testng.elite.webTest;

import com.selenium.testng.elite.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class RegisterTest extends BaseTest {

  @Test
  public void RegisterTest_verifyThatUserAbleToNavigateToSite() {
    log.info("Browser opened: " + environmentConfig.getBrowser().toString());
    driver.get("https://www.baeldung.com/spring-boot-properties-env-variables");
    fail("Fail the test case");
    log.info("This is my step ");
  }

  @Test
  public void RegisterTest_test2() {
    log.info("Browser opened: " + environmentConfig.getBrowser().toString());
    driver.get("https://www.baeldung.com/spring-boot-properties-env-variables");
    log.info("This is my step ");
    Assert.assertTrue(true);
  }

  @Test
  public void RegisterTest_test3() {
    log.info("Browser opened: " + environmentConfig.getBrowser().toString());
    driver.get("https://www.baeldung.com/spring-boot-properties-env-variables");
    log.info("This is my step ");
  }
}
