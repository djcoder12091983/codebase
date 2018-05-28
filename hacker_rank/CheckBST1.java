package hackerrank.interview;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class CheckBST1 {
	
	Node root;
	
	class Node {
        int data;
        Node left;
        Node right;
        
        public Node(int data) {
			this.data = data;
		}
     }
	
	boolean checkBST() {
		if(root == null) {
			// best case
			return true;
		}
		// in order traverse, and check whether they are in sequence
		Deque<Node> stack = new LinkedList<Node>();
		Node current = root;
		Queue<Integer> temp = new LinkedList<Integer>();
		boolean bal = true;
		while(!stack.isEmpty() || current != null) {
			if(current != null) {
				stack.push(current);
				current = current.left;
			} else {
				// pop
				current = stack.pop();
				if(temp.size() == 2) {
					// check sequence
					int first = temp.poll();
					int second = temp.peek();
					if(first > second) {
						// order violates
						bal = false;
						break;
					}
				}
				temp.add(current.data); // keep on adding
				
				// now right turn
				current = current.right;
			}
		}
		return bal;
	}
	
	void addnode(int data) {
		if(root == null) {
			// first node
			root = new Node(data);
			return;
		}
		// find entry point
		Node current = root;
		while(true) {
			int d = current.data;
			if(data <= d) {
				// left movement
				if(current.left == null) {
					// entry point
					current.left = new Node(data);
					break;
				} else {
					current = current.left;
				}
			} else if(data > d) {
				// right movement
				if(current.right == null) {
					// entry point
					current.right = new Node(data);
					break;
				} else {
					current = current.right;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		CheckBST1 cb = new CheckBST1();
		String input = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 16 18 19 20 21 22 23 24 25 26 27 28 29 30 31";
		StringTokenizer tokens = new StringTokenizer(input);
		while(tokens.hasMoreTokens()) {
			cb.addnode(Integer.valueOf(tokens.nextToken()));
		}
		System.out.println(cb.checkBST());
	}

}