package com.selenium.elementHelper.web;

import com.selenium.elementHelper.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumHelper {

  private WebDriver driver;
  private final WaitHelper waitHelper;
  private final JavaScriptHelper jsHelper;

  public SeleniumHelper(WebDriver driver) {
    this.driver = driver;
    waitHelper = new WaitHelper(driver);
    jsHelper = new JavaScriptHelper(driver);
  }

  // wait till element become enable
  private WebElement waitTillElementIsEnable(WebElement element) throws InterruptedException {
    var numberOfTries = 1;
    while (!isElementDisable(element) && numberOfTries != 15) {
      waitHelper.hardWait(numberOfTries);
      numberOfTries++;
    }
    return element;
  }

  private void clickOnElement(WebElement element) {
    var tryToClickOnElement = 10;
    boolean staleElement;

    do {
      try {
        element.click();
        staleElement = false;
      } catch (Exception e) {
        staleElement = true;
      }
      tryToClickOnElement--;
    } while (tryToClickOnElement != 0 && staleElement);
  }

  // First clear the value from element and enter value in input box
  private void enterText(WebElement element, String value) throws InterruptedException {
    var enableElement = waitTillElementIsEnable(element);
    enableElement.clear();
    enableElement.sendKeys(value);
  }

  // Return that element is in enable state or disable state
  private boolean isElementDisable(WebElement element) {
    return element.isEnabled();
  }

  // Use this for sendKey to an element
  public void scrollAndEnterText(By by, String value) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by);
    jsHelper.scrollToElementIfNotInView(element);
    enterText(element, value);
  }

  public void scrollAndEnterText(By by, String value, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    jsHelper.scrollToElementIfNotInView(element);
    enterText(element, value);
  }

  public void enterText(By by, String value) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by);
    enterText(element, value);
  }

  public void enterText(By by, String value, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    enterText(element, value);
  }

  // This method used to click on element
  public void scrollAndClickOn(By by) throws InterruptedException {
    var element = waitHelper.waitForElementToBeClickable(by);
    var elementIsEnable = waitTillElementIsEnable(element);
    jsHelper.scrollToElementIfNotInView(elementIsEnable);
    clickOnElement(elementIsEnable);
  }

  public void scrollAndClickOn(By by, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeClickable(by, second);
    var elementIsEnable = waitTillElementIsEnable(element);
    jsHelper.scrollToElementIfNotInView(elementIsEnable);
    clickOnElement(elementIsEnable);
  }

  public void ClickOn(By by) throws InterruptedException {
    var element = waitHelper.waitForElementToBeClickable(by);
    var elementIsEnable = waitTillElementIsEnable(element);
    clickOnElement(elementIsEnable);
  }

  public void ClickOn(By by, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeClickable(by, second);
    var elementIsEnable = waitTillElementIsEnable(element);
    clickOnElement(elementIsEnable);
  }
}
