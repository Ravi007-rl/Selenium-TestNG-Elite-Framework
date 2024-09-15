package pageObjectModel.webPageObject.accountPage;

import pageObjectModel.webPageObject.WebPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountCreatedPage extends WebPageBase {

  public AccountCreatedPage(WebDriver driver) {
    super(driver);
  }

  private final By pageHeader = By.xpath("//h1");

  public boolean isPageHeaderDisplayed() {
    return seleniumHelper.isElementDisplayed(pageHeader);
  }

  public String getPageHeader() throws InterruptedException {
    return seleniumHelper.getText(pageHeader);
  }
}
