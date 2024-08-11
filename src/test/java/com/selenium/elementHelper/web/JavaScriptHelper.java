package com.selenium.elementHelper.web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptHelper {

  private final WebDriver driver;

  public JavaScriptHelper(WebDriver driver) {
    this.driver = driver;
  }

  // Using this we check element displayed in viewport
  private boolean isElementInViewport(WebElement element) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    return (Boolean)
        js.executeScript(
            "var rect = arguments[0].getBoundingClientRect();"
                + "return ("
                + "rect.top >= 0 &&"
                + "rect.left >= 0 &&"
                + "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&"
                + "rect.right <= (window.innerWidth || document.documentElement.clientWidth)"
                + ");",
            element);
  }

  // used this method when element is not in view port
  private void scrollToElementCenter(WebElement element) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(
        "var elementRect = arguments[0].getBoundingClientRect();"
            + "var absoluteElementTop = elementRect.top + window.pageYOffset;"
            + "var middle = absoluteElementTop - (window.innerHeight / 2);"
            + "window.scrollTo(0, middle);",
        element);
  }

  // use this method to check and ensure element is in view port
  public void scrollToElementIfNotInView(WebElement element) {
    if (!isElementInViewport(element)) {
      scrollToElementCenter(element);
    }
  }
}
