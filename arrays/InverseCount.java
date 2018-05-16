package practice.computation.array;

public class InverseCount {
	
	int data[];
	
	public InverseCount(int data[]) {
		this.data = data;
	}
	
	// start end inclusive
	int count(int start, int end) {
		if(start == end) {
			//only one element
			return 0;
		}
		int mid = start + (end-start)/2;
		int l = count(start, mid);
		int r = count(mid+1, end);
		
		// merge count
		int merge = merge(start, mid, mid+1, end);
		return l+r+merge;
	}
	
	// start end inclusive
	int merge(int start1, int end1, int start2, int end2) {
		int newdata[] = new int[end2-start1+1];
		
		int i = start1;
		int j = start2;
		int k = 0;
		int ic = 0;
		while(i<=end1 && j<=end2) {
			if(data[i]<data[j]) {
				newdata[k++] = data[i];
				i++;
			} else {
				newdata[k++] = data[j];
				// inverse count
				ic++;
				j++;
			}
		}
		
		if(i<=end1) {
			// all count will be inverse-count
			ic += end1-i;
			// remaining first-set
			while(i<=end1) {
				newdata[k++] = data[i];
				i++;
			}
		}
		
		// remaining second-set
		while(j<=end2) {
			newdata[k++] = data[j];
			j++;
		}
		
		// copy to original array
		k = start1;
		for(int nd : newdata) {
			data[k++] = nd;
		}
		
		return ic;
	}
	
	void print() {
		for(int d : data) {
			System.out.print(d + " ");
		}
	}
	
	public static void main(String[] args) {
		int data[] = new int[]{2, 4, 1, 3, 5};
		InverseCount ic = new InverseCount(data);
		System.out.println("Inverse Count: " + ic.count(0, data.length-1)); // count
		System.out.println("Sorted-Data");
		ic.print();
	}

}