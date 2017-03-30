package com.github.bobi4597.concurrency.producerconsumer;

/**
 * Producer.
 */
public class Producer {

  private Buffer buffer;
  private Object lock;

  public Producer(Buffer buffer, Object lock) {
    this.buffer = buffer;
    this.lock = lock;
  }

  public void produce(int producedElement) {
    synchronized (lock) {
      while (buffer.isFull()) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      buffer.addElement(producedElement);
      lock.notify();
    }
  }

}
