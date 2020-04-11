package hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class MagicalWord {

	String input;
	int n;
	static TreeSet<Integer> primes = new TreeSet<>();
	
	public MagicalWord(String input, int n) {
		this.input = input;
		this.n = n;
	}
	
	// pre process primes
	static void pre() {
		int limit = 256;
		boolean primesf[] = new boolean[limit + 1];
		
		// initially all are primes
		for(int i = 1; i <= limit; i++) {
			primesf[i] = true;
		}
		
		for(int p = 2; p * p <= limit; p++) {
			if(primesf[p]) {
				// already marked as primes
				// mark all multiples as non prime (starts with p^2)
				// note: p^2 already processed
				for(int i = p  * p; i <= limit; i += p) {
					primesf[i] = false;
				}
			}
		}
		
		// saves primes
		for(int i = 1; i <= limit; i++) {
			if(primesf[i]) {
				// avoids non-alphabetic letter
				if(Character.isAlphabetic((char)i)) {
					primes.add(i);
				}
			}
		}
		
		// System.out.println(primes);
	}
	
	// magical word solve
	String solve() {
		StringBuilder magical = new StringBuilder();
		for(int i = 0; i < n; i++) {
			int ch = input.charAt(i);
			
			// closest primes
			Integer left = primes.floor(ch);
			Integer right = primes.ceiling(ch);
			
			if(left == null) {
				left = right;
			}
			if(right == null) {
				right = left;
			}
			
			if(left == right) {
				// number itself
				magical.append((char)left.intValue());
			} else {
				// closest distance
				int d1 = ch - left;
				int d2 = right - ch;
				if(d1 <= d2) {
					magical.append((char)left.intValue());
				} else {
					magical.append((char)right.intValue());
				}
			}
		}
		
		return magical.toString();
	}
	
	public static void main(String[] args) throws Exception {
		
		//String file = "/home/hirak/debasis_da/data/5b7cfb80-b-in00.txt";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader reader = new BufferedReader(new FileReader(file));
		
		// pre process primes
		pre();
		
		int t = Integer.parseInt(reader.readLine());
		while(--t >= 0) {
			// for each query
			int n = Integer.parseInt(reader.readLine());
			String input = reader.readLine();
			MagicalWord mw = new MagicalWord(input, n);
			
			// solve
			System.out.println(mw.solve());
		}
		
		reader.close();
	}
}