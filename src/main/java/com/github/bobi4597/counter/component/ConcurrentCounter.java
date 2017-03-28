package com.github.bobi4597.counter.component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Implementation of the Counter interface that allows multiple threads to access it concurrently.
 */
public class ConcurrentCounter implements Counter {

  final ConcurrentMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

  @Override
  public void add(String key, int count) {

    AtomicInteger currentCount = new AtomicInteger(0);
    AtomicInteger oldCount = map.putIfAbsent(key, currentCount);

    // the key had a count before
    if (oldCount != null) {
      currentCount = oldCount;
    }
    // this is atomic operation
    currentCount.addAndGet(count);
  }

  @Override
  public int get(String key) {
    return map.get(key).intValue();
  }
}
