package com.selenium.testng.elite.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHelper {

  public static <T> T getRandomElementFromList(List<T> items) {
    return items.get(ThreadLocalRandom.current().nextInt(items.size()));
  }

  public static String getRandomString(int length) {
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(chars.charAt(ThreadLocalRandom.current().nextInt(chars.length())));
    }
    return sb.toString();
  }
}
