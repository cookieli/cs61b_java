public class ArrayMap<K,V> {
    private K[] keys;
    private V[] values;
    private int size;
    public ArrayMap() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }
    public void put(K key, V value) {

    }
}
