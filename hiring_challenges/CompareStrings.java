package hackerearth.hiring.challenges.date06052018.tw;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

class Range implements Comparable<Range>{
	int start;
	int end;
	
	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int compareTo(Range second) {
		if((second.start >= start && second.start <= end) || (second.end >= start && second.end <= end)) {
			// with-in the range, it equals
			return 0;
		}
		// otherwise normal logic
		return new Integer(start).compareTo(new Integer(second.start));
	}
}

public class CompareStrings {
	
	boolean first[]; //A
	boolean second[]; //B
	
	Map<Range, Range> firstOneRanges = new TreeMap<Range, Range>(); // 1's ranges of first
	Map<Range, Range> secondOneRanges = new TreeMap<Range, Range>(); // 1's ranges of second
	
	void copyfirst(String first) {
		int l = first.length();
		this.first = new boolean[l];
		int start = -1;
		boolean flag = false;
		for(int i=0; i<l; i++) {
			char ch = first.charAt(i);
			this.first[i] = ch == '1';
			if(ch == '1') {
				if(!flag) {
					flag = true;
					start = i;
				}
			} else {
				if(flag) {
					// range found
					Range range = new Range(start, i-1);
					firstOneRanges.put(range, range);
				}
				flag = false;
			}
		}
		if(flag) {
			// range found
			Range range = new Range(start, l-1);
			firstOneRanges.put(range, range);
		}
	}
	
	void copysecond(String second) {
		int l = second.length();
		this.second = new boolean[l];
		int start = -1;
		boolean flag = false;
		for(int i=0; i<l; i++) {
			char ch = second.charAt(i);
			this.second[i] = ch == '1';
			if(ch == '1') {
				if(!flag) {
					flag = true;
					start = i;
				}
			} else {
				if(flag) {
					// range found
					Range range = new Range(start, i-1);
					secondOneRanges.put(range, range);
				}
				flag = false;
			}
		}
		if(flag) {
			// range found
			Range range = new Range(start, l-1);
			secondOneRanges.put(range, range);
		}
	}
	
	// 0 based position
	void setBitSecond(int position) {
		int l = second.length;
		second[position] = true;
		// update range
		Range range = new Range(position, position); // [position, position] range
		Range foundRange =  secondOneRanges.get(range);
		if(foundRange == null) {
			// range does not exists, try +/- 1
			if(position > 0 && position < l-1 && second[position+1] && second[position-1]) {
				// in between two ranges
				Range left = new Range(position-1, position-1);
				Range right = new Range(position+1, position+1);
				Range leftRange = secondOneRanges.get(left);
				Range rightRange = secondOneRanges.get(right);
				secondOneRanges.remove(left);
				secondOneRanges.remove(right);
				Range newRange = new Range(leftRange.start, rightRange.end);
				secondOneRanges.put(newRange, newRange);
			} else if(position < l-1 && second[position+1]) {
				// right side merge
				Range right = new Range(position+1, position+1);
				Range rightRange = secondOneRanges.get(right);
				secondOneRanges.remove(right);
				Range newRange = new Range(position, rightRange.end);
				secondOneRanges.put(newRange, newRange);
			} else if(position > 0 && second[position-1]) {
				// left side merge
				Range left = new Range(position-1, position-1);
				Range leftRange = secondOneRanges.get(left);
				secondOneRanges.remove(left);
				Range newRange = new Range(leftRange.start, position);
				secondOneRanges.put(newRange, newRange);
			} else {
				// new range
				range = new Range(position, position);
				secondOneRanges.put(range, range);
			}
		}
	}
	
	boolean compare() {
		Iterator<Range> firsti = firstOneRanges.keySet().iterator();
		Iterator<Range> secondi = secondOneRanges.keySet().iterator();
		boolean flag = false;
		Range firstr = null;
		Range secondr = null;
		while(firsti.hasNext() && secondi.hasNext()) {
			firstr = firsti.next();
			secondr = secondi.next();
			System.out.println(firstr.start + " " + firstr.end);
			System.out.println(secondr.start + " " + secondr.end);
			if(secondr.start < firstr.start) {
				flag = true;
				break;
			} else if(secondr.start > firstr.start) {
				break;
			}
		}
		if(firstr != null && secondr != null) {
			flag = firstr.start == secondr.start && firstr.end <= secondr.end;
		}
		return flag;
	}
	
	public static void main(String[] args) {
		String first = "11111";
		String second = "00010";
		
		CompareStrings p = new CompareStrings();
		p.copyfirst(first);
		p.copysecond(second);
		p.setBitSecond(0);
		p.setBitSecond(1);
		p.setBitSecond(2);
		p.setBitSecond(3);
		p.setBitSecond(4);
		System.out.println(p.compare());
	}

}