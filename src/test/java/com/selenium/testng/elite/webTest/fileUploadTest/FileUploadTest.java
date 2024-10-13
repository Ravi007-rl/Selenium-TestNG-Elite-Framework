package com.selenium.testng.elite.webTest.fileUploadTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.selenium.testng.elite.BaseTest;
import java.util.Arrays;
import org.testng.annotations.Test;
import pageObjectModel.webPageObject.fileUploadPage.FileUploadPage;

public class FileUploadTest extends BaseTest {

  @Test
  public void verifyThatUserCanUploadFileForInputTypeIsInput() throws InterruptedException {

    // Page object for upload file page (Internet HerokuApp)
    var fileUpload = new FileUploadPage(driver);

    log.get().info("Navigate to Internet HerokuApp's upload File page");
    driver.get("https://the-internet.herokuapp.com/upload");

    log.get().info("Upload a text file");
    var txtFile = "TextFile.txt";
    fileUpload.uploadFile(txtFile);

    log.get().info("Click on 'Upload' button");
    fileUpload.clickOnUploadButton();

    log.get().info("Verify that 'File Uploaded!' message is displayed");
    assertThat(fileUpload.getHeaderText()).isEqualTo("File Uploaded!");

    log.get().info("Verify that uploaded file name is correct");
    assertThat(fileUpload.getUploadedFileText()).isEqualTo("TextFile.txt");
  }

  @Test
  public void verifyThatUserCanUploadFileForInputTypeIsDragAndDrop() throws InterruptedException {

    // Page object for upload file page (Internet HerokuApp)
    var fileUpload = new FileUploadPage(driver);

    log.get().info("Navigate to Internet HerokuApp's upload File page");
    driver.get("https://the-internet.herokuapp.com/upload");

    log.get().info("Upload a text file");
    var txtFile = "TextFile.txt";
    fileUpload.uploadFileAtDragAndDropArea(txtFile);

    log.get().info("Verify that uploaded file name is correct");
    assertThat(fileUpload.getUploadedFileNameFromDragAndDropArea()).isEqualTo("TextFile.txt");
  }

  @Test
  public void verifyThatUserCanUploadMultipleFilesAtDragAndDrop() throws InterruptedException {

    // Page object for upload file page (Internet HerokuApp)
    var fileUpload = new FileUploadPage(driver);

    log.get().info("Navigate to Internet HerokuApp's upload File page");
    driver.get("https://the-internet.herokuapp.com/upload");

    log.get().info("Upload a text file");
    var files = Arrays.asList("TextFile.txt", "CSVFile.csv", "DocFile.doc");
    fileUpload.uploadMultipleFileAtDragAndDropArea(files);

    log.get().info("Verify that uploaded file name is correct");
    assertThat(fileUpload.getAllUploadFilesNameFromDragAndDropArea()).isEqualTo(files);
  }
}
