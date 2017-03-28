package com.github.bobi4597.counter.component;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the Counter interface
 * that allows only one thread to updated the counter.
 */
public class SynchronizedCounter implements Counter {

  private final Map<String, Integer> map = new HashMap<>();

  @Override
  public synchronized void add(final String key, final int count) {

    Integer prev = map.get(key);
    if (prev == null) {
      prev = 0;
    }

    map.put(key, prev + count);

  }

  @Override
  public int get(String key) {
    return map.get(key);
  }
}
