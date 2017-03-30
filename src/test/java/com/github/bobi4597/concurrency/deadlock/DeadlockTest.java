package com.github.bobi4597.concurrency.deadlock;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
/**
 * Test that a deadlock will occur.
 */
public class DeadlockTest {

  @Test
  @Ignore
  public void test_deadlock() throws InterruptedException {
    // Given
    Deadlock deadlock = new Deadlock();

    Runnable r1 = () -> {
      deadlock.a();
    };

    Runnable r2 = () -> {
      deadlock.b();
    };

    Thread t1 = new Thread(r1);
    t1.setName("T-1");

    Thread t2 = new Thread(r2);
    t2.setName("T-2");

    t1.start();
    t2.start();

    Runnable r3 = () -> {
      try {
        Thread.sleep(3000);
        System.exit(0);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    Thread t3 = new Thread(r3);
    t3.start();

    t1.join();
    Assert.fail("The code shouldn't reach this point!");

    t2.join();
    t3.join();
  }
}
