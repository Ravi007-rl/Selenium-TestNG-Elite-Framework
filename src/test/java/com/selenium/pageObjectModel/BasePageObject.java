package com.selenium.pageObjectModel;

import com.selenium.elementHelper.WaitHelper;
import org.openqa.selenium.WebDriver;

public class BasePageObject {
  protected WaitHelper wait;

  protected BasePageObject(WebDriver driver) {
    wait = new WaitHelper(driver);
  }
}
