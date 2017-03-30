package com.github.bobi4597.concurrency.producerconsumer;

/**
 * The buffer.
 */
public class Buffer {

  private int count;
  private int[] buffer;
  private int size;

  public Buffer(int size) {
    this.size = size;
    this.buffer = new int[size];
    this.count = 0;
  }

  public boolean isFull() {
    return count == size;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public void addElement(int x) {
    buffer[count] = x;
    ++count;
  }

  public int removeElement() {
    --count;
    return buffer[count];
  }

  public int getCount() {
    return count;
  }
}
