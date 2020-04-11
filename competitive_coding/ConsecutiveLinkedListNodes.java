package others;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ConsecutiveLinkedListNodes {
	
	// display stored result
	static void display(Queue<Integer> queue) {
		StringBuilder res = new StringBuilder().append('[').append(queue.poll());
		while(!queue.isEmpty()) {
			res.append(',').append(queue.poll());
		}
		res.append(']');
		// print
		System.out.println(res.toString());
	}
	
	// list: linked list
	// A: nodes
	static void solve(LinkedList<Integer> list, int A[]) {
		int l = A.length;
		int i = 0;
		Iterator<Integer> listi = list.iterator();
		Queue<Integer> tqueue = new LinkedList<>();
		while(listi.hasNext() && i < l) {
			int node = listi.next();
			if(node == A[i]) {
				tqueue.add(node); // keep on adding (continuation)
				i++;
			} else {
				// sequence break
				if(!tqueue.isEmpty()) {
					display(tqueue);
					// reset
					tqueue = new LinkedList<>();
				}
			}
		}
		// if last exists
		if(!tqueue.isEmpty()) {
			display(tqueue);
		}
	}
	
	public static void main(String[] args) {
		// #TC1
		LinkedList<Integer> list = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		solve(list, new int[] {1, 3, 5, 6, 8, 9});
		// #TC2
		System.out.println();
		System.out.println();
		list = new LinkedList<>(Arrays.asList(10, 11, 3, 7, 8, 12, 15, 19, 100, 33, 45, 67, 123, 89));
		solve(list, new int[] {10, 11, 3, 7, 15, 100, 45, 67});
	}
}