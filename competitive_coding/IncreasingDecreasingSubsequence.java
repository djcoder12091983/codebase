package hackerearth;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class IncreasingDecreasingSubsequence {
	
	// longest increasing sequence
	static int lis(int[] arr) {
        int max = 1;
        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0, 0); // base case
        // count mapping to remove duplicate count
        Map<Integer, Integer> count = new HashMap<>();

        for(int a : arr) {
        	// strictly increasing order
            int floor = dp.floorKey(a - 1);
            int c = dp.get(floor);
            int nc = c + 1; // new sequence count
            if(count.containsKey(nc)) {
                // remove old entry if exists
                int old = count.get(nc);
                dp.remove(old);
            }
            if(nc > max) {
                // new max
                max = nc;
            }
            // both way mapping
            dp.put(a, nc);
            count.put(nc, a);
        }

        return max;
    }
	
	// longest decreasing sequence
	static int lds(int[] arr) {
        int max = 1;
        // note: order changed
        TreeMap<Integer, Integer> dp = new TreeMap<>(new Comparator<Integer>() {
    		@Override
    		public int compare(Integer d1, Integer d2) {
    			return d2.compareTo(d1);
    		}
		});
        dp.put(Integer.MAX_VALUE, 0); // base case
        Map<Integer, Integer> count = new HashMap<>();

        for(int a : arr) {
        	// strictly decreasing order
            int floor = dp.floorKey(a + 1);
            int c = dp.get(floor);
            int nc = c + 1; // new sequence count
            if(count.containsKey(nc)) {
                // remove old entry if exists
                int old = count.get(nc);
                dp.remove(old);
            }
            if(nc > max) {
                // new max
                max = nc;
            }
            // both way mapping
            dp.put(a, nc);
            count.put(nc, a);
        }

        return max;
    }
    
    public static void main(String[] args) {
		System.out.println(lis(new int[] {12, 10, 8, 6, 5, 4, 100, 200, 13, 17, 123, 56, 78, 77, 125, 3, 2, 1}));
    	/*TreeMap<Integer, Integer> t = new TreeMap<>(new Comparator<Integer>() {
    		@Override
    		public int compare(Integer d1, Integer d2) {
    			return d2.compareTo(d1);
    		}
		});
    	t.put(Integer.MAX_VALUE, 0);
    	t.put(8, 0);
    	t.put(4, 0);
    	t.put(12, 0);
    	System.out.println(t.floorKey(9));*/
	}

}