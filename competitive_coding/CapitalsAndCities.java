package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
 
public class CapitalsAndCities {
	
	static class Data {
		int position;
		long data;
		
		public Data(long data, int position) {
			this.data = data;
			this.position = position;
		}
	}
	
	static long[] minimize(Data[] positions, long k) {
		int l = positions.length;
		if(l == 1) {
			return new long[]{1, 0};
		}
		// sort
		Arrays.sort(positions, new Comparator<Data>() {
			@Override
			public int compare(Data first, Data second) {
				return Long.valueOf(first.data).compareTo(Long.valueOf(second.data));
			}
		});
		
		// cumulative sum
		long[] sums = new long[l];
		sums[0] = positions[0].data;
		for(int i = 1; i < l; i++) {
			sums[i] = sums[i - 1] + positions[i].data; 
		}
		
		// finds minimum differences which is closed to K
		long requiredsum = Long.MAX_VALUE;
		int requiredindex = Integer.MAX_VALUE;
		for(int i = 0; i < l; i++) {
			int t = i - 0;
			long d = positions[i].data;
			long lsteps = t * d - (i > 0 ? sums[i - 1] : 0);
			t = l - 1 - i;
			long rsteps = sums[l - 1] - sums[i] - t * d;
			long tsteps = lsteps + rsteps;
			long trequired;
			if(tsteps >= k) {
				// k is minimum or same to fill all steps
				trequired = tsteps - k;
			} else {
				// k is overflowed
				// now if difference is even then minimum required sum is zero otherwise 1
				trequired = k - tsteps;
				if((trequired & 1L) == 0) {
					// even
					trequired = 0;
				} else {
					// odd
					trequired = 1;
				}
			}
			int position = positions[i].position + 1;
			if(trequired == requiredsum && position < requiredindex) {
				// same difference then take smallest index
				requiredindex = position;
			} else if(trequired < requiredsum) {
				// new minimum
				requiredindex = position;
				requiredsum = trequired;
			}
		}
		
		// result
		return new long[]{requiredindex, requiredsum};
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = reader.readLine();
		StringTokenizer tokens = new StringTokenizer(line);
		int n = Integer.parseInt(tokens.nextToken());
		long k = Long.parseLong(tokens.nextToken());
		// data
		line = reader.readLine();
		tokens = new StringTokenizer(line);
		Data[] positions = new Data[n];
		for(int i = 0 ;i < n; i++) {
			positions[i] = new Data(Long.parseLong(tokens.nextToken()), i);
		}
		
		// result
		long result[] = minimize(positions, k);
		System.out.println(result[0] + " " + result[1]);
		
		reader.close();
	}
}