package com.selenium.pageObjectModel.webPageObject;

import com.selenium.elementHelper.web.SeleniumHelper;
import com.selenium.pageObjectModel.BasePageObject;
import org.openqa.selenium.WebDriver;

public class WebPageBase extends BasePageObject {

  protected SeleniumHelper seleniumHelper;

  protected WebPageBase(WebDriver driver) {
    super(driver);
    seleniumHelper = new SeleniumHelper(driver);
  }
}
