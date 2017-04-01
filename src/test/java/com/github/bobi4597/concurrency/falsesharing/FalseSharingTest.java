package com.github.bobi4597.concurrency.falsesharing;

/**
 * Test for a False Sharing scenario.
 */
public class FalseSharingTest {
  private static final int ITERATIONS = 100_000_000;
  private static final int NUM_THREADS_MAX = 8;

  public static final class PaddedLong {
    public long q1, q2, q3, q4, q5, q6;
    public volatile long value;
    public long p1, p2, p3, p4, p5, p6;
  }

  public static final class UnPaddedLong {
    public volatile long value;
  }

  private static PaddedLong[] paddedLongs;
  private static UnPaddedLong[] unPaddedLongs;

  static {
    paddedLongs = new PaddedLong[NUM_THREADS_MAX];
    for (int i = 0; i < paddedLongs.length; ++i) {
      paddedLongs[i] = new PaddedLong();
    }

    unPaddedLongs = new UnPaddedLong[NUM_THREADS_MAX];
    for (int i = 0; i < unPaddedLongs.length; ++i) {
      unPaddedLongs[i] = new UnPaddedLong();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    runBenchmark();
  }

  private static void runBenchmark() throws InterruptedException {

    long startTime, endTime;

    for (int n = 1; n <= NUM_THREADS_MAX; ++n) {
      Thread[] threads = new Thread[n];
      for (int j = 0; j < threads.length; ++j) {
        threads[j] = new Thread(createPaddedRunnable(j));
      }

      startTime = System.currentTimeMillis();
      for (Thread t: threads) {t.start();}
      for (Thread t: threads) {t.join();}
      endTime = System.currentTimeMillis();
      System.out.printf("   Padded # threads %d - T = %dms\n", n, endTime - startTime);

      for (int j = 0; j < threads.length; ++j) {
        threads[j] = new Thread(createUnPaddedRunnable(j));
      }

      startTime = System.currentTimeMillis();
      for (Thread t: threads) {t.start();}
      for (Thread t: threads) {t.join();}
      endTime = System.currentTimeMillis();
      System.out.printf("Un Padded # threads %d - T = %dms\n", n, endTime - startTime);

    }
  }

  private static Runnable createPaddedRunnable(final int k) {
    return () -> {
      long i = 0;
      while (i++ < ITERATIONS) {
        paddedLongs[k].value = i;
      }
    };
  }
  private static Runnable createUnPaddedRunnable(final int k) {
    return () -> {
      long i = 0;
      while (i++ < ITERATIONS) {
        unPaddedLongs[k].value = i;
      }
    };
  }

  //////////////////////////////////////////////////////
  // Result of executing this benchmark on my laptop. //
  //////////////////////////////////////////////////////
  //    Padded # threads 1 - T = 843ms
  // Un Padded # threads 1 - T = 838ms
  //    Padded # threads 2 - T = 626ms
  // Un Padded # threads 2 - T = 3215ms
  //    Padded # threads 3 - T = 645ms
  // Un Padded # threads 3 - T = 6604ms
  //    Padded # threads 4 - T = 797ms
  // Un Padded # threads 4 - T = 7488ms
  //    Padded # threads 5 - T = 857ms
  // Un Padded # threads 5 - T = 7684ms
  //    Padded # threads 6 - T = 1035ms
  // Un Padded # threads 6 - T = 7953ms
  //    Padded # threads 7 - T = 1206ms
  // Un Padded # threads 7 - T = 8133ms
  //    Padded # threads 8 - T = 1329ms
  // Un Padded # threads 8 - T = 9379ms
}
