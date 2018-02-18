package hackerearth.hiring.challenges.date16022018.ee;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Range implements Comparable<Range> {
	int first;
	int second;
	int firstIndex;
	int secondIndex;
	int l = 0;
	
	Range(int first, int firstIndex) {
		this.first = first;
		this.second = first;
		this.firstIndex = firstIndex;
		this.secondIndex = firstIndex;
		l = 1;
	}
	
	void add(int second, int secondIndex) {
		this.second = second;
		this.secondIndex = secondIndex;
		l++;
	}
	
	@Override
	public int compareTo(Range r) {
		return new Integer(this.second).compareTo(r.second);
	}
}

public class IncreasingSubsequence {
	
	int find(int a[], int k) {
		
		TreeSet<Range> previous = new TreeSet<Range>();
		previous.add(new Range(a[0], 0));
		int l = a.length;
		for(int i = 1; i < l; i++) {
			int n = a[i];
			Set<Range> sublist = previous.subSet(new Range(Integer.MIN_VALUE, -1), false, new Range(n, -1), true);
			if(!sublist.isEmpty()) {
				// exists
				int maxl = 0;
				Range selected = null;
				for(Range r : sublist) {
					if(r.l > maxl) {
						maxl = r.l;
						selected = r;
					}
				}
				// reinsert with updated
				previous.remove(selected);
				selected.add(n, i);
				previous.add(selected);
			} else {
				// create new
				previous.add(new Range(n, i));
			}
		}
		
		// find max
		int max = -1;
		for(Range r : previous) {
			if(r.l >= k) {
				int d = a[r.secondIndex] - a[r.firstIndex];
				if(d > max) {
					max = d;
				}
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		
		IncreasingSubsequence is = new IncreasingSubsequence();
		
		/*int a[] = new int[]{1, 2, 3, 4};
		System.out.println(is.find(a, 2));*/
		
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int k = s.nextInt();
		int a[] = new int[n];
		for(int i = 0; i < n; i++) {
			a[i] = s.nextInt();
		}
		System.out.println(is.find(a, k));
		
		s.close();
	}

}