package com.selenium.testng.elite.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHelper {

  public static <T> T getRandomList(List<T> items) {
    return items.get(ThreadLocalRandom.current().nextInt(items.size()));
  }
}