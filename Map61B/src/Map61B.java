//package Map61B;
import java.util.List;

public interface Map61B<K, V> {
    /*Return true if this map contains a mapping for the specified key.*/
    boolean containsKey(K key);

    V get(K key);

    void put(K key, V value);

    List<K> keys();
}