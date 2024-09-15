package com.selenium.testng.elite.webTest.registerPageTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import dataFactory.registerUserDataFactory.RegisterUserDataFactory;
import pageObjectModel.webPageObject.homePage.HomePage;
import pageObjectModel.webPageObject.loginPage.LoginPage;
import pageObjectModel.webPageObject.registrationPage.RegistrationPage;
import com.selenium.testng.elite.BaseTest;
import java.util.Arrays;
import org.testng.annotations.Test;

public class RegisterTest01 extends BaseTest {

  @Test
  public void VerifyThatUserAbleToNavigateToRegistrationPage() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Verify that registration page is displayed");
    assertThat(registerPage.isPageHeaderDisplayed()).isTrue();
    assertThat(registerPage.getPageHeader()).isEqualTo("Register Account");
  }

  @Test
  public void VerifyThatValidationMessageDisplayedForRequiredFields() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Click on 'Register' button");
    registerPage.clickOnContinueButton();

    log.get().info("Verify that validation message displayed for required fields");
    assertThat(registerPage.isValidationDisplayedForFirstName()).isTrue();
    assertThat(registerPage.isValidationDisplayedForLastName()).isTrue();
    assertThat(registerPage.isValidationDisplayedForEmail()).isTrue();
    assertThat(registerPage.isValidationDisplayedForTelephone()).isTrue();
    assertThat(registerPage.isValidationDisplayedForPassword()).isTrue();

    log.get().info("Verify that proper validation message displayed for required fields");
    assertThat(registerPage.getFirstNameValidationMessage())
        .isEqualTo("First Name must be between 1 and 32 characters!");
    assertThat(registerPage.getLastNameValidationMessage())
        .isEqualTo("Last Name must be between 1 and 32 characters!");
    assertThat(registerPage.getEmailValidationMessage())
        .isEqualTo("E-Mail Address does not appear to be valid!");
    assertThat(registerPage.getTelephoneValidationMessage())
        .isEqualTo("Telephone must be between 3 and 32 characters!");
    assertThat(registerPage.getPasswordValidationMessage())
        .isEqualTo("Password must be between 4 and 20 characters!");
  }

  @Test
  public void VerifyThatAsteriskIsDisplayedForRequiredFields() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Verify that asterisk is displayed for required fields");
    var fieldNameList =
        Arrays.asList(
            "First Name", "Last Name", "E-Mail", "Telephone", "Password", "Password Confirm");
    fieldNameList.forEach(
        x -> assertThat(registerPage.isAsteriskIsDisplayedForGivenFiled(x)).isTrue());
  }

  @Test
  public void VerifyThatValidationMessageDisplayedForMaxLength() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Enter invalid data in all required fields and click on 'Continue' button");
    var invalidData = RegisterUserDataFactory.getInvalidRegisterUserData();
    registerPage.enterFirstName(invalidData.getFirstName());
    registerPage.enterLastName(invalidData.getLastName());
    registerPage.enterEmail(invalidData.getEmail());
    registerPage.enterTelephone(invalidData.getTelephone());
    registerPage.enterPassword(invalidData.getPassword());
    registerPage.enterConfirmPassword(invalidData.getPassword());
    registerPage.clickOnContinueButton();

    log.get().info("Verify that validation message displayed for max length fields");
    assertThat(registerPage.isValidationDisplayedForFirstName()).isTrue();
    assertThat(registerPage.isValidationDisplayedForLastName()).isTrue();
    assertThat(registerPage.isValidationDisplayedForTelephone()).isTrue();

    log.get().info("Verify that proper validation message displayed for max length fields");
    assertThat(registerPage.getFirstNameValidationMessage())
        .isEqualTo("First Name must be between 1 and 32 characters!");
    assertThat(registerPage.getLastNameValidationMessage())
        .isEqualTo("Last Name must be between 1 and 32 characters!");
    assertThat(registerPage.getTelephoneValidationMessage())
        .isEqualTo("Telephone must be between 3 and 32 characters!");
  }

  @Test
  public void VerifyThatUserRedirectToLoginPageWhenUserClickOnLoginLink()
      throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);
    var loginPage = new LoginPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Click on 'Login' link and verify that user redirected to login page");
    assertThat(registerPage.isLoginLinkDisplayed()).isTrue();
    assertThat(registerPage.isLoginLinkClickable()).isTrue();
    registerPage.clickOnLoginLink();

    log.get().info("Verify that user redirected to login page");
    assertThat(loginPage.getPageTitle()).isEqualTo("Account Login");
  }
}
