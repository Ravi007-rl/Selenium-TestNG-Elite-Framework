package com.selenium.pageObjectModel.webPageObject.searchPage;

import com.selenium.pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends WebPageBase {
  public SearchPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private final By pageHeader = By.cssSelector("h1");

  public String getPageTitle() {
    return seleniumHelper.getPageTitle();
  }

  public String getPageHeader() {
    return seleniumHelper.getText(pageHeader);
  }
}