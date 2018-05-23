package practice.computation.dp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

// N*log(N)
public class LongestIncreasingSubsequence {
	
	class LISDetail {
		int lastnumber;
		int count;
		
		public LISDetail(int lastnumber, int count) {
			this.lastnumber = lastnumber;
			this.count = count;
		}
	}
	
	List<Integer> numbers = new ArrayList<Integer>();
	
	void add(int data) {
		numbers.add(data);
	}
	
	int lis() {
		int max = Integer.MIN_VALUE; // current LIS MAX
		// tracks last numbers to add number @ suitable active list
		TreeSet<LISDetail> lastnumbers = new TreeSet<LISDetail>(new Comparator<LISDetail>() {
			// comparator to add detail into BST
			@Override
			public int compare(LISDetail first, LISDetail second) {
				return new Integer(first.lastnumber).compareTo(new Integer(second.lastnumber));
			}
		});
		
		for(int number : numbers) {
			// dummy count (-1)
			LISDetail floor = lastnumbers.floor(new LISDetail(number, -1));
			int newcount;
			if(floor != null) {
				// found, copy & append 2 suitable active list
				newcount = floor.count+1; // new count
			} else {
				// not found, create new active list
				newcount = 1;
			}
			
			if(max < newcount) {
				// new max
				max = newcount;
			}
			lastnumbers.add(new LISDetail(number, newcount));
			// remove with duplicate count
			LISDetail dummy = new LISDetail(number+1, -1);
			LISDetail ceil = lastnumbers.ceiling(dummy);
			if(ceil != null && ceil.count == newcount) {
				// remove duplicate counts
				lastnumbers.remove(ceil);
			}
		}
		
		return max;
	}
	
	public static void main(String[] args) {
		
		LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
		lis.add(0);
		lis.add(8);
		lis.add(4);
		lis.add(12);
		lis.add(2);
		lis.add(10);
		lis.add(6);
		lis.add(14);
		lis.add(1);
		lis.add(9);
		lis.add(5);
		lis.add(13);
		lis.add(3);
		lis.add(11);
		lis.add(7);
		lis.add(15);
		System.out.println(lis.lis());
	}

}