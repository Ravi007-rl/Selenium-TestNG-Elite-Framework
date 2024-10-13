package pageObjectModel.webPageObject.fileUploadPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObjectModel.webPageObject.WebPageBase;

import java.util.List;

public class FileUploadPage extends WebPageBase {
  public FileUploadPage(WebDriver driver) {
    super(driver);
  }

  // Locators
  private final By chooseFile = By.xpath("//input[@id='file-upload']");
  private final By uploadButton = By.xpath("//input[@id='file-submit']");
  private final By uploadedFile = By.xpath("//div[@id='uploaded-files']");
  private final By dragAndDropArea = By.xpath("//div[@id='drag-drop-upload']");
  private final By headerText = By.xpath("//h3");
  private final By uploadedFileNameAtDragAndDropArea =
      By.xpath("//div[contains(@class,'dz-success')]//div[@class='dz-filename']/span");

  // Methods
  public void uploadFile(String fileName) throws InterruptedException {
    seleniumHelper.uploadFile(chooseFile, fileName);
  }

  public void clickOnUploadButton() throws InterruptedException {
    seleniumHelper.scrollAndClickOn(uploadButton);
    seleniumHelper.waitTillPageLoadedProperly();
  }

  public String getHeaderText() throws InterruptedException {
    return seleniumHelper.getText(headerText);
  }

  public String getUploadedFileText() throws InterruptedException {
    return seleniumHelper.getText(uploadedFile);
  }

  public void uploadFileAtDragAndDropArea(String fileName) throws InterruptedException {
    seleniumHelper.uploadFile(dragAndDropArea, fileName);
  }

  public void uploadMultipleFileAtDragAndDropArea(List<String> fileNames)
      throws InterruptedException {
    seleniumHelper.uploadFile(dragAndDropArea, fileNames);
  }

  public String getUploadedFileNameFromDragAndDropArea() throws InterruptedException {
    return seleniumHelper.getText(uploadedFileNameAtDragAndDropArea);
  }

  public List<String> getAllUploadFilesNameFromDragAndDropArea() {
    return seleniumHelper.getAllElementsText(uploadedFileNameAtDragAndDropArea);
  }
}
