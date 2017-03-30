package com.github.bobi4597.concurrency.counter;

/**
 * Definition of the Counter.
 */
public interface Counter {

  void add(final String key, final int count);

  int get(final String key);
}
