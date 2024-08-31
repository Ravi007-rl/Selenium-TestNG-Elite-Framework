package com.selenium.pageObjectModel.webPageObject.productPage;

import com.selenium.pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends WebPageBase {
  public ProductPage(WebDriver driver) {
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