package com.github.bobi4597.concurrency.producerconsumer;

/**
 * Consumer.
 */
public class Consumer {

  private Buffer buffer;
  private Object lock;

  public Consumer(Buffer buffer, Object lock) {
    this.buffer = buffer;
    this.lock = lock;
  }

  public int consume() {
    synchronized(lock) {
      if (buffer.isEmpty()) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      final int consumedElement = buffer.removeElement();
      //System.out.println("      " + Thread.currentThread().getName() + ": " + consumedElement);
      lock.notify();
      return consumedElement;

    }
  }

}
