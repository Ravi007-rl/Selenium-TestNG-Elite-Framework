package com.selenium.pageObjectModel.webPageObject.forgotPasswordPage;

import com.selenium.pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends WebPageBase {

  public ForgotPasswordPage(WebDriver driver) {
    super(driver);
  }

  private final By pageHeader = By.cssSelector("h1");

  public boolean isPageHeaderDisplayed() {
    return seleniumHelper.isElementDisplayed(pageHeader);
  }

  public String getPageHeader() {
    return seleniumHelper.getText(pageHeader);
  }
}
