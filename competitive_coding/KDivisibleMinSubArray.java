package hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class KDivisibleMinSubArray {
	
	// solution
	static int solve(Map<Integer, List<Integer>> modpos) {
		int min = Integer.MAX_VALUE;
		for(int mod : modpos.keySet()) {
			// take each consecutive minimum difference
			List<Integer> pos = modpos.get(mod);
			int l = pos.size();
			for(int i = 0; i < l - 1; i++) {
				int d = pos.get(i + 1) - pos.get(i);
				if(d < min) {
					// new min
					min = d;
				}
			}
		}
		
		if(min == Integer.MAX_VALUE) {
			// still not found
			min = -1;
		}
		return min;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int t = Integer.parseInt(reader.readLine());
    	while(--t >= 0) {
    		StringTokenizer tokens = new StringTokenizer(reader.readLine());
    		int n = Integer.parseInt(tokens.nextToken());
    		int k = Integer.parseInt(tokens.nextToken());
    		
    		Map<Integer, List<Integer>> modpos = new HashMap<>(); // positional index
    		// base cases
    		modpos.put(0, new ArrayList<>(2));
    		modpos.get(0).add(0);
    		int prevmod = 0;
    		
    		tokens = new StringTokenizer(reader.readLine());
    		for(int i = 0; i < n; i++) {
    			int a = Integer.parseInt(tokens.nextToken());
    			int mod = (prevmod + a) % k;
    			List<Integer> pos = modpos.get(mod);
    			if(pos == null) {
    				pos = new ArrayList<>(2);
    				modpos.put(mod, pos);
    			}
    			pos.add(i + 1);
    			
    			// previous mod track
    			prevmod = mod;
    		}
    		// solve
    		System.out.println(solve(modpos));
    	}
	}
}