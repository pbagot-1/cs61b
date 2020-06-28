package es.datastructur.synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public int capacity() {
        return rb.length;
    }     // return size of the buffer

    @Override
    public int fillCount() {
        return fillCount;
    }     // return number of items currently in the buffer

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
        this.rb = (T[]) new Object[capacity];
        //       first, last, and fillCount should all be set to 0.
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity()) {
            throw new RuntimeException("Ring Buffer overflow");
        } else {
            rb[last] = x;
            fillCount += 1;
            last += 1;
            if (last == capacity()) {
                last = 0;
            }
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        } else {
            T temp = rb[first];
            rb[first] = null;
            fillCount -= 1;
            first += 1;
            if (first == capacity()) {
                first = 0;
            }
            return temp;
        }

    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        } else {
            return rb[first];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new myIterator();
    }

    private class myIterator implements Iterator<T> {
        private int start;

        myIterator() {
            start = first;
        }

        public boolean hasNext() {
            return start < last;
        }

        public T next() {
            int spot = start;
            start += 1;
            return rb[spot];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this.fillCount() != ((ArrayRingBuffer) o).fillCount()) {
            return false;
        }

        Iterator it1 = iterator();
        Iterator it2 = ((ArrayRingBuffer) o).iterator();

        while (it1.hasNext()) {
            if (it1.next() != it2.next()) {
                return false;
            }
        }
        return true;
    }
}


