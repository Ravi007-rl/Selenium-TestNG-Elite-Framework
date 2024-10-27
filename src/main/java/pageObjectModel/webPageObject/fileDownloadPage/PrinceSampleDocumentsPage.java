package pageObjectModel.webPageObject.fileDownloadPage;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObjectModel.webPageObject.WebPageBase;

public class PrinceSampleDocumentsPage extends WebPageBase {

  public PrinceSampleDocumentsPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private static final By pdfFileLink = By.xpath("//div[@id='dictionary']//a[text()='PDF']");

  // Methods
  public Pair<Boolean, String> clickOnPDFLink(String fileName) throws InterruptedException {
    return seleniumHelper
        .initiateDownloadAndVerifyWithExpectedMessageBuilder()
        .locator(pdfFileLink)
        .fileName(fileName)
        .build();
  }
}