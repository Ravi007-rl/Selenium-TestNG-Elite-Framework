package com.selenium.pageObjectModel.webPageObject.homePage;

import com.selenium.pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends WebPageBase {
  public HomePage(WebDriver driver) {
    super(driver);
  }

  private final By currencyDropdown =
      By.xpath("//span[.='Currency']");
  private final By myAccountDropdown =
      By.xpath("//span[.='My Account']/ancestor::div[@class='dropdown']");
  private final By wishListIcon = By.cssSelector("a[title*='Wish List']");
  private final By shoppingCartIcon = By.cssSelector("a[title='Shopping Cart']");
  private final By searchBar = By.name("search");
  private final By logoLink = By.cssSelector("//img[@title='Your Store']/parent::a");
  private final By checkoutButton = By.cssSelector("a[title='Checkout']");
  private final By getCurrentCurrency = By.xpath("//span[.='Currency']/preceding-sibling::strong");

  public void clickOnCurrencyDropdown() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(currencyDropdown);
  }

  public void clickOnMyAccountDropdown() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(myAccountDropdown);
  }

  public void clickOnWishListIcon() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(wishListIcon);
  }

  public void clickOnShoppingCartIcon() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(shoppingCartIcon);
  }

  public void enterTextInSearchBar(String productName) throws InterruptedException {
    seleniumHelper.scrollAndEnterText(searchBar, productName);
  }

  public void clickOnLogoLink() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(logoLink);
  }

  public void clickOnCheckoutButton() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(checkoutButton);
  }

  public String getCurrentCurrency(){
    return seleniumHelper.getText(getCurrentCurrency);
  }
}