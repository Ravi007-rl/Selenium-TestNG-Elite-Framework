package com.selenium.testng.elite.webTest;

import com.selenium.pageObjectModel.webPageObject.homePage.HomePage;
import com.selenium.testng.elite.BaseTest;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {

  @Test
  public void VerifyTharCurrencyDropdownWorkingProperly() throws InterruptedException {
    var homePage = new HomePage(driver);

    log.get().info("Click on 'Currency' dropdown");
    homePage.clickOnCurrencyDropdown();
  }
}
