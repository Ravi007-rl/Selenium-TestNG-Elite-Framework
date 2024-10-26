package pageObjectModel.webPageObject.fileDownloadPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObjectModel.webPageObject.WebPageBase;

public class FileDownloadPageInternetHerokuApp extends WebPageBase {
  public FileDownloadPageInternetHerokuApp(WebDriver driver) {
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

  public boolean clickOnDownloadFile(String fileName) throws InterruptedException {
    return seleniumHelper
        .initiateDownloadAndVerifyBuilder()
        .locator(downloadFile(fileName))
        .fileName(fileName)
        .build();
  }
}
