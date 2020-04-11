package cp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// https://www.hackerrank.com/challenges/longest-increasing-subsequent/problem
public class LIS {

    // Complete the longestIncreasingSubsequence function below.
    static int longestIncreasingSubsequence(int[] arr) {

        int max = 1;

        // save previus result
        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0, 0); // base case

        // count mapping to remove duplicate count
        Map<Integer, Integer> count = new HashMap<>();

        for(int a : arr) {
            int floor = dp.floorKey(a - 1); // strictly descending floor
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
}