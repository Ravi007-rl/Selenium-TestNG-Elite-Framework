package com.selenium.testng.elite.utils;


import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {

  public static String getUploadFilesFullPath(String file) {
    return PathHelper.getUploadFiles() + file;
  }

  public static String getConcatenatedPath(List<String> fileNames) {
    return fileNames.stream()
        .map(fileName -> PathHelper.getUploadFiles() + fileName)
        .collect(Collectors.joining("\n")).trim();
  }
}
