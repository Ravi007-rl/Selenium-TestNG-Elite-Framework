package pageObjectModel.webPageObject.fileDownloadPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObjectModel.webPageObject.WebPageBase;

public class FileDownloadPage extends WebPageBase {
  public FileDownloadPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private final By pageHeader = By.cssSelector("h3");


  // Dynamic locator
  private static By downloadFile(String fileName) {
    return By.xpath("//a[text()='" + fileName + "']");
  }

  public String getPageHeader() throws InterruptedException {
    return seleniumHelper.getText(pageHeader);
  }

  public void clickOnDownloadFile(String fileName) throws InterruptedException {
    seleniumHelper.scrollAndClickOn(By.xpath("//div[@id='dictionary']//a[text()='PDF']"));
  }

}
