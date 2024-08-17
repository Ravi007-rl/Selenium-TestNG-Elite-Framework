package com.selenium.testng.elite.webTest;

import com.selenium.testng.elite.BaseTest;
import org.testng.annotations.Test;

public class MyTest extends BaseTest {

  @Test
  public void MyTest_verifyThatUserAbleToNavigateToSite() {
    log.get().info("This is my step ");
    driver.get("https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework/pull/3/files");
  }

  @Test
  public void MyTest_testByRavi_1() {
    log.get().info("This is my step ");
    driver.get("https://github.com/Ravi007-rl/Selenium-TestNG-Elite-Framework/pull/3/files");
  }

  @Test
  public void MyTest_test3() {
    log.get().info("This is my step ");
  }
}
