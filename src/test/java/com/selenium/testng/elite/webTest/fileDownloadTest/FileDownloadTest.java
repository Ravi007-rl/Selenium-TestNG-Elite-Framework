package com.selenium.testng.elite.webTest.fileDownloadTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.selenium.testng.elite.BaseTest;
import com.selenium.testng.elite.utils.FileHelper;
import java.util.Date;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import pageObjectModel.webPageObject.fileDownloadPage.FileDownloadPage;

public class FileDownloadTest extends BaseTest {

    @Test
    public void verifyThatUserCanDownloadFileFromInternetHerokuApp() throws InterruptedException {

        // Page object for download file page (Internet HerokuApp)
        var fileDownload = new FileDownloadPage(driver);

        log.get().info("Navigate to Internet HerokuApp's download page");
        driver.get("https://the-internet.herokuapp.com/download");

        log.get().info("Verify page header is displayed");
        assertThat(fileDownload.getPageHeader()).isEqualTo("File Downloader");

        log.get().info("Click on 'File Download' link");
        var fileName = "zero_bytes_file.txt";
        fileDownload.clickOnDownloadFile(fileName);
    }

    @Test
    public void verifyThatUserCanDownloadFile() throws InterruptedException {

        // Page object for download file page (Internet HerokuApp)
        var fileDownload = new FileDownloadPage(driver);

        log.get().info("Navigate to Internet HerokuApp's download page");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.location = 'https://download.oracle.com/java/21/archive/jdk-21.0.4_linux-aarch64_bin.tar.gz';");

//        log.get().info("Click on 'File Download' link");
//        var fileName = "test.txt";
//        fileDownload.clickOnDownloadFile(fileName);

        log.get().info("Verify that downloaded file name is correct");

        var startTime = new Date().getTime();
        var fileDownloaded = FileHelper.isFileExists("jdk-21.0.4_linux-aarch64_bin.tar.gz",30);
        var endTime = new Date().getTime();

        // Convert into seconds
        var totalTime = (endTime - startTime) / 1000;
        System.out.println(totalTime);

        assertThat(fileDownloaded).isTrue();





        log.get().info("Test case complete here");
    }
}
