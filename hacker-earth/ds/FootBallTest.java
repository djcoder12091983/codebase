package hackerearth.practice.ds;

import java.util.LinkedList;
import java.util.Scanner;

// with linked-list
class CustomStack {
	
	LinkedList<Integer> items = new LinkedList<Integer>();
	
	void push(int item) {
		items.add(item);
	}
	
	int pop() {
		// assume pop operation doesn't error
		return items.removeLast();
	}
	
	int peek() {
		return items.getLast();
	}
	
	boolean isEmpty() {
		return items.size() == 0;
	}
	
	int size() {
		return items.size();
	}
	
	void clear() {
		items.clear();
	}
}

public class FootBallTest {
	
	CustomStack transitions = new CustomStack();
	int from = 0; // from player
	
	void pass(int next) {
		if(!transitions.isEmpty()) {
			from = transitions.peek(); // touch
		}
		transitions.push(next);
	}
	
	void back(int count) {
		if(count%2==1) {
			// pop
			from = transitions.pop();
		} else {
			// loop
			pass(from);
		}
	}
	
	String last() {
		return "Player " + transitions.peek();
	}
	
	void reset() {
		transitions.clear();
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
        int tc = s.nextInt();
        
        for(int i=0; i<tc; i++) {
        	int n = s.nextInt();
        	int id = s.nextInt();
        	
        	FootBallTest f = new FootBallTest(); // new for each test case
        	f.pass(id);
        	
        	// each test-case
        	int backcount = 0;
        	for(int k=0; k<n; k++) {
        		char type = s.next().charAt(0);
        		if(type == 'P') {
        			// simply pass
        			f.pass(s.nextInt());
        			backcount = 0; // reset back count
        		} else {
        			// pass-back
        			f.back(++backcount);
        		}
        	}
        	
        	System.out.println(f.last());
        	//f.reset(); // reset result
        }
        
        s.close();
	}

}