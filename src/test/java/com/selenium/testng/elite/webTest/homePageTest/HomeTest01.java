package com.selenium.testng.elite.webTest.homePageTest;

import com.selenium.pageObjectModel.webPageObject.homePage.HomePage;
import com.selenium.pageObjectModel.webPageObject.productPage.ProductPage;
import com.selenium.pageObjectModel.webPageObject.searchPage.SearchPage;
import com.selenium.testng.elite.BaseTest;
import com.selenium.testng.elite.enums.EnvironmentType;
import com.selenium.testng.elite.utils.RandomHelper;
import org.testng.annotations.Test;
import java.util.Arrays;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HomeTest01 extends BaseTest {

  @Test(groups = "smoke")
  public void VerifyThatCurrencyDropdownWorkingProperly() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);

    log.get().info("Verify the default currency symbol");
    final String expectedDefaultCurrencySymbol =
        environmentConfig.getEnvironment() == EnvironmentType.TEST ? "£" : "$";
    var actualCurrencySymbol = homePage.getCurrentCurrencySymbol();
    assertThat(actualCurrencySymbol).isEqualTo(expectedDefaultCurrencySymbol);

    log.get().info("Verify that cart have currency symbol same as default currency symbol");
    var cartTotalText = homePage.getCartTotalText();
    assertThat(cartTotalText).contains(expectedDefaultCurrencySymbol);

    log.get().info("Verify that all product have price with default currency symbol");
    var allProductPrice = homePage.getAllProductPrice();
    allProductPrice.forEach(x -> assertThat(x).contains(expectedDefaultCurrencySymbol));

    log.get().info("Click on currency dropdown and verify that all currency displayed properly");
    homePage.clickOnCurrencyDropdown();
    var expectedCurrencyDropdownOptions =
        Arrays.asList("€ Euro", "£ Pound Sterling", "$ US Dollar");
    var actualCurrencyDropdownOptions = homePage.getCurrencyDropdownOptionList();
    assertThat(actualCurrencyDropdownOptions).isEqualTo(expectedCurrencyDropdownOptions);

    log.get().info("Change currency and Verify cart and product have same currency symbol");
    var changeCurrency = "Euro";
    final String expectedCurrencySymbol = "€";
    homePage.clickOnCurrency(changeCurrency);
    cartTotalText = homePage.getCartTotalText();
    assertThat(cartTotalText).contains(expectedCurrencySymbol);
    allProductPrice = homePage.getAllProductPrice();
    allProductPrice.forEach(x -> assertThat(x).contains(expectedCurrencySymbol));
  }

  @Test(groups = "smoke")
  public void VerifyThatMyAccountDropdownWorkingProperly() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);

    log.get().info("Verify that 'My Account' dropdown displayed at Home page");
    assertThat(homePage.isMyAccountDropdownDisplayed()).isTrue();

    log.get().info("Click on 'My Account' dropdown and verify that options are displayed properly");
    homePage.clickOnMyAccountDropdown();
    var expectedDropdownOptions = Arrays.asList("Register", "Login");
    var actualDropdownOptions = homePage.getMyAccountDropdownOption();
    assertThat(actualDropdownOptions).isEqualTo(expectedDropdownOptions);
  }

  @Test(groups = "smoke")
  public void VerifyThatWishListFunctionalityWorkingWhenUserIsNotLoggedIn()
      throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);

    log.get().info("Verify that 'Wish List' icon displayed at Home page");
    assertThat(homePage.IsWishListIconDisplayed()).isTrue();

    log.get().info("Click on product wish List button and Verify that alert message displayed");
    var randomProductName = RandomHelper.getRandomList(homePage.getAllProductNamesList());
    homePage.clickOnWishListButton(randomProductName);
    assertThat(homePage.isAlertMessageDisplayed()).isTrue();
    var alertMessage =
        "You must login or create an account to save " + randomProductName + " to your wish list!";
    assertThat(homePage.getAlertMessage()).contains(alertMessage);

    log.get().info("Verify that wish list count increase at header");
    assertThat(homePage.getWishListText()).isEqualTo("Wish List (1)");
  }

  @Test(groups = "smoke")
  public void VerifyThatUserRedirectToProductPageWhenUserClickOnProductName()
      throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var productName = new ProductPage(driver);

    log.get().info("Click on product name");
    var randomProductName = RandomHelper.getRandomList(homePage.getAllProductNamesList());
    homePage.clickOnProductName(randomProductName);

    log.get().info("Verify that user redirect to product page and page title is correct");
    assertThat(productName.getPageTitle()).contains(randomProductName);
    assertThat(productName.getPageHeader()).isEqualTo(randomProductName);
  }

  @Test(groups = "smoke")
  public void VerifyThatSearchFunctionalityWorkingProperly() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var searchPage = new SearchPage(driver);

    log.get().info("Enter product name in search bar and click on search button");
    var randomProductName = RandomHelper.getRandomList(homePage.getAllProductNamesList());
    homePage.enterTextInSearchBar(randomProductName);
    homePage.clickOnSearchButton();

    log.get().info("Verify page title and page header is correct");
    final String expectedTitleHeader = "Search - " + randomProductName;
    assertThat(searchPage.getPageTitle()).isEqualTo(expectedTitleHeader);
    assertThat(searchPage.getPageHeader()).isEqualTo(expectedTitleHeader);
  }
}
