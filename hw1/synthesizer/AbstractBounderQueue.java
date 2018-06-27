package synthesizer;

public abstract class AbstractBounderQueue<T> implements BoundedQueue<T>{
    protected int fillCount;
    protected int capacity;
}
