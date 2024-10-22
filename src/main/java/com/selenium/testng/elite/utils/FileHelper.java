package com.selenium.testng.elite.utils;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

public class FileHelper {

  /**
   * Gets the full path of the uploaded files by concatenating the upload directory path with the
   * file name.
   *
   * @param file The name of the file.
   * @return The full path of the uploaded file.
   */
  public static String getUploadFilesFullPath(String file) {
    return PathHelper.getUploadFiles() + file;
  }

  /**
   * Gets the concatenated path of the uploaded files by concatenating the upload directory path
   * with the file name and separated by a new line.
   *
   * @param fileNames The list of file names.
   * @return The concatenated path of the uploaded files.
   */
  public static String getConcatenatedPath(List<String> fileNames) {
    return fileNames.stream()
        .map(fileName -> PathHelper.getUploadFiles() + fileName)
        .collect(Collectors.joining("\n"))
        .trim();
  }

  /**
   * Verifies if the given file exists in the download folder within a specified time period.
   *
   * @param fileName The name of the file.
   * @param timeInSeconds The time in seconds to wait for the file to exist.
   * @return true if the file exists, false otherwise.
   */
  public static boolean isFileExists(String fileName, int timeInSeconds) {

    File dir = new File(PathHelper.getDownloadFolderPath());

    FluentWait<File> wait =
        new FluentWait<>(dir)
            .withTimeout(Duration.ofSeconds(timeInSeconds))
            .pollingEvery(Duration.ofSeconds(2)) // Check every 2 seconds
            .ignoring(Exception.class); // Ignore any exceptions during the wait

    Function<File, Boolean> checkForFile =
        fileDir -> {
          File[] files = fileDir.listFiles();
          if (files == null) return false;
          for (File file : files) {
            if (file.getName().equals(fileName) && file.length() > 0) {
              return true;
            }
          }
          return false;
        };

    try {
      return wait.until(checkForFile);
    } catch (TimeoutException e) {
      System.out.println("File not found: " + e.getMessage());
      return false;
    }
  }

  /**
   * Deletes all files in the download folder. This method cleans the download directory by removing
   * all files present within it.
   */
  public static void deleteAllFiles() {
    var dir = new File(PathHelper.getDownloadFolderPath());
    try {
      FileUtils.cleanDirectory(dir);
    } catch (Exception e) {
      System.out.println("An error occurred: " + e.getMessage());
    }
  }
}
