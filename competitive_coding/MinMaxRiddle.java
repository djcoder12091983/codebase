package cp;

import java.util.Stack;

// https://www.hackerrank.com/challenges/min-max-riddle/problem
public class MinMaxRiddle {

	static long[] riddle(long[] arr) {
        int n = arr.length;
        // used in find previous and next smaller
        Stack<Integer> s = new Stack<>();

        // Arrays to store previous and next smaller
        int left[] = new int[n + 1];
        int right[] = new int[n + 1];

        // elements of left[] and right[]
        // left smaller and right smaller
        for (int i = 0; i < n; i++) {
            left[i] = -1;
            right[i] = n;
        }

        // fill elements of left[]
        for (int i = 0; i < n; i++) {
            while (!s.empty() && arr[s.peek()] >= arr[i])
                s.pop();

            if (!s.empty()) {
                left[i] = s.peek();
            }

            s.push(i);
        }

        s.removeAllElements(); // remove all elements

        // Fill elements of right[]
        for (int i = n - 1; i >= 0; i--) {
            while (!s.empty() && arr[s.peek()] >= arr[i])
                s.pop();

            if (!s.empty()) {
                right[i] = s.peek();
            }

            s.push(i);
        }

        // create and initialize answer array
        long ans[] = new long[n + 1];
        for(int i = 0; i <= n; i++) {
            ans[i] = 0;
        }

        // fill answer array by comparing minimums of all
        // lengths computed using left[] and right[]
        for (int i = 0; i < n; i++) {
            // length of the interval
            int len = right[i] - left[i] - 1;

            // arr[i] is a possible answer for this length
            // 'len' interval, check if arr[i] is more than
            // max for 'len'
            ans[len] = Math.max(ans[len], arr[i]);
        }

        // Some entries in ans[] may not be filled yet. Fill
        // them by taking values from right side of ans[]
        for(int i = n - 1; i >= 1; i--) {
            ans[i] = Math.max(ans[i], ans[i + 1]);
        }

        return ans;
    }
}