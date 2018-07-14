package hw4.hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO: Write a utility function that returns true if the given oomages 
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        Map<Integer, Integer> buckets = new HashMap<>();
        for(Oomage oomage:oomages){
            int hash = (oomage.hashCode() & 0x7FFFFFFF) % M;
            if(buckets.containsKey(hash)){
                int count = buckets.get(hash);
                buckets.put(hash,++count);
            } else {
                buckets.put(hash, 1);
            }
        }

        for(int hash: buckets.keySet()){
            boolean hasGoodDistribution = buckets.get(hash) >= oomages.size()/50;
            hasGoodDistribution = hasGoodDistribution && (buckets.get(hash) <= oomages.size() / 2.5);
            if(!hasGoodDistribution)
                return false;
        }

        return true;
    }
}
