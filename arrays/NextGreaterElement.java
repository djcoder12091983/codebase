package practice.computation.array;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// using a comparator we can generalize it as nextSmaller or nextGreater
public class NextGreaterElement {
	
	class Element {
		int value;
		int position; // 0 based
		
		public Element(int value, int position) {
			this.value = value;
			this.position = position;
		}
	}
	
	List<Integer> data = null;
	Deque<Element> stack = new LinkedList<Element>();
	
	public NextGreaterElement(int size) {
		data = new ArrayList<Integer>(size);
	}
	
	void add(int d) {
		data.add(d);
	}
	
	void add(int data[]) {
		// add all
		for(int d : data) {
			this.data.add(d);
		}
	}
	
	int[] nextGreater() {
		int s = data.size();
		int result[] = new int[s];
		for(int i=0; i<s; i++) {
			int d = data.get(i);
			// iterate over data
			while(!stack.isEmpty()) {
				Element peek = stack.peek();
				if(d > peek.value) {
					// pop and map the greater-element
					Element top = stack.pop();
					result[top.position] = d; // last pop is next greater
				} else {
					// break
					break;
				}
			}
			stack.push(new Element(d, i)); // push the new number
		}
		// remaining element
		while(!stack.isEmpty()) {
			Element top = stack.pop();
			result[top.position] = -1; // no next greater element found
		}
		return result; // result
	}
	
	void print() {
		int[] result = nextGreater();
		for(int r : result) {
			System.out.print(r + " ");
		}
	}
	
	public static void main(String[] args) {
		
		NextGreaterElement p = new NextGreaterElement(8);
		p.add(new int []{3, 7, 1, 7, 8, 4, 5, 2});
		p.print();
	}
}