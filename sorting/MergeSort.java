// merge sort without recursive call, instead interative call
// ascneding order
// this method could be generic using a comprator which decides the comparison logic
class MergeSort {

	// TODO generic data type to be used
	int[] input;
	
	MergeSort(int[] input) {
		this.input = input;
	}

	int[] sort() {
		
		int n = input.length;
		int sortedArray[] = new int[n];
		
		// copy the array
		// TODO use system library to copy
		for(int i =  0; i < n; i++) {
			sortedArray[i] = input[i];
		}
		
		if(n <= 1) {
			return sortedArray;
		}
		
		int partitionSize = 1;
		while(partitionSize < n) {
			
			levelSort(partitionSize, sortedArray);
			
			// each time size multipled 2
			// this creates log base 2 (n) height tree
			partitionSize *= 2;
		}
		
		return sortedArray;
	}
	
	// level sort
	void levelSort(int pSize, int[] sortedArray) {
	
		// System.out.println("pSize: " + pSize);
	
		int n = input.length;
	
		for(int i = 0; i < n; i += pSize * 2) {
			merge(sortedArray, i, i + pSize, i + pSize, i + (pSize * 2));
		}
	}
	
	// merge and modified the range arry with sorted values
	void merge(int[] sortedArray, int lStart, int lEnd, int rStart, int rEnd) {
	
		// System.out.println("lStart: " + lStart + ", lEnd: " + lEnd + ", rStart: " + rStart + ", rEnd: " + rEnd);
		
		int n = input.length;
		
		if(lEnd >= n) {
			// lEnd = n - 1;
			// do nothing
			return;
		}
		
		if(rStart >= n) {
			// rStart = n  - 1;
			// do nothing
			return;
		}
		
		if(rEnd > n) {
			rEnd = n  - 1;
		}
		
		int i = lStart;
		int j = rStart;
		
		int k = 0;
		int modifiedArray[] = new int[rEnd - lStart];
		
		// System.out.println("range: " + (rEnd - lStart));
		
		// merge
		while(i < lEnd && j < rEnd) {
			// could be generic using a comprator which decides the comparison logic
			// scending order
			if(sortedArray[i] <= sortedArray[j]) {
				modifiedArray[k++] = sortedArray[i];
				i++;
			} else {
				modifiedArray[k++] = sortedArray[j];
				j++;
			}
		}
		
		// copy extra values
		while(i < lEnd) {
			modifiedArray[k++] = sortedArray[i];
			i++;
		}
		
		while(j < rEnd) {
			modifiedArray[k++] = sortedArray[j];
			j++;
		}
		
		// copy modified one to original array
		k = 0;
		for(i = lStart; i < rEnd; i++) {
			sortedArray[i] = modifiedArray[k++];
		}
		
		/*System.out.print("Modified array: ");
		for(i = lStart; i < rEnd; i++) {
			System.out.print(sortedArray[i] + " ");
		}
		System.out.print("\n");*/
	}
	
	// test
	public static void main(String a[]) {
	
		int data[] = {1, 10, 7, 8, 9, 11, 13, 100, 23, 46, 89, 111};
		
		System.out.println("--------------Original data-----------------------");
		for(int i = 0; i < data.length; i++) {
			System.out.print(data[i] + " ");
		}
		
		System.out.println("\n\n");
		
		// sort
		int[] sortedArray = new MergeSort(data).sort();
		
		System.out.println("\n\n--------------Sorted data-----------------------");
		for(int i = 0; i < sortedArray.length; i++) {
			System.out.print(sortedArray[i] + " ");
		}
	}
}