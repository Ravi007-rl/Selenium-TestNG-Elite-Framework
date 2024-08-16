package com.selenium.testng.elite.webTest;


import com.selenium.testng.elite.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

  @Test
  public void LoginTest_VerifyThatUserIsAbleToLogin() {
    log.get().info("This is my step ");
    driver.get("https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework/pull/3/files");
  }

  @Test
  public void LoginTest_testByRavi() {
    log.get().info("This is my step ");
    driver.get("https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework/pull/3/files");
  }
}
