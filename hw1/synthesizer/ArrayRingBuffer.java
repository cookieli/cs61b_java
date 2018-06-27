// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBounderQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    private class bufferIterator implements Iterator<T> {
        private int cursor;
        private int step;
        public bufferIterator() {
            this.cursor = first;
            this.step = 0;
        }
        @Override
        public boolean hasNext() {
            return this.step != capacity;
        }

        @Override
        public T next() {
            T temp = rb[this.cursor];
            this.cursor = (this.cursor + 1) % capacity;
            this.step++;
            return  temp;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.first = 0;
        this.last  = 0;
        this.capacity = capacity;
        this.fillCount = 0;
        this.rb = (T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int fillCount() {
        return this.fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (this.fillCount == this.capacity ) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            this.rb[this.last] = x;
            this.fillCount++;
            if(this.fillCount == this.capacity) ;
            else this.last = (this.last + 1) % this.capacity;
        }
    }
    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (this.fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");

        } else {
            T temp =  this.rb[this.first];
            this.first = (this.first + 1) % this.capacity;
            this.fillCount--;
            return temp;
        }

    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return this.rb[this.first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    @Override
    public Iterator<T> iterator() {
        return new bufferIterator();
    }

    public static void main(String[] args) {
        BoundedQueue<Integer> bq = new ArrayRingBuffer<>(8);
        for(int i = 0; i < bq.capacity(); i++) {
            bq.enqueue(i);
        }
        Iterator<Integer> i = bq.iterator();
        while (i.hasNext()){
            System.out.println(i.next());
        }
    }
}
