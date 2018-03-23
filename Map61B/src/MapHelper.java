import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

public class MapHelper {


    public static <K, V>  V get(Map61B<K, V> sim, K key) {
        if(sim.containsKey(key))
            return sim.get(key);
        return null;
    }
    public static <K extends Comparable<K>, V> K maxKey(Map61B<K,V> map){
        List<K> keylist = map.keys();
        K largest = keylist.get(0);
        for (K k: keylist) {
            if (k.compareTo(largest) > 0) {
                largest = k;
            }
        }
        return largest;
    }

}
