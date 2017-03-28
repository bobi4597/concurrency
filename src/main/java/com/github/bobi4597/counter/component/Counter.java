package com.github.bobi4597.counter.component;

/**
 * Definition of the Counter.
 */
public interface Counter {

  void add(final String key, final int count);

  int get(final String key);
}
