package cp;

import java.util.Arrays;

// https://www.hackerrank.com/challenges/pairs/problem
public class Pairs {

	// binary search start to end
    static boolean search(int[] arr, int k, int start) {
        int s = start;
        int e = arr.length - 1;
        while(s <= e) {
            int mid = (s + e) / 2;
            if(arr[mid] == k) {
                // found
                return true;
            } else if(arr[mid] > k) {
                // left side
                e = mid - 1;
            } else {
                // right side
                s = mid + 1;
            }
        }

        return false; // not found
    }

    // Complete the pairs function below.
    static int pairs(int k, int[] arr) {
        // sort elements
        Arrays.sort(arr);

        // for each element try to find pair
        int c = 0;
        int l = arr.length;
        for(int i = 0; i < l; i++) {
            int a = arr[i];
            // required element to pair
            int required = a + k;

            // binary search on right side
            boolean f = search(arr, required, i + 1);
            if(f) {
                // note all elements are distinct
                // only one pair possible
                c++;
            }
        }

        return c;
    }
}
