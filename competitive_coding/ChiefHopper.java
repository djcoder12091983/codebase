package cp;

// https://www.hackerrank.com/challenges/chief-hopper/problem
public class ChiefHopper {
	
	static long chiefHopper(int h[]) {
        // we will start searching with total
        long total = 0;
        int l = h.length;
        for(int i = 0; i < l; i++) {
            total += h[i];
        }
        
        // now do binary search 1 to s
        long start = 1, end = total;
        long min = 0;
        while(start <= end) {
            long mid = (start + end) / 2;
            // check whether this can meet requirement
            long energy = mid;
            for(int i = 0; i < l; i++) {
                long t = h[i];
                if(energy <= t) {
                    energy -= t - energy;
                } else {
                    energy += energy - t;
                }
                if(energy >= total || energy < 0) {
                    // energy reaches below 0
                    // or gained energy gets higher than total_energy
                    // so no need to compute any more (it's a positive case)
                    break;
                }
            }
            if(energy >= 0) {
                // we can find minimal result on left side
                min = mid; // is a valid starting energy
                end = mid - 1; // find on left side
            } else {
                // energy is negative so we will find or right side
                start = mid + 1;
            }
        }
        
        return min;
    }

}
