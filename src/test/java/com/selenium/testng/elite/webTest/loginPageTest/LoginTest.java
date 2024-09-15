package com.selenium.testng.elite.webTest.loginPageTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import dataFactory.loginUserDataFactory.LoginUserDataFactory;
import pageObjectModel.webPageObject.forgotPasswordPage.ForgotPasswordPage;
import pageObjectModel.webPageObject.homePage.HomePage;
import pageObjectModel.webPageObject.loginPage.LoginPage;
import pageObjectModel.webPageObject.registrationPage.RegistrationPage;
import com.selenium.testng.elite.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

  @Test
  public void VerifyThatUserAbleToNavigateToLoginPage() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var login = new LoginPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnLoginOption();

    log.get().info("Verify that login page is displayed");
    assertThat(login.getPageTitle()).isEqualTo("Account Login");

    log.get().info("Verify that login form is displayed");
    assertThat(login.isLoginPageBreadcrumbDisplayed()).isTrue();
  }

  @Test
  public void VerifyThatUserRedirectToForgotPasswordPageWhenUserClickOnForgotPasswordLink()
      throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var loginPage = new LoginPage(driver);
    var forgotPasswordPage = new ForgotPasswordPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Login' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnLoginOption();

    log.get().info("Click on 'Forgotten Password' link");
    loginPage.clickOnForgotPasswordLink();

    log.get().info("Verify that user redirected to forgot password page");
    assertThat(forgotPasswordPage.getPageTitle()).isEqualTo("Forgot Your Password?");
    assertThat(forgotPasswordPage.isPageHeaderDisplayed()).isTrue();
    assertThat(forgotPasswordPage.getPageHeader()).isEqualTo("Forgot Your Password?");
  }

  @Test
  public void VerifyThatErrorMessageDisplayedWhenUserEnterInvalidDetailsAtLoginPage()
      throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var loginPage = new LoginPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Login' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnLoginOption();

    log.get().info("Enter invalid details and click on 'Login' button");
    var loginUserData = LoginUserDataFactory.invalidLoginUserData();
    loginPage.fillLoginDetails(loginUserData);
    loginPage.clickOnLoginButton();

    log.get().info("Verify that error message displayed");
    assertThat(loginPage.isValidationMessageDisplayed()).isTrue();
    assertThat(loginPage.getValidationMessage())
        .isEqualTo("Warning: No match for E-Mail Address and/or Password.");
  }

  @Test
  public void VerifyThatUserRedirectToRegisterPageWhenUserClickOnContinueButton() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var loginPage = new LoginPage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Login' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnLoginOption();

    log.get().info("Click on 'Continue' button");
    loginPage.clickOnRegisterUserContinueButton();

    log.get().info("Verify that user redirected to registration page");
    assertThat(registerPage.isPageHeaderDisplayed()).isTrue();
    assertThat(registerPage.getPageHeader()).isEqualTo("Register Account");
  }
}
