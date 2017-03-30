package com.github.bobi4597.concurrency.deadlock;

/**
 * Deadlock example.
 */
public class Deadlock {

  private Object lock1 = new Object();
  private Object lock2 = new Object();

  /**
   * Method synchronized on {@code lock1} that invokes b().
   */
  public void a() {
    synchronized (lock1) {
      System.out.printf("Thread [%s] in method [a()].\n", Thread.currentThread().getName());
      b();
    }
  }

  /**
   * Method synchronized on {@code lock2} that invokes c().
   */
  public void b() {
    synchronized (lock2) {
      System.out.printf("Thread [%s] in method [b()].\n", Thread.currentThread().getName());
      c();
    }
  }

  /**
   * Method synchronized on {@code lock1}.
   */
  public void c() {
    synchronized (lock1) {
      System.out.printf("Thread [%s] in method [c()].\n", Thread.currentThread().getName());
    }
  }
}
