package hackerearth.practice.ds;

import java.util.ArrayList;
import java.util.List;

public class ZeroSubsetCount {
	
	List<Integer> data = new ArrayList<Integer>();
	int min = Integer.MAX_VALUE;
	int max = Integer.MIN_VALUE;
	
	void add(int d) {
		data.add(d);
		if(min > d) {
			// new min
			min = d;
		}
		if(max < d) {
			// new max
			max = d;
		}
	}
	
	int count() {
		if(min > 0) {
			// greater than 0 then no nonempty-subset leads to 0
			return 0;
		}
		// DP table, DP(i,j) => upto ith element leads to j sum count
		// for details see sum-subset problem
		// here table size minimum to 0
		int s = data.size();
		// min is negative
		int column = (max-min)+2;
		int count[][] = new int[s+1][column];
		for(int i=0; i<s+1; i++) {
			count[i][0] = 1;
		}
		for(int j=1; j<column; j++) {
			count[0][j] = 0;
		}
		for(int i=1; i<s+1; i++) {
			for(int j=1; j<column; j++) {
				int c = count[i-1][j];
				boolean flag = false;
				if(c > 0) {
					// count available, copy it
					// element not included
					count[i][j] += c;
					flag = true;
				}
				int d = data.get(i-1)+min*(-1)+1;
				int index = j-d;
				if(index >= 0) {
					// valid index
					c = count[i-1][index]; // because of negative number
					if(c > 0) {
						// count available, copy it
						// element included
						count[i][j] += c;
						flag = true;
					}
				}
				if(!flag) {
					// count not available
					count[i][j] = 0;
				}
			}
		}
		
		return count[s][column-1];
	}
	
	public static void main(String[] args) {
		
		ZeroSubsetCount zc = new ZeroSubsetCount();
		zc.add(-1);
		zc.add(-2);
		zc.add(3);
		zc.add(4);
		zc.add(-3);
		zc.add(-4);
		zc.add(-2);
		System.out.println(zc.count());
	}

}