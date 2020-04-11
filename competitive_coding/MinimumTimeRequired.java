package cp;

import java.util.HashMap;
import java.util.Map;

// https://www.hackerrank.com/challenges/minimum-time-required/problem
public class MinimumTimeRequired {

	// Complete the minTime function below.
    static long minTime(long[] machines, long goal) {
        // days frequency map
        Map<Long, Integer> days = new HashMap<>();

        // prepare greedy
        long max = Long.MIN_VALUE;
        for(long day : machines) {
            if(days.containsKey(day)) {
                // incremental
                days.put(day, days.get(day) + 1);
            } else {
                // first time
                days.put(day, 1);
            }
            
            if(max < day) {
                // new max
                max = day;
            }
        }

        // maximum days to complete task
        long maxdays = Double.valueOf(Math.ceil(max * goal / days.get(max))).longValue();
        // now greedily try to finish goal
        // now do binary search on 1->maxdays and try to find suitable days
        long start = 1, end = maxdays;
        long mindays = Long.MAX_VALUE;
        while(start <= end) {
            
            long mid = (start + end) / 2;
            long t[] = itemsproduced(days, mid); // minimum/maximum items produced
            if(goal <= t[1]) {
                // found result (inside range)
                // but we can try more minimum on left side
                end = mid - 1;
                if(goal >= t[0] && goal <= t[1] && mindays > mid) {
                    // new minimum result
                    mindays = mid;
                }
            } else if(t[1] < goal) {
                // by maximum items goal can't be produced
                // find right side
                start = mid + 1;
            }
        }
        
        // won't occur
        return mindays;
    }
    
    // how many items can be produced by given days
    static long[] itemsproduced(Map<Long, Integer> daysf, long days) {
        long minitems = 0, maxitems = 0;
        for(long d : daysf.keySet()) {
            // minimum items, last day 1 item
            int f = daysf.get(d);
            minitems += (days / d - 1) * f + 1;
            // maximum items, full items
            maxitems += days / d * f;
        }
        
        return new long[]{minitems, maxitems};
    }
}