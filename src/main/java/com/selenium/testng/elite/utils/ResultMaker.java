package com.selenium.testng.elite.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultMaker {

  public static void writeFailedTestCasesToFile(List<String> failedTests) {
    try (BufferedWriter writer =
        new BufferedWriter(new FileWriter(PathHelper.getListOfFailedTestCasesFile(), false))) {
      writer.write("List of test cases which are failed:");
      writer.newLine();

      // Loop through the list and write each test case with a number
      int index = 1;
      for (String testCase : failedTests) {
        writer.write(index + ") " + testCase);
        writer.newLine();
        index++;
      }
    } catch (IOException e) {
      e.printStackTrace(); // Handle any IO exceptions
    }

    System.out.println("Failed test cases written to file: " + PathHelper.getListOfFailedTestCasesFile());
  }

  public static void writeGoodMessageInTestCaseFailedFile() {
    try (BufferedWriter writer =
        new BufferedWriter(new FileWriter(PathHelper.getListOfFailedTestCasesFile(), false))) {
      writer.write("All Test cases are passed!! Congratulations!");
    } catch (IOException e) {
      e.printStackTrace(); // Handle any IO exceptions
    }
    System.out.println("No failed test cases found");
  }
}
