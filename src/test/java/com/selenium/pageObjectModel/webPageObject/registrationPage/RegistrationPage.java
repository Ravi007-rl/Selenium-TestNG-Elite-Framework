package com.selenium.pageObjectModel.webPageObject.registrationPage;

import com.selenium.pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends WebPageBase {
  public RegistrationPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private final By pageHeader = By.cssSelector("h1");
  private final By firstNameField = By.id("input-firstname");
  private final By lastNameField = By.id("input-lastname");
  private final By emailField = By.id("input-email");
  private final By telephoneField = By.id("input-telephone");
  private final By passwordField = By.id("input-password");
  private final By confirmPasswordField = By.id("input-confirm");
  private final By subscribeYesRadio = By.xpath("//input[@name='newsletter' and @value='1']");
  private final By subscribeNoRadio = By.xpath("//input[@name='newsletter' and @value='0']");
  private final By privacyPolicyCheckbox = By.name("agree");
  private final By continueButton = By.xpath("//input[@value='Continue']");

  private By validationMessageUsingFieldName(String fieldName) {
    return By.xpath(
        "//input[@name='" + fieldName + "']/following-sibling::div[@class='text-danger']");
  }

  private static By getLabelTextElement(String labelText) {
    return By.xpath("//label[text()='" + labelText + "']");
  }

  // Methods
  public boolean isPageHeaderDisplayed() {
    return seleniumHelper.isElementDisplayed(pageHeader);
  }

  public String getPageHeader() {
    return seleniumHelper.getText(pageHeader);
  }

  public void enterFirstName(String firstName) throws InterruptedException {
    seleniumHelper.enterText(firstNameField, firstName);
  }

  public boolean isFirstNameFieldDisplayed() {
    return seleniumHelper.isElementDisplayed(firstNameField);
  }

  public void enterLastName(String lastName) throws InterruptedException {
    seleniumHelper.enterText(lastNameField, lastName);
  }

  public boolean isLastNameFieldDisplayed() {
    return seleniumHelper.isElementDisplayed(lastNameField);
  }

  public void enterEmail(String email) throws InterruptedException {
    seleniumHelper.enterText(emailField, email);
  }

  public boolean isEmailFieldDisplayed() {
    return seleniumHelper.isElementDisplayed(emailField);
  }

  public void enterTelephone(String telephone) throws InterruptedException {
    seleniumHelper.enterText(telephoneField, telephone);
  }

  public boolean isTelephoneFieldDisplayed() {
    return seleniumHelper.isElementDisplayed(telephoneField);
  }

  public void enterPassword(String password) throws InterruptedException {
    seleniumHelper.enterText(passwordField, password);
  }

  public boolean isPasswordFieldDisplayed() {
    return seleniumHelper.isElementDisplayed(passwordField);
  }

  public void enterConfirmPassword(String confirmPassword) throws InterruptedException {
    seleniumHelper.enterText(confirmPasswordField, confirmPassword);
  }

  public boolean isConfirmPasswordFieldDisplayed() {
    return seleniumHelper.isElementDisplayed(confirmPasswordField);
  }

  public boolean isSubscribeNoRadioSelected() {
    return seleniumHelper.isElementSelected(subscribeNoRadio);
  }

  public void clickOnContinueButton() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(continueButton);
  }

  public boolean isValidationDisplayedForFirstName() {
    return seleniumHelper.isElementDisplayed(validationMessageUsingFieldName("firstname"));
  }

  public boolean isValidationDisplayedForLastName() {
    return seleniumHelper.isElementDisplayed(validationMessageUsingFieldName("lastname"));
  }

  public boolean isValidationDisplayedForEmail() {
    return seleniumHelper.isElementDisplayed(validationMessageUsingFieldName("email"));
  }

  public boolean isValidationDisplayedForTelephone() {
    return seleniumHelper.isElementDisplayed(validationMessageUsingFieldName("telephone"));
  }

  public boolean isValidationDisplayedForPassword() {
    return seleniumHelper.isElementDisplayed(validationMessageUsingFieldName("password"));
  }

  public String getFirstNameValidationMessage() {
    return seleniumHelper.getText(validationMessageUsingFieldName("firstname"));
  }

  public String getLastNameValidationMessage() {
    return seleniumHelper.getText(validationMessageUsingFieldName("lastname"));
  }

  public String getEmailValidationMessage() {
    return seleniumHelper.getText(validationMessageUsingFieldName("email"));
  }

  public String getTelephoneValidationMessage() {
    return seleniumHelper.getText(validationMessageUsingFieldName("telephone"));
  }

  public String getPasswordValidationMessage() {
    return seleniumHelper.getText(validationMessageUsingFieldName("password"));
  }

  public boolean isAsteriskIsDisplayedForGivenFiled(String fieldName) {
    return seleniumHelper.isElementHaveGivenClass(getLabelTextElement(fieldName), "control-label");
  }
}
