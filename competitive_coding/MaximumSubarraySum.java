package cp;

import java.util.TreeSet;

// https://www.hackerrank.com/challenges/maximum-subarray-sum/problem
public class MaximumSubarraySum {

	static long maximumSum(long[] a, long m) {
        if(m == 1) {
            // base case
            //always 0
            return 0;
        }
        
        // assuming modulo operator is distributive over addition
        // the ways exactly k subarray sum works that same way it will work to find
        // maximum remainder
        
        // binary search on left side while doing prefix modulo sum
        // to find closest remainder to maximize remainder
        TreeSet<Long> prefixmodulosum = new TreeSet<>(); // BST
        prefixmodulosum.add(0L); // to cover whole subarray
        
        int l = a.length;
        long prev = 0;
        long max = Long.MIN_VALUE; // max modulo
        for(int i = 0; i < l; i++) {
            long t = prev + a[i];
            if(t >= m) {
                // modulo reduction
                t %= m;
            }
            // we always try to find t + 1
            long required = t + 1;
            
            // try to find closest uppper one if exists
            Long ceil = prefixmodulosum.ceiling(required);
            long rem;
            if(ceil != null) {
                // found
                rem = t - ceil;
                if(rem < 0) {
                    // minus modulo
                    rem += m;
                }
            } else {
                // remainder itself
                rem = t;
            }

            if(max < rem) {
                // new maximum remainder
                max = rem;
            }

            // add to BST
            prefixmodulosum.add(t);
            prev = t;
        }

        return max;
    }
}