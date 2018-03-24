package synthesizer;

import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T>{
    /*return size of the buffer*/
    int capacity();
    /*return number of items currently in the buffer*/
    int fillCount();
    /*add item x to the end*/
    void enqueue(T x);
    /*return (but do not delete ) item from the front*/
    T dequeue();
    T peek();
    /*is the buffer empty (fillCount equals zero)?*/
    default boolean isEmpty() {
        return this.fillCount() == 0;
    }
    default boolean isFull() {
        return this.fillCount() == this.capacity();
    }

    @Override
    Iterator<T> iterator();
}