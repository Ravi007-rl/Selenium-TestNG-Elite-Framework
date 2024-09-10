package com.selenium.testng.elite.webTest.registerPageTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.javafaker.Faker;
import com.selenium.dataFactory.registerUserDataFactory.RegisterUserDataFactory;
import com.selenium.pageObjectModel.webPageObject.accountPage.AccountCreatedPage;
import com.selenium.pageObjectModel.webPageObject.homePage.HomePage;
import com.selenium.pageObjectModel.webPageObject.registrationPage.RegistrationPage;
import com.selenium.testng.elite.BaseTest;
import org.testng.annotations.Test;

public class RegisterTest02 extends BaseTest {

  @Test
  public void VerifyThatValidationMessageDisplayedForPrivacyPolicy() throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Click on 'Privacy Policy' checkbox");
    registerPage.clickOnContinueButton();

    log.get().info("Verify that validation message displayed for privacy policy");
    assertThat(registerPage.isValidationDisplayedForPrivacyPolicy()).isTrue();

    log.get().info("Verify that proper validation message displayed for privacy policy");
    final String privacyPolicyValidationMessage = "Warning: You must agree to the Privacy Policy!";
    assertThat(registerPage.getPrivacyPolicyValidationMessage())
        .isEqualTo(privacyPolicyValidationMessage);
  }

  @Test
  public void VerifyThatPrivacyPolicyPopupDisplayedWhenUserClickOnPrivacyPolicyLink()
      throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Verify that privacy policy popup is displayed");
    assertThat(registerPage.isPrivacyPolicyLinkDisplayed()).isTrue();
    assertThat(registerPage.isPrivacyPolicyLinkClickable()).isTrue();

    log.get()
        .info("Click on 'Privacy Policy' link and verify that privacy policy popup is displayed");
    registerPage.clickOnPrivacyPolicyLink();

    log.get().info("Verify that privacy policy popup is displayed");
    assertThat(registerPage.isPrivacyPolicyPopupDisplayed()).isTrue();

    log.get().info("Verify that privacy policy popup header is displayed");
    assertThat(registerPage.getPrivacyPolicyPopupHeader()).isEqualTo("Privacy Policy");

    log.get().info("Verify that privacy policy popup content is displayed");
    assertThat(registerPage.getPrivacyPolicyPopupContent()).isEqualTo("Privacy Policy");

    log.get().info("Click on 'Close' button and verify that privacy policy popup is closed");
    registerPage.clickOnPrivacyPolicyPopupCloseButton();
    assertThat(registerPage.isPrivacyPolicyPopupDisplayed()).isFalse();
  }

  @Test
  public void VerifyThatValidationMessageDisplayedWhenPasswordAndConfirmPasswordNotMatched()
      throws InterruptedException {

    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Enter user details and click on 'Continue' button");
    var registerUserData = RegisterUserDataFactory.getRegisterUserData();
    registerUserData.setConfirmPassword(new Faker().internet().password());
    registerPage.enterPassword(registerUserData.getPassword());
    registerPage.enterConfirmPassword(registerUserData.getConfirmPassword());
    registerPage.clickOnContinueButton();

    log.get()
        .info(
            "Verify that validation message displayed when password and confirm password not matched");
    assertThat(registerPage.isValidationMessageDisplayedForPasswordAndConfirmPasswordNotMatched())
        .isTrue();

    log.get()
        .info(
            "Verify that proper validation message displayed when password and confirm password not matched");
    final String validationMessage = "Password confirmation does not match password!";
    assertThat(registerPage.getValidationMessageForPasswordAndConfirmPasswordNotMatched())
        .isEqualTo(validationMessage);
  }

  @Test
  public void VerifyThatUserRegisteredSuccessfully() throws InterruptedException {
    // All page object mention here
    var homePage = new HomePage(driver);
    var registerPage = new RegistrationPage(driver);
    var accountPage = new AccountCreatedPage(driver);

    log.get().info("Click on 'My Account' dropdown and click on 'Register' option");
    homePage.clickOnMyAccountDropdown();
    homePage.clickOnRegisterOption();

    log.get().info("Enter user details and click on 'Continue' button");
    var registerUserData = RegisterUserDataFactory.getRegisterUserData();
    registerPage.fillTheForm(registerUserData);
    registerPage.clickOnPrivacyPolicyCheckbox();
    registerPage.clickOnContinueButton();

    log.get().info("Verify that user redirect to account created page");
    var pageTitle = "Your Account Has Been Created!";
    assertThat(accountPage.getPageTitle()).isEqualTo(pageTitle);
    assertThat(accountPage.isPageHeaderDisplayed()).isTrue();
    assertThat(accountPage.getPageHeader()).isEqualTo("Your Account Has Been Created!");
  }
}
