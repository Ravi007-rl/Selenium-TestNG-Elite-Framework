package elementHelper.web;

import com.selenium.testng.elite.utils.PathHelper;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.support.ui.FluentWait;

public class FileHelper {

  /**
   * Gets the full path of the uploaded files by concatenating the upload directory path with the
   * file name.
   *
   * @param file The name of the file.
   * @return The full path of the uploaded file.
   */
  static String getUploadFilesFullPath(String file) {
    return PathHelper.getUploadFiles() + file;
  }

  /**
   * Gets the concatenated path of the uploaded files by concatenating the upload directory path
   * with the file name and separated by a new line.
   *
   * @param fileNames The list of file names.
   * @return The concatenated path of the uploaded files.
   */
  static String getConcatenatedPath(List<String> fileNames) {
    return fileNames.stream()
        .map(fileName -> PathHelper.getUploadFiles() + fileName)
        .collect(Collectors.joining("\n"))
        .trim();
  }

  /**
   * Checks if a file with the specified name exists in the download folder within the specified
   * time period.
   *
   * @param fileName The name of the file to check for existence.
   * @param timeInSeconds The time period in seconds to wait for the file to exist.
   * @return A pair containing a boolean indicating whether the file exists and has a size greater
   *     than 0, and a string listing the files available in the download folder.
   */
  static Pair<Boolean, String> isFileExists(String fileName, int timeInSeconds) {

    File dir = new File(PathHelper.getDownloadFolderPath());

    FluentWait<File> wait =
        new FluentWait<>(dir)
            .withTimeout(Duration.ofSeconds(timeInSeconds))
            .pollingEvery(Duration.ofSeconds(2)) // Check every 2 seconds
            .ignoring(Exception.class); // Ignore any exceptions during the wait

    Function<File, Boolean> checkForFile =
        fileDir -> {
          var files = fileDir.listFiles();
          if (files == null) return false;
          for (File file : files) {
            if (file.getName().equals(fileName) && file.length() > 0) {
              return true;
            }
          }
          return false;
        };

    // Check if the file exists
    var fileExists = false;
    try {
      fileExists = wait.until(checkForFile);
    } catch (Exception e) {
      fileExists = false;
    }

    Collection<File> files = FileUtils.listFiles(dir, null, true);
    var fileNames = files.stream().map(File::getName).toList();
    var fileInFolder = "\nFiles available in download folder:\n" + String.join("\n", fileNames);
    return Pair.of(fileExists, fileInFolder);
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

  /**
   * Deletes a file with the specified name in the download folder.
   *
   * @param fileName The name of the file to delete.
   */
  static void deleteFile(String fileName) {
    var file = new File(PathHelper.getDownloadFolderPath() + fileName);
    try {
      FileUtils.forceDelete(file);
    } catch (IOException _) {
    }
  }
}
