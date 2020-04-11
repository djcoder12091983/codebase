package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class PalindromeNumber {
	
	static int LIMIT = 1000000000;
	
	int P[] = new int[101];
	int PC[] = new int[101];
	int N;
	
	public PalindromeNumber() {
		// base cases
		P[0] = 1;
		P[1] = 10;
		P[2] = 10;
		PC[0] = 0;
		PC[1] = 9;
		PC[2] = 18;
		
		long c = 0;
		c  = PC[2];
		int i = 3;
		// initialization of palindrome formation pattern 
		for(i = 3; c <= LIMIT; i++) {
			P[i] = 10 * P[i - 2];
			PC[i] = PC[i - 1] + 9 * P[i - 2];
			c = PC[i];
		}
		
		this.N = i;
	}
	
	// palindrome number close to >= X
	long close2X(long X) {
		
		String xstr = String.valueOf(X);
		int l = xstr.length();
		int mid = Double.valueOf(Math.ceil(l / 2.0)).intValue();
		
		char t[] = new char[l];
		for(int i = 0; i < mid; i++) {
			t[i] = xstr.charAt(i);
			t[l - i - 1] = xstr.charAt(i);
		}
		
		long temp1 = Long.valueOf(new String(t));
		if(temp1 >= X) {
			// found solution
			return temp1;
		} else {
			// change mid digit value to +1
			// if 9 then recursively change it
			for(int i = mid - 1; i >= 0; i--) {
				if(t[i] != '9') {
					// straight way +1
					char ch = (char)((int)t[i] + 1);
					t[i] = ch;
					t[l - i - 1] = ch;
					break;
				} else {
					// otherwise recursively change
					t[i] = '0';
					t[l - i - 1] = '0';
				}
			}
			
			return Long.valueOf(new String(t));
		}
	}
	
	// kth palindrome which is greater or equals to X
	long kthPalindrome(int K) {
		if(K == 0) {
			return 0;
		}
		int pdigits[] = null;
		for(int i = 0; i < N - 1; i++) {
			if(K > PC[i] && K <= PC[i + 1]) {
				// valid range
				pdigits = new int[i + 1];
				int rem = K - PC[i] - 1;
				int j = 0;
				int dc = i + 1;
				while(dc > 0) {
					// till Kth palindrome not found
					int pidx = Math.max(dc - 2, 0);
					int d = rem / P[pidx];
					pdigits[i - j] = pdigits[j] = j > 0 ? d : (d + 1);
					rem -= d * P[pidx];
					dc -= 2;
					j++;
				}
				
				// done
				break;
			}
		}
 
		int l = pdigits.length;
		long p = 1l;
		long pno = pdigits[l - 1];
		for(int i = l - 2; i >= 0; i--) {
			pno += pdigits[i] * p * 10;
			p *= 10;
		}
		
		return pno;
	}
	
	// given a pno number find k (inverse of kthPalindrome)
	int findK(long pno) {
		for(int i = 0; i < N - 1; i++) {
			long pno1 = kthPalindrome(PC[i]);
			long pno2 = kthPalindrome(PC[i + 1]);
			if(pno >= pno1 && pno <= pno2) {
				// do BS
				int start = PC[i];
				int end = PC[i + 1];
				while(start <= end) {
					int mid = (start + end) / 2;
					long tpno = kthPalindrome(mid);
					if(tpno == pno) {
						// found k
						return mid;
					} else if(tpno > pno) {
						// move left
						end = mid - 1;
					} else {
						// move right
						start = mid + 1;
					}
				}
			}
		}
		
		return -1; // won't happen
	}
	
	// actual solution
	long solve(long X, int K) {
		long tx = close2X(X);
		int xp = findK(tx);
		return kthPalindrome(K + xp - 1);
	}
	
	public static void main(String[] args) throws Exception {
		PalindromeNumber pn = new PalindromeNumber();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(reader.readLine());
		while(--t >= 0) {
			StringTokenizer tokens = new StringTokenizer(reader.readLine());
			long X = Long.parseLong(tokens.nextToken());
			int K = Integer.parseInt(tokens.nextToken());
			
			System.out.println(pn.solve(X, K));
		}
		
		reader.close();
	}
}