package com.github.bobi4597.concurrency.producerconsumer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Producer/Consumer pattern.
 */
public class SingleProducerSingleConsumerTest {

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

    Runnable consumerRunnable1 = () -> {
      for (int i = 0; i < 94; ++i) {
        System.out.println("   C: " + i);
        consumer.consume();
      }
    };

    // When
    Thread producerThread = new Thread(producerRunnable);
    producerThread.setName("Producer_Thread");
    Thread consumerThread = new Thread(consumerRunnable1);
    consumerThread.setName("Consumer_Thread");


    producerThread.start();
    Thread.sleep(100);
    consumerThread.start();

    producerThread.join();
    consumerThread.join();

    // Then
    //    Producer produces 100 elements,
    //    but the consumer consumes only 94.
    //    6 should still be in the buffer.
    Assert.assertEquals(6, buffer.getCount());
  }
}
