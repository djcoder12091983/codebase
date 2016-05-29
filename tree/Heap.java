// heap DS
public class Heap {

	// TODO strategy to be decided
	final static int RESIZE_FACTOR = 10;

	int data[];
	int heap[];
	int size = 0;
	
	// max/min heap indicator
	boolean maxHeap = false;
	
	Heap(int data[], boolean maxHeap) {
		this.data = data;
		
		// copy the data
		size = this.data.length;
		this.heap = new int[size];
		for(int i = 0; i < size; i++) {
			heap[i] = data[i];
		}
		
		this.maxHeap = maxHeap;
	}
	
	// assumed below nodes are heapified
	void heapify(int nodeIdx) {
		
		int nextNode2Heapify = nextNodeToHeapify(nodeIdx);
		
		if(nextNode2Heapify != nodeIdx) {
			// needs to heapify
			// exchange the values
			int t = heap[nextNode2Heapify];
			heap[nextNode2Heapify] = heap[nodeIdx];
			heap[nodeIdx] = t;
			
			// next heapify
			heapify(nextNode2Heapify);
		}
	}
	
	// construction of heap
	void buildHeap() {
		
		// at most numbers of leaves will be n/2
		for(int i = size / 2; i >= 0; i--) {
			// heapify all nodes from leaves
			// bottom-top approach
			heapify(i);
		}
	}
	
	int size() {
		return heap.length;
	}
	
	// peak element (min/max)
	int peak() {
		
		if(size == 0) {
			throw new IllegalStateException("No element exists");
		}
		
		return heap[0];
	}
	
	// extract min/max
	int extract() {
		
		if(size == 0) {
			throw new IllegalStateException("No element exists");
		}
		
		// extract
		int peak = heap[0];
		// remove (logically)
		size--;
		heap[0] = heap[size];
		heap[size] = maxHeap ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		// heapify the root node
		heapify(0);
		
		// System.out.println("Size: " + size);
		
		// TODO
		// remove space and reallocation of array strategy to be decided
		
		return peak;
	}
	
	// update an existing index
	void update(int position, int newValue) {
	
		if(position < size) {
		
			int oldValue = heap[position];
			heap[position] = newValue;
			
			if(maxHeap) {
				// max heap
				if(newValue > oldValue) {
					// bottom to top traversal
					int idx = position;
					int parentNodeIdx = (idx + 1) / 2;
					while(parentNodeIdx >= 0) {
					
						// exchange if parent < child
						if(heap[parentNodeIdx] < heap[idx]) {
							int t = heap[parentNodeIdx];
							heap[parentNodeIdx] = heap[idx];
							heap[idx] = t;
						} else {
							// suitable position
							break;
						}
						
						idx = parentNodeIdx;
						// next parent node index
						parentNodeIdx = (idx + 1) / 2;
					}
				} else if(newValue < oldValue) {
					// heapify (top to bottom traversal)
					heapify(position);
				}
			} else {
				// min heap
				if(newValue < oldValue) {
					// bottom to top traversal
					// System.out.println("-------------------------------------");
					int idx = position;
					int parentNodeIdx = (idx + 1) / 2;
					while(parentNodeIdx >= 0) {
					
						// System.out.println("parentNodeIdx: " + heap[parentNodeIdx] + ", idx: " + heap[idx]);
						
						// exchange if parent > child
						if(heap[parentNodeIdx] > heap[idx]) {
							int t = heap[parentNodeIdx];
							heap[parentNodeIdx] = heap[idx];
							heap[idx] = t;
						} else {
							// suitable position
							break;
						}
						
						idx = parentNodeIdx;
						// next parent node index
						parentNodeIdx = (idx + 1) / 2;
					}
				} else if(newValue > oldValue) {
					// heapify (top to bottom traversal)
					heapify(position);
				}
			}
		} else {
			throw new IllegalStateException("Invalid position, should be less than " + size);
		}
	}
	
	// adds a node
	void add(int newValue) {
		
		int l = heap.length;
		if(l == size) {
			// heap is full
			// reallocate the array
			// resize factor is some cnstant
			int newSize = size + RESIZE_FACTOR;
			int newHeap[] = new int[newSize];
			for(int i = 0; i < size; i++) {
				newHeap[i] = heap[i];
			}
			for(int i = size; i < newSize; i++) {
				newHeap[i] = maxHeap ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			}
			
			// reallocate
			heap = newHeap;
		}
		
		size++;
		// update the value
		update(size - 1, newValue);
	}
	
	int nextNodeToHeapify(int nodeIdx) {
	
		int leftNodeIdx = (2 * nodeIdx) + 1;
		int rightNodeIdx = (2 * nodeIdx) + 2;
		
		boolean leftNodeExists = leftNodeIdx < size && heap[leftNodeIdx] != Integer.MIN_VALUE;
		boolean rightNodeExists = rightNodeIdx < size && heap[rightNodeIdx] != Integer.MIN_VALUE;
	
		int newRootIdx = nodeIdx;
		// int l = heap.length;
		if(maxHeap) {
			// max heap
			if(leftNodeExists && heap[leftNodeIdx] > heap[newRootIdx]) {
				newRootIdx = leftNodeIdx;
			}
			
			if(rightNodeExists && heap[rightNodeIdx] > heap[newRootIdx]) {
				newRootIdx = rightNodeIdx;
			}
			
			return newRootIdx;
		} else {
			// min heap
			if(leftNodeExists && heap[leftNodeIdx] < heap[newRootIdx]) {
				newRootIdx = leftNodeIdx;
			}
			
			if(rightNodeExists && heap[rightNodeIdx] < heap[newRootIdx]) {
				newRootIdx = rightNodeIdx;
			}
			
			return newRootIdx;
		}
	}
	
	// test
	public static void main(String a[]) {
	
		int input[] = {12, 3 , 4, 5, 10, 11, 23, 47, 7, 56, 55, 34, 19, 21, 37, 29, 39, 41,45};
		int newInput[] = {100, 17, 15, 13, 110, 123, 33, 66, 69, 71, 51, 53};
		
		Heap maxHeap = new Heap(input, true);
		maxHeap.buildHeap();
		
		// test
		// extraction
		for(int i = 0; i < 5; i++) {
			int peak = maxHeap.extract();
			System.out.println("Max Peak: " + peak);
		}
		// add
		int l = newInput.length;
		for(int i = 0; i < l; i++) {
			maxHeap.add(newInput[i]);
		}
		// again extraction
		for(int i = 0; i < 5; i++) {
			int peak = maxHeap.extract();
			System.out.println("Max Peak: " + peak);
		}
		
		Heap minHeap = new Heap(input, false);
		minHeap.buildHeap();
		
		// test
		// extraction
		for(int i = 0; i < 5; i++) {
			int peak = minHeap.extract();
			System.out.println("Min Peak: " + peak);
		}
		// add
		for(int i = 0; i < l; i++) {
			minHeap.add(newInput[i]);
		}
		// again extraction
		for(int i = 0; i < 5; i++) {
			int peak = minHeap.extract();
			System.out.println("Min Peak: " + peak);
		}
	}
}