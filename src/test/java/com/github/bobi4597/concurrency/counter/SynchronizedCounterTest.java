package com.github.bobi4597.concurrency.counter;

import org.junit.Assert;
import org.junit.Test;
/**
 * Tests for the Concurrent Counter.
 */
public class SynchronizedCounterTest {

  @Test
  public void test_singleThread() throws InterruptedException {

    // Given
    final Counter counter = new SynchronizedCounter();

    Runnable runnable = () -> {
      for (int j = 0; j < 1_000; ++j) {
        counter.add("Test", 1);
      }
    };

    // When
    Thread t = new Thread(runnable);
    t.start();
    t.join();

    // Then
    Assert.assertEquals(1_000, counter.get("Test"));
  }

  @Test
  public void test_multiThreaded() throws InterruptedException {
    // Given
    final Counter counter = new SynchronizedCounter();

    long t1 = System.currentTimeMillis();

    // When
    Runnable runnable = () -> {
      String s1 = "Test";
      String s2 = "Test";

      for (int j = 0; j < 1_000; ++j) {
        if (j % 2 == 0) {
          counter.add(s1, 1);
        } else {
          counter.add(s2, 1);
        }
      }
    };

    Thread[] threads = new Thread[1_000];
    for (int i = 0; i < 1_000; ++i) {
      threads[i] = new Thread(runnable);
      threads[i].start();
    }

    for (int i = 0; i < 1_000; ++i) {
      threads[i].join();
    }

    long t2 = System.currentTimeMillis();
    System.out.println("Total time: " + (t2 - t1) + " ms");
    // Then
    Assert.assertEquals(1_000_000, counter.get("Test"));
  }

}
