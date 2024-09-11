package com.selenium.pageObjectModel.webPageObject;

import com.selenium.elementHelper.web.SeleniumHelper;
import com.selenium.pageObjectModel.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebPageBase extends BasePageObject {

  protected SeleniumHelper seleniumHelper;

  protected WebPageBase(WebDriver driver) {
    super(driver);
    seleniumHelper = new SeleniumHelper(driver);
  }

  // Common Locator
  private final By logoLink = By.cssSelector("div#logo a[href*='home']");

  public String getPageTitle() {
    return seleniumHelper.getPageTitle();
  }

  public void clickOnLogoLink() throws InterruptedException {
    seleniumHelper.clickOnElementUsingJavaScript(logoLink);
    seleniumHelper.waitTillPageLoadedProperly();
  }
}
