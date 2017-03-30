package com.github.bobi4597.concurrency.producerconsumer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Producer/Consumer pattern.
 */
public class ProducerConsumerPatternWithWaitNotifyTest {

  private static final int BUFFER_SIZE = 10;

  @Test
  public void test_singleProducerSingleConsumer() throws InterruptedException {

    // Given
    Buffer buffer = new Buffer(BUFFER_SIZE);
    Object lock = new Object();

    Producer producer = new Producer(buffer, lock);
    Consumer consumer = new Consumer(buffer, lock);

    Runnable producerRunnable = () -> {
      for (int i = 0; i < 100; ++i) {
        System.out.println("P: " + i);
        producer.produce(i);
      }
    };

    Runnable consumerRunnable = () -> {
      for (int i = 0; i < 94; ++i) {
        System.out.println("   C: " + i);
        consumer.consume();
      }
    };


    // When
    Thread producerThread = new Thread(producerRunnable);
    producerThread.setName("Producer_Thread");
    Thread consumerThread = new Thread(consumerRunnable);
    consumerThread.setName("Consumer_Thread");

    producerThread.start();
    consumerThread.start();

    producerThread.join();
    consumerThread.join();

    // Then
    //    Producer produces 100 elements,
    //    but the consumer consumes only 94.
    //    6 should still be in the buffer.
    Assert.assertEquals(6, buffer.getCount());
  }

  @Test
  public void test_multipleProducersMultipleConsumers() throws InterruptedException {

    // Given
    Buffer buffer = new Buffer(BUFFER_SIZE);
    Object lock = new Object();

    final Producer producer1 = new Producer(buffer, lock);
    final Producer producer2 = new Producer(buffer, lock);
    final Consumer consumer1 = new Consumer(buffer, lock);
    final Consumer consumer2 = new Consumer(buffer, lock);

    Runnable producerRunnable = () -> {
      for (int i = 0; i < 100; ++i) {
        System.out.println("P1: " + i);
        producer1.produce(i);
      }
    };
    Runnable producerRunnable2 = () -> {
      for (int i = 0; i < 100; ++i) {
        System.out.println("P2: " + i);
        producer2.produce(i);
      }
    };

    Runnable consumerRunnable = () -> {
      for (int i = 0; i < 99; ++i) {
        System.out.println("C1: " + i);
        consumer1.consume();
      }
    };

    Runnable consumerRunnable2 = () -> {
      for (int i = 0; i < 98; ++i) {
        System.out.println("C2: " + i);
        consumer2.consume();
      }
    };

    // When
    Thread producerThread1 = new Thread(producerRunnable);
    Thread producerThread2 = new Thread(producerRunnable2);
    Thread consumerThread1 = new Thread(consumerRunnable);
    Thread consumerThread2 = new Thread(consumerRunnable2);

    producerThread1.start();
    producerThread2.start();
    consumerThread1.start();
    consumerThread2.start();

    producerThread1.join();
    producerThread2.join();
    consumerThread1.join();
    consumerThread2.join();

    // Then
    //    Producers produce 200 elements,
    //    but the consumers consume only 99 + 98 == 197.
    //    3 should still be in the buffer.
    Assert.assertEquals(3, buffer.getCount());
  }

}
