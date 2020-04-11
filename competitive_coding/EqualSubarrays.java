package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;
 
public class EqualSubarrays {
	
	class Data {
		long data;
		int index;
		
		public Data(long data, int index) {
			this.data = data;
			this.index = index;
		}
	}
	
	long prefixsum[];
	long a[];
	PriorityQueue<Data> maxpq = new PriorityQueue<>(new Comparator<Data>() {
		// reverse order
		@Override
		public int compare(Data d1, Data d2) {
			return Long.valueOf(d2.data).compareTo(d1.data);
		}
	});
	TreeSet<Integer> index = new TreeSet<>();
	
	public EqualSubarrays(int n) {
		prefixsum = new long[n + 1];
		prefixsum[0] = 0;
		a = new long[n];
		
		index.add(-1);
		index.add(n);
	}
	
	void add(long v, int idx) {
		a[idx] = v;
		prefixsum[idx + 1] = prefixsum[idx] + v;
		maxpq.add(new Data(v, idx));
	}
	
	// start, end inclusive index find a suitable index on left side
	// which meets required criteria
	int findleft(int start, int end, long k, long max) {
		// binary search
		int last = end;
		while(start < end) {
			int mid = (start + end) / 2;
			long sum = prefixsum[last + 1] - prefixsum[mid];
			int d = last - mid + 1;
			long t = max * d; // equals
			long req = t - sum; // required sum
			if(k == req) {
				// optimal index
				return mid;
			} else if(k > req) {
				// move to left
				end = mid - 1;
			} else {
				// move to right
				start = mid + 1;
			}
		}
		
		if(max * (last - start + 1) - (prefixsum[last + 1] - prefixsum[start]) <= k) {
			return start;
		} else {
			return start + 1;
		}
	}
	
	// start, end inclusive index find a suitable index on left side
	// which meets required criteria
	int findright(int start, int end, long k, long max) {
		// binary search
		int first = start;
		while(start < end) {
			int mid = (start + end) / 2;
			long sum = prefixsum[mid + 1] - prefixsum[first];
			int d = mid - first + 1;
			long t = max * d; // equals
			long req = t - sum; // required sum
			if(k == req) {
				// optimal index
				return mid;
			} else if(k > req) {
				// move to right
				start = mid + 1;
			} else {
				// move to left
				end = mid - 1;
			}
		}
		
		if(max * (start - first + 1) - (prefixsum[start + 1] - prefixsum[first]) <= k) {
			return start;
		} else {
			return start - 1;
		}
	}
	
	// maximum equal subarrays
	int max(long k) {
		int max = Integer.MIN_VALUE;
		while(!maxpq.isEmpty()) {
			Data d = maxpq.poll();
			long mx = d.data;
			int idx=  d.index;
			
			// index positions for selected maximum
			// now we can consider this subarray to find optimal solution
			int left = index.floor(idx) + 1;
			int right = index.ceiling(idx) - 1;
			
			int end1 = idx;
			int start1 = findleft(left, end1, k, mx);
			// for each position on left side find optional index on right side
			for(int i = start1; i <= end1; i++) {
				if(right - i + 1 > max) {
					// if available position is greater than found max
					long t = (end1 - i + 1) * mx - (prefixsum[end1 + 1] - prefixsum[i]);
					long rem = k - t;
					
					int start2 = idx + 1;
					int end2 = right;
					int rightidx = findright(start2, end2, rem, mx);
					
					int nd = rightidx - i + 1;
					if(nd > max) {
						// new max
						max = nd;
					}
				} else {
					// no more computation required
					break;
				}
			}
			
			// add max index
			index.add(idx);
		}
		
		return max;
	}
	
	public static void main(String args[]) throws Exception {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(reader.readLine());
		long k = Long.parseLong(reader.readLine());
		
		EqualSubarrays p = new EqualSubarrays(n);
		StringTokenizer tokens = new StringTokenizer(reader.readLine());
		for(int i = 0; i < n; i++) {
			long a = Long.parseLong(tokens.nextToken());
			p.add(a, i);
		}
		
		// result
		System.out.println(p.max(k));
		
		reader.close();
	}
}