package com.selenium.pageObjectModel.webPageObject.loginPage;

import com.selenium.dataObject.loginUser.LoginUserDataObject;
import com.selenium.pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends WebPageBase {
  public LoginPage(WebDriver driver) {
    super(driver);
  }

  // Locator
  private final By loginPageBreadcrumb = By.xpath("//ul[@class='breadcrumb']/li[.='Login']");
  private final By forgotPasswordLink = By.linkText("Forgotten Password");
  private final By emailField = By.id("input-email");
  private final By passwordField = By.id("input-password");
  private final By loginButton = By.xpath("//input[@value='Login']");
  private final By validationMessage = By.xpath("//div[contains(@class,'alert-danger')]");
  private final By registerUserContinueButton = By.partialLinkText("Continue");

  public boolean isLoginPageBreadcrumbDisplayed() {
    return seleniumHelper.isElementDisplayed(loginPageBreadcrumb);
  }

  public void clickOnForgotPasswordLink() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(forgotPasswordLink);
    seleniumHelper.waitTillPageLoadedProperly();
  }

  public void clickOnRegisterUserContinueButton() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(registerUserContinueButton);
    seleniumHelper.waitTillPageLoadedProperly();
  }

  public void enterEmail(String email) throws InterruptedException {
    seleniumHelper.scrollAndEnterText(emailField, email);
  }

  public void enterPassword(String password) throws InterruptedException {
    seleniumHelper.scrollAndEnterText(passwordField, password);
  }

  public void clickOnLoginButton() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(loginButton);
    seleniumHelper.waitTillPageLoadedProperly();
  }

  public boolean isValidationMessageDisplayed() {
    return seleniumHelper.isElementDisplayed(validationMessage);
  }

  public String getValidationMessage() throws InterruptedException {
    return seleniumHelper.getText(validationMessage);
  }

  public void fillLoginDetails(LoginUserDataObject loginDetails) throws InterruptedException {
    enterEmail(loginDetails.getEmail());
    enterPassword(loginDetails.getPassword());
  }
}
