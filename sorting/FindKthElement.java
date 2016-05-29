// finds kth minimum element without sorting
// it can be generic minimum/maximum based on a given comparison logic
public class FindKthElement {

	int[] input;

	FindKthElement(int[] input) {
		// copy the elements
		int n = input.length;
		this.input = new int[n];
		for(int i = 0; i < n; i++) {
			this.input[i] = input[i];
		}
	}

	int find(int k) {
	
		// index starts from 0
		k = k -1;
		
		int n = input.length;
		if(k >= n) {
			throw new IllegalStateException("Invalid k value");
		}
		
		if(n == 1) {
			return input[0];
		}
		
		// gets minimum and max value
		int min = input[0];
		int max = input[1];
		
		for(int i = 1; i < n; i++) {
			if(min > input[i]) {
				min = input[i];
			}
			
			if(max < input[i]) {
				max = input[i];
			}
		}
		
		int mid = min + ((max - min) / 2);
		
		int c = 0;
		
		// kth element
		int kth = -1;
		
		int start = 0;
		int end = n - 1;
		// quick selection of start->end and find two halfs of 'mid'
		while(start < end) {
		
			int i = start;
			int j = end;
			
			// System.out.println("mid: " + mid);
			
			while(i < j) {
			
				// it can be generic minimum/maximum based on a given comparison logic
				while(input[i] <= mid) {
					
					i++;
					
					if(i >= j) {
						break;
					}
				}
				
				if(i == end) {
					i = end -1;
					j = i;
					break;
				}
				
				// it can be generic minimum/maximum based on a given comparison logic
				while(input[j] > mid) {
					
					j--;
					
					if(j <= i) {
						break;
					}
				}
				
				/*if( j == start) {
					i = sart + 1;
					j = i;
					break;
				}*/
				
				// System.out.println(i + " " + j);
				
				if(i <= j) {
					// exchange
					int t = input[i];
					input[i] = input[j];
					input[j] = t;
				}
			}
			
			// pivot point i/j
			int pivot = i;
			
			/*System.out.println("Pivot: " + pivot + "\n");
			
			for(i = 0; i < n; i++) {
				System.out.print(input[i] + " ");
			}*/
			
			if(pivot > k) {
				// kthh element exist in first/left half
				end = pivot;
			} else if(pivot < k) {
				// kthh element exist in second/right half
				start = pivot;
			} else {
				// pivot == k
				// kth element found
				kth = pivot;
				break;
			}
			
			// safe condition
			/*if(c++ < n) {
				System.out.println("\nStart: " + start + ", End: " + end);
			} else {
				break;
			}*/
			
			// gets expected mid
			// gets minimum and max value
			min = input[start];
			max = input[start];
			
			for(i = start + 1; i <= end; i++) {
				if(min > input[i]) {
					min = input[i];
				}
				
				if(max < input[i]) {
					max = input[i];
				}
			}
			
			// System.out.println("min: " + min + ", max: " + max);
			
			mid = min + ((max - min) / 2);
		}
		
		// returns input[start/end]
		return input[kth];
	}
	
	public static void main(String a[]) {
	
		int[] input = {12, 34, 11, 100, 7, 6, 3, 2, 110, 111, 113, 45, 9, 99, 89, 44, 77, 123};
		int[] ks = {2, 3, 5, 4, 7, 10, 9, 8};
		
		FindKthElement f = new FindKthElement(input);
		
		// System.out.println("5th element: " + f.find(5));
		
		for(int i = 0; i < ks.length; i++) {
			System.out.println(ks[i] + "-th element: " + f.find(ks[i]));
		}
	}
}