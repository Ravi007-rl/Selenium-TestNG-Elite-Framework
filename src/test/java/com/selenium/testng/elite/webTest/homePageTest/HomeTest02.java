package com.selenium.testng.elite.webTest.homePageTest;

import com.selenium.pageObjectModel.webPageObject.checkoutPage.CheckoutPage;
import com.selenium.pageObjectModel.webPageObject.homePage.HomePage;
import com.selenium.pageObjectModel.webPageObject.productPage.ProductPage;
import com.selenium.testng.elite.BaseTest;
import com.selenium.testng.elite.enums.EnvironmentType;
import com.selenium.testng.elite.utils.RandomHelper;
import org.testng.annotations.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HomeTest02 extends BaseTest {

  @Test(groups = "smoke")
  public void VerifyThatCheckOutButtonWorkingProperly() throws InterruptedException {

    var homePage = new HomePage(driver);
    var checkoutPage = new CheckoutPage(driver);

    log.get().info("Verify that 'CheckOut' button displayed at header");
    assertThat(homePage.isCheckoutButtonDisplayed()).isTrue();

    log.get().info("Click on 'Checkout' button and verify that user redirected to checkout page");
    homePage.clickOnCheckoutButton();
    final String expectedResult = "Shopping Cart";
    assertThat(checkoutPage.getPageTitle()).isEqualTo(expectedResult);
    assertThat(checkoutPage.getPageHeader()).isEqualTo(expectedResult);
  }

  @Test
  public void VerifyLogoLingWorkingProperly() throws InterruptedException {

    var homePage = new HomePage(driver);
    var productPage = new ProductPage(driver);

    log.get().info("Click on any product");
    var randomProduct = RandomHelper.getRandomList(homePage.getAllProductNamesList());
    homePage.clickOnProductName(randomProduct);

    log.get().info("Click on 'Logo' link and verify that user redirected to Home Page");
    productPage.clickOnLogoLink();
    var expectedTitle = environmentConfig.getEnvironment() == EnvironmentType.TEST ? "OpenCart" : "Your Store";
    assertThat(homePage.getPageTitle()).isEqualTo(expectedTitle);
  }
}
