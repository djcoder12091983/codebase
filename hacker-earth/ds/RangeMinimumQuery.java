package hackerearth.practice.ds;

import java.util.Arrays;
import java.util.Scanner;

class SegmentNode {
	int value;
	int start, end;
	
	SegmentNode left, right;
	
	public SegmentNode(int value) {
		this.value = value;
	}
}

public class RangeMinimumQuery {
	
	SegmentNode root = null;
	
	// build segment tree
	// start/end inclusive
	SegmentNode build(int data[], int start, int end) {
		int l = data.length;
		if(l == 1) {
			// leaf node
			SegmentNode node = new SegmentNode(data[0]);
			node.start = start;
			node.end = end;
			return node;
		} else {
			// will have left and right
			int mid = l/2;
			SegmentNode left = build(Arrays.copyOfRange(data, 0, mid), start, start + mid-1); // left node build
			SegmentNode right = build(Arrays.copyOfRange(data, mid, l), start + mid, start + l-1); // right node build
			SegmentNode node = new SegmentNode(left.value < right.value ? left.value : right.value); // final node
			node.start = start;
			node.end = end;
			node.left = left;
			node.right = right;
			return node;
		}
	}
	
	// init root node
	void init(int data[]) {
		root = build(data, 0, data.length-1);
	}
	
	int min(SegmentNode node, int start, int end) {
		int s = node.start;
		int e = node.end;
		if(s >= start && e <= end) {
			// within range
			return node.value; // no need to recursion
		} else if(end<s || start>e) {
			// outside the range
			return Integer.MAX_VALUE; // infinity
		} else {
			// partially range
			if(node.left != null && node.right != null) {
				// non-leaf node
				int lv = min(node.left, start, end); // left recursion
				int rv = min(node.right, start, end); // right recursion
				return lv < rv ? lv : rv;
			} else {
				return node.value;
			}
		}
	}
	
	int min(int start, int end) {
		return min(root, start, end);
	}
	
	int update(SegmentNode node, int position, int newValue) {
		int start = node.start;
		int end = node.end;
		if(position >= start && position <= end) {
			// valid range
			if(start == end) {
				// leaf node
				node.value = newValue; // update new value
				return node.value;
			} else {
				// recursion
				int lv = update(node.left, position, newValue);
				int rv = update(node.right, position, newValue);
				int newMinValue = lv < rv ? lv : rv;
				node.value = newMinValue; // update new min-value
				return node.value;
			}
		} else {
			// invalid range
			// no need to update minimum value
			return node.value;
		}
	}
	
	void update(int position, int newValue) {
		update(root, position, newValue); // update with new value
	}
	
	public static void main(String[] args) {
		
		RangeMinimumQuery rq = new RangeMinimumQuery();
		
		//Scanner
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int q = s.nextInt();
        int data[] = new int[n];
        for(int i=0; i<n; i++) {
        	data[i] = s.nextInt();
        }
        rq.init(data); // build RMQ
        // query and update
        for(int i=0; i<q; i++) {
        	char type = s.next().charAt(0);
        	if(type == 'q') {
        		// query
        		int start = s.nextInt();
        		int end = s.nextInt();
        		System.out.println(rq.min(start-1, end-1));
        	} else {
        		// update
        		int p = s.nextInt();
        		int v = s.nextInt();
        		rq.update(p-1, v);
        	}
        }
        
        s.close();
	}

}