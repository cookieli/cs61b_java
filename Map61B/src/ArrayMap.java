//package Map61B;

import org.junit.Assert.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import org.junit.Test;


import static org.junit.Assert.*;

/*
* An array based implementation of the Map61B class
*/

public class ArrayMap<K, V> implements Map61B<K,V>{
    private K[] keys;
    private V[] values;
    int size;

    public ArrayMap() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }

    private int KeyIndex(K key) {
        for(int i = 0; i < size; i += 1) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }
    public int size() {
        return size;
    }
    @Override
    public boolean containsKey(K key) {
        int index = KeyIndex(key);
        return index > -1;
    }

    @Override
    public List<K> keys() {
        List<K> keylist = new ArrayList<>();
        for(int i = 0; i < size; i += 1) {
            keylist.add(keys[i]);
        }
        return keylist;
    }

    @Override
    public void put(K key, V value) {
        int index = KeyIndex(key);
        if(index == -1) {
            keys[size] = key;
            values[size] = value;
            size += 1;
            return;
        }
        values[index] = value;
    }

    @Override
    public V get(K key) {
        int index = this.KeyIndex(key);
        if (index < 0) {
            throw new IllegalArgumentException("Key " + key + " does not exist in map.");
        }
        return values[index];
    }

    public static void main(String[] args) {
//        ArrayMap<String, Integer> m = new ArrayMap<>();
//        m.put("horse", 3);
//        System.out.print(m.get("yolp"));
//        System.out.println("ayyy lmao");
//        throw new RuntimeException("For no reason");
        List<Integer> friends = new ArrayList<>();
        friends.add(5);
        friends.add(23);
        friends.add(42);
        Iterator<Integer> seer = friends.iterator();
        while (seer.hasNext()) {
            System.out.println(seer.next());
        }
        for (int x: friends) {
            System.out.println(x);
        }
    }
}