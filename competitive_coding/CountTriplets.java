package cp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://www.hackerrank.com/challenges/count-triplets-1/problem
public class CountTriplets {

	// positions is array
    // do binary search
    static int ceilposition(List<Integer> positions, int p) {
        int l = positions.size();
        if(p > positions.get(l - 1)) {
            // not possible
            return l;
        }
        int s = 0, e = l - 1;
        while(s < e) {
            int mid = (s + e) / 2;
            int p1 = positions.get(mid);
            if(p1 == p) {
                return mid;
            } else if(p1 < p) {
                // right half
                s = mid + 1;
            } else {
                // left half
                e = mid - 1;
            }
        }

        int p1 = positions.get(s);
        if(p1 >= p) {
            // match
            return s;
        } else {
            // next position
            return s + 1;
        }
    }

    // Complete the countTriplets function below.
    static long countTriplets(List<Long> arr, long r) {
        long c = 0;
        Map<Long, List<Integer>> map = new HashMap<>(); // positional index
        // preparing look up table
        int l = arr.size();
        for(int i = 0; i < l; i++) {
            long a = arr.get(i);
            List<Integer> p = map.get(a);
            if(p == null) {
                p = new ArrayList<>(2);
                map.put(a, p);
            }
            p.add(i); // adds position
        }

        // find triplets
        for(long key : map.keySet()) {
            if(r == 1) {
                int s = map.get(key).size();
                if(s >= 3) {
                    // s choose 3
                    long t = s;
                    c += (t * ( t -1) * (t - 2)) / 6;
                }
            } else {
                // other than 1
                long a2 = key * r; // 2nd
                long a3 = a2 * r; // 3rd
                if(map.containsKey(a2) && map.containsKey(a3)) {
                    List<Integer> keyp = map.get(key);
                    List<Integer> a2p =  map.get(a2);
                    int s2 = a2p.size();
                    
                    int p2 = ceilposition(a2p, keyp.get(0) + 1);
                    if(p2 < s2) {
                        // valid position in list2
                        
                        // a2count prefix sum for reuse
                        // no need to count for pair <key, a2, a3>
                        long a2count[] = new long[s2 + 1];
                        List<Integer> a3p =  map.get(a3);
                        int s3 = a3p.size();
                        // now prepare cache for a2count
                        long t = 0;
                        for(int i = p2; i < s2; i++) {
                            int p3 = ceilposition(a3p, a2p.get(i) + 1);
                            if(p3 < s3) {
                                // valid position in list3
                                a2count[i + 1] = a2count[i]  + s3 - p3;
                                t = a2count[i + 1];
                            } else {
                                // invalid position
                                a2count[i + 1] = -1;
                            }
                        }
                        
                        // now check for each key
                        int s = keyp.size();
                        for(int i = 0; i < s; i++) {
                            p2 = ceilposition(a2p, keyp.get(i) + 1);
                            if(p2 < s2 && a2count[p2] >= 0) {
                                // valid position in list2
                                // reuse cache count (a2count)
                                c += t - a2count[p2];
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return c;
    }
}