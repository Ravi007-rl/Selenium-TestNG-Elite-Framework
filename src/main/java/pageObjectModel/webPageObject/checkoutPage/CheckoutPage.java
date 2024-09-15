package pageObjectModel.webPageObject.checkoutPage;

import pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends WebPageBase {
  public CheckoutPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private final By pageHeader = By.cssSelector("h1");

  public String getPageHeader() throws InterruptedException {
    return seleniumHelper.getText(pageHeader);
  }
}