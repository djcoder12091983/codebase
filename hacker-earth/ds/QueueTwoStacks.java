package hackerrank.practice.ds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class QueueTwoStacks {
	
	Stack<Integer> first = new Stack<Integer>();
	Stack<Integer> second = new Stack<Integer>();
	
	void enqueue(int item) {
		first.push(item);
	}
	
	int dequeue() {
		if(!second.isEmpty()) {
			// second, natural stack order
			return second.pop();
		} else {
			// pop all the elements except last
			// add to second
			int size = first.size();
			int c = 0;
			while(c++<size-1) {
				second.push(first.pop());
			}
			return first.pop();
		}
	}
	
	int print() {
		// second, natural stack order
		if(!second.isEmpty()) {
			return second.peek();
		} else {
			// pop all the elements except last
			// add to second
			int size = first.size();
			int c = 0;
			while(c++<size) {
				second.push(first.pop());
			}
			return second.peek();
		}
	}
	
	public static void main(String[] args) throws Exception {
		//System.setIn(new FileInputStream("/home/dspace/debasis/NDL/HackerRank/data/q2.input02.txt"));
		QueueTwoStacks q2 = new QueueTwoStacks();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(reader.readLine());
		for(int i=0; i<tc; i++) {
			String tokens[] = reader.readLine().split(" ");
			int type = Integer.parseInt(tokens[0]);
			if(type == 1) {
				// add
				q2.enqueue(Integer.parseInt(tokens[1]));
			} else if(type == 2) {
				// remove
				q2.dequeue();
			} else {
				// print
				System.out.println(q2.print());
			}
		}
	}

}