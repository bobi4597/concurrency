package com.github.bobi4597.concurrency.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

/**
 * Test for a Multiple Producers/Consumers pattern, using BlockingQueue.
 */
public class ProducerConsumerPatternWithBlockingQueueTest {

  static class Producer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      for (int i = 0; i < 1000; ++i) {
        try {
          System.out.println("Producing: " + i);
          queue.put(i);

        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static class Consumer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      while (true) {
        try {
          final Integer consumedElement = queue.take();
          System.out.println("Consumed: " + consumedElement);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Test
  public void test_multipleProducersConsumers() throws InterruptedException {
    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    Thread producerThread = new Thread(new Producer(queue));
    Thread producerThread2 = new Thread(new Producer(queue));
    Thread consumerThread = new Thread(new Consumer(queue));
    Thread consumerThread2 = new Thread(new Consumer(queue));

    producerThread.start();
    producerThread2.start();
    consumerThread.start();
    consumerThread2.start();

    producerThread.join();
    producerThread2.join();
    consumerThread.join();
    consumerThread2.join();
  }
}
