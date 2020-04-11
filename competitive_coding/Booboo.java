package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Booboo {
	
	// check for M partition with maximum sum S
	static boolean check(long s, long q[], int m, int n) {
		int sub = 0;
		long sum = 0;
		for(int i = 0; i < n; i++) {
			if(q[i] > s) {
				// straight way not possible
				return false;
			}
			
			sum += q[i];
			if(sum > s) {
				// partition
				sub++;
				sum = q[i];
			}
		}
		sub++;
		
		// if sub count less than m then partition possible
		return sub <= m;
	}
	
	// finds min
	static long min(long max, long q[], int m, int n) {
		// ;possible 1 to max
		long start = 1;
		long end = max;
		
		// binary search
		long ans = start;
		while(start <= end) {
			long mid = (start + end) / 2;
			if(check(mid, q, m, n)) {
				// try left side
				ans = mid; // tentative answer
				end = mid - 1;
			} else {
				// try right side
				start = mid + 1;
			}
		}
		
		return ans;
	}
	
	public static void main(String args[]) throws Exception {
		//System.out.println(min(9, new long[] {1, 2, 2, 1, 3}, 3, 5));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tokens = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(tokens.nextToken());
		int m = Integer.parseInt(tokens.nextToken());
		
		tokens = new StringTokenizer(reader.readLine());
		long sum = 0;
		long q[] = new long[n];
		for(int i = 0; i < n; i++) {
			q[i] = Long.parseLong(tokens.nextToken());
			sum += q[i];
		}
		System.out.println(min(sum, q, m, n));
		
		reader.close();
	}
}