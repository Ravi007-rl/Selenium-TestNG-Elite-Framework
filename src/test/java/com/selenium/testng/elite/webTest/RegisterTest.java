package com.selenium.testng.elite.webTest;

import com.selenium.testng.elite.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

  @Test
  public void RegisterTest_verifyThatUserAbleToNavigateToSite() {
    log.get().info("This is my step ");
    driver.get("https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework/pull/3/files");
  }

  @Test
  public void RegisterTest_test2() {
    log.get().info("This is my step ");
    driver.get("https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework/pull/3/files");
    Assert.fail();
  }

  @Test
  public void RegisterTest_test3() {
    log.get().info("This is my step ");
    driver.get("https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework/");
    Assert.fail();
  }
}
