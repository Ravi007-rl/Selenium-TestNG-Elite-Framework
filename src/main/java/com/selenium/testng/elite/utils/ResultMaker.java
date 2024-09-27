package com.selenium.testng.elite.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class ResultMaker {

  private static final boolean wantToStoreFailedTestCases =
      Boolean.parseBoolean(Dotenv.configure().ignoreIfMissing().load().get("Is_Flaky"));
  private static final String resultFilePath = PathHelper.getListOfFailedTestCasesFile();

  public static void CreateFileForResult(List<String> failedTests, List<String> passedTests) {

    // First delete the file
    File file = new File(resultFilePath);
    try {
      if (file.exists()) FileUtils.forceDelete(file);
    } catch (IOException e) {
      System.out.println("An error occurred: " + e.getMessage());
    }

    // Write the failed test cases to the file
    writeFailedTestCasesToFile(failedTests);
    if (wantToStoreFailedTestCases) writeFlakyTestCasesToFile(passedTests);
  }

  private static void writeFailedTestCasesToFile(List<String> testCasesList) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilePath, false))) {

      if (!testCasesList.isEmpty()) {
        writer.write("List of test cases which are failed:");
        writer.newLine();

        // Loop through the list and write each test case with a number
        int index = 1;
        for (String testCase : testCasesList) {
          writer.write(index + ") " + testCase);
          writer.newLine();
          index++;
        }
      } else writer.write("All Test cases are passed!! Congratulations!");

    } catch (IOException e) {
      e.printStackTrace(); // Handle any IO exceptions
    }

    System.out.println("Failed test cases written to file: " + resultFilePath);
  }

  private static void writeFlakyTestCasesToFile(List<String> testCasesList) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilePath, true))) {

      // Check if the list is empty
      if (testCasesList.isEmpty()) return;

      // Write the failed test cases to the file
      writer.newLine();
      writer.write("\n\nList of test cases which are flaky:");
      writer.newLine();

      // Loop through the list and write each test case with a number
      int index = 1;
      for (String testCase : testCasesList) {
        writer.write(index + ") " + testCase);
        writer.newLine();
        index++;
      }
    } catch (IOException e) {
      e.printStackTrace(); // Handle any IO exceptions
    }
  }
}
