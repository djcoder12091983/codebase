import java.util.*;

class Range implements Comparable<Range> {
	int start; // inclusive
	int end; // inclusive
	
	// both same
	Range(int start) {
		this.start = start;
		this.end = start;
	}
	
	Range(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public int compareTo(Range r) {
		//System.out.println("yahoo!!");
		int s = r.start;
		int e = r.end;
		
		//System.out.println(s + " " + e + " " + start + " " + end);
		
		if(end < s) {
			return -1; // before
		} else if((start >= s && start <= e) || (end >= s && end <= e)) {
			return 0; // in-b2n range
		} else {
			// s > end
			return 1; // after
		}
	}
	
	// not required by tree-map, instead compare method required
	@Override
	public boolean equals(Object r) {
		if(r == null) {
			return false;
		} else if(r == this) {
			return true;
		} else if(r instanceof Range) {
			Range range = (Range)r;
			int s = range.start;
			int e = range.end;
			//System.out.println(s + " " + e + " " + start + " " + end);
			// overlaps
			return (s >= start && s <= end) || (e >= start && e <= end);
		} else {
			return false;
		}
	}
}

class RangeValue extends Range {
	
	boolean value;
	
	RangeValue(int start, int end, boolean value) {
		super(start, end);
		this.value = value;
	}
	
	RangeValue(int start, boolean value) {
		super(start);
		this.value = value;
	}
}

// tower research c++ hiring challenge
// using bit vector (can't be done, because of no sizeof), another approach cab ne range details
public class BitVectorUpdateAndQuery {
	
	Map<Range, RangeValue> rangeTree = new TreeMap<Range, RangeValue>(); // range tree
	
	public BitVectorUpdateAndQuery(int size) {
		// initial entry
		rangeTree.put(new Range(0, size - 1), new RangeValue(0, size -1, false));
	}
	
	// update by 1
	void update(int index) {
		Range r = new Range(index); // start and end same
		RangeValue found = rangeTree.get(r); // assumed found
		int s = found.start;
		int e = found.end;
		// remove original and split/merge
		rangeTree.remove(r);
		// split
		if(index > s && index < e) {
			// 3 parts
			rangeTree.put(new Range(s, index - 1), new RangeValue(s, index - 1, false));
			rangeTree.put(r, new RangeValue(index, true)); // set to 1
			rangeTree.put(new Range(index + 1, e), new RangeValue(index + 1, e, false));
		} else if(index == s) {
			// merge left side if contains 1
			r = new Range(index - 1);
			RangeValue left = rangeTree.get(r);
			if(left != null && left.value) {
				// merge
				rangeTree.remove(r);
				rangeTree.put(new Range(left.start, left.end + 1), new RangeValue(left.start, left.end + 1, true));
			} else {
				rangeTree.put(r, new RangeValue(index, true)); // set to 1
			}
			rangeTree.put(new Range(index + 1, e), new RangeValue(index + 1, e, false));
		} else if(index == e) {
			rangeTree.put(new Range(s, index - 1), new RangeValue(s, index - 1, false));
			// merge right side if contains 1
			r = new Range(index + 1);
			RangeValue right = rangeTree.get(r);
			if(right != null && right.value) {
				// merge
				rangeTree.remove(r);
				rangeTree.put(new Range(right.start - 1, right.end), new RangeValue(right.start - 1, right.end, true));
			} else {
				rangeTree.put(r, new RangeValue(index, true)); // set to 1
			}
		}
	}
	
	boolean query(int index) {
		Range r = new Range(index); // start and end same
		RangeValue found = rangeTree.get(r); // assumed found
		return found.value;
	}
	
	public static void main(String args[]) {
		
		BitVectorUpdateAndQuery bv = new BitVectorUpdateAndQuery(1000);
		
		bv.update(500);
		
		System.out.println(bv.query(499));
		System.out.println(bv.query(500));
		System.out.println(bv.query(501));
	}
}
