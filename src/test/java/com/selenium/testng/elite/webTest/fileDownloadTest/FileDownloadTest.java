package com.selenium.testng.elite.webTest.fileDownloadTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.selenium.testng.elite.BaseTest;
import org.testng.annotations.Test;
import pageObjectModel.webPageObject.fileDownloadPage.FileDownloadPageInternetHerokuApp;
import pageObjectModel.webPageObject.fileDownloadPage.PrinceSampleDocumentsPage;

public class FileDownloadTest extends BaseTest {

  @Test
  public void verifyThatUserCanDownloadFileFromInternetHerokuApp() throws InterruptedException {

    // Page object for download file page (Internet HerokuApp)
    var fileDownload = new FileDownloadPageInternetHerokuApp(driver);

    log.get().info("Navigate to Internet HerokuApp's download page");
    driver.get("https://the-internet.herokuapp.com/download");

    log.get().info("Verify page header is displayed");
    assertThat(fileDownload.getPageHeader()).isEqualTo("File Downloader");

    log.get().info("Click on 'File Download' link");
    var fileName = "SomeFile.txt";
    var fileExists = fileDownload.clickOnDownloadFile(fileName);

    log.get().info("Verify that downloaded file name is correct");
    assertThat(fileExists).isTrue();
  }

  @Test
  public void verifyThatUserCanDownloadPDFWhichIsRenderedAtBrowser() throws InterruptedException {

    // Page object for download file page (Internet HerokuApp)
    var fileDownload = new PrinceSampleDocumentsPage(driver);

    log.get().info("Navigate to 'Prince - Sample Documents' page");
    driver.get("https://www.princexml.com/samples/");

    log.get().info("Click on 'PDF' link");
    var isFileExists = fileDownload.clickOnPDFLink("dictionary.pdf");

    log.get().info("Verify that downloaded file name is correct");
    assertThat(isFileExists.getLeft()).withFailMessage(isFileExists.getRight()).isTrue();
  }
}
