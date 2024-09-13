package com.selenium.testng.elite.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Constant {

  public static String BASE_URL;

  public static void loadConstants(Dotenv dotenv) {
    BASE_URL = dotenv.get("BASE_URL");
  }
}
