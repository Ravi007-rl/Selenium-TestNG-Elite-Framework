package com.selenium.pageObjectModel.webPageObject.homePage;

import com.selenium.pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class HomePage extends WebPageBase {
  public HomePage(WebDriver driver) {
    super(driver);
  }

  private final By currencyDropdown = By.xpath("//span[.='Currency']");
  private final By myAccountDropdown = By.xpath("//span[.='My Account']");
  private final By myAccountOptions =
      By.xpath("//span[.='My Account']/ancestor::a/following-sibling::ul//a");
  private final By registrationOption =
      By.xpath("//ul[contains(@class,'dropdown-menu')]//a[.='Register']");
  private final By loginOption = By.xpath("//ul[contains(@class,'dropdown-menu')]//a[.='Login']");
  private final By wishListIcon = By.cssSelector("a[title*='Wish List']");
  private final By shoppingCartIcon = By.cssSelector("a[title='Shopping Cart']");
  private final By searchBar = By.name("search");
  private final By searchButton = By.xpath("//div[@id='search']//button");
  private final By logoLink = By.cssSelector("//img[@title='Your Store']/parent::a");
  private final By checkoutButton = By.cssSelector("a[title='Checkout']");
  private final By currencySymbol = By.xpath("//span[.='Currency']/preceding-sibling::strong");
  private final By cartTotalText = By.id("cart-total");
  private final By currencyDropdownOptions = By.cssSelector(".currency-select");
  private final By allProductPrice = By.className("price");
  private final By allProductNames = By.xpath("//div[contains(@class,'product-thumb')]//h4/a");
  private final By alertMessage = By.xpath("//div[contains(@class,'alert-success')]");
  private final By alertMessageCrossButton = By.cssSelector("button.close");

  // Dynamic locator
  private static By currencyOption(String currencyName) {
    return By.xpath("//button[contains(text(),'" + currencyName + "')]");
  }

  private static By wishListButton(String productName) {
    return By.xpath(
        "//a[text()='"
            + productName
            + "']/ancestor::div[contains(@class,'product-thumb')]//button[@data-original-title='Add to Wish List']");
  }

  private static By addToCartButton(String productName) {
    return By.xpath(
        "//a[text()='"
            + productName
            + "']/ancestor::div[contains(@class,'product-thumb')]//button/span[text()='Add to Cart']");
  }

  private static By productName(String productName) {
    return By.xpath("//a[text()='" + productName + "']");
  }

  public void clickOnCurrencyDropdown() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(currencyDropdown);
  }

  public void clickOnMyAccountDropdown() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(myAccountDropdown);
  }

  public boolean isMyAccountDropdownDisplayed() {
    return seleniumHelper.isElementDisplayed(myAccountDropdown);
  }

  public List<String> getMyAccountDropdownOption() {
    return seleniumHelper.getAllElementsText(myAccountOptions);
  }

  public void clickOnRegisterOption() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(registrationOption);
  }

  public void clickOnLoginOption() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(loginOption);
  }

  public void clickOnWishListIcon() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(wishListIcon);
  }

  public boolean IsWishListIconDisplayed() throws InterruptedException {
    return seleniumHelper.isElementDisplayed(wishListIcon);
  }

  public void clickOnShoppingCartIcon() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(shoppingCartIcon);
  }

  public boolean isShoppingCartIconDisplayed() throws InterruptedException {
    return seleniumHelper.isElementDisplayed(shoppingCartIcon);
  }

  public void enterTextInSearchBar(String productName) throws InterruptedException {
    seleniumHelper.scrollAndEnterText(searchBar, productName);
  }

  public void clickOnSearchButton() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(searchButton);
    seleniumHelper.waitTillPageLoadedProperly();
  }

  public void clickOnLogoLink() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(logoLink);
  }

  public void clickOnCheckoutButton() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(checkoutButton);
  }

  public String getCurrentCurrencySymbol() {
    return seleniumHelper.getText(currencySymbol);
  }

  public String getCartTotalText() {
    return seleniumHelper.getText(cartTotalText);
  }

  public List<String> getCurrencyDropdownOptionList() {
    return seleniumHelper.getAllElementsText(currencyDropdownOptions);
  }

  public List<String> getAllProductPrice() {
    return seleniumHelper.getAllElementsText(allProductPrice);
  }

  public void clickOnCurrency(String currency) throws InterruptedException {
    seleniumHelper.scrollAndClickOn(currencyOption(currency));
    seleniumHelper.waitTillPageLoadedProperly();
  }

  public List<String> getAllProductNamesList() {
    return seleniumHelper.getAllElementsText(allProductNames);
  }

  public void clickOnWishListButton(String productName) throws InterruptedException {
    seleniumHelper.scrollAndClickOn(wishListButton(productName));
  }

  public boolean isAlertMessageDisplayed() {
    return seleniumHelper.isElementDisplayed(alertMessage);
  }

  public String getAlertMessage() {
    return seleniumHelper.getText(alertMessage);
  }

  public String getWishListText() {
    return seleniumHelper.getText(wishListIcon);
  }

  public void clickOnAddToCartButton(String productName) throws InterruptedException {
    seleniumHelper.scrollAndClickOn(addToCartButton(productName));
    seleniumHelper.waitTillPageLoadedProperly();
  }

  public void clickOnCrossIcon() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(alertMessageCrossButton);
  }

  public void clickOnProductName(String productName) throws InterruptedException {
    seleniumHelper.scrollAndClickOn(productName(productName));
    seleniumHelper.waitTillPageLoadedProperly();
  }
}
