package hackerearth.practice.ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Node {
	Node left;
	Node right;
	int leftHeight = 0;
	int rightHeight = 0;
	int value;
	
	public Node(int value) {
		this.value = value;
	}
	
	Node add2Left(int value) {
		left = new Node(value);
		return left;
	}
	
	Node add2Right(int value) {
		right = new Node(value);
		return right;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
	
	public void setLeftHeight(int leftHeight) {
		this.leftHeight = leftHeight;
	}
	
	public void setRightHeight(int rightHeight) {
		this.rightHeight = rightHeight;
	}
	
	public int getLeftHeight() {
		return leftHeight;
	}
	
	public int getRightHeight() {
		return rightHeight;
	}
	
	int maxHeight() {
		return leftHeight > rightHeight ? leftHeight : rightHeight;
	}
}

public class DiameterBinaryTree {
	
	Node root = null;
	int diameter = Integer.MIN_VALUE;
	
	void addRoot(int value) {
		root = new Node(value);
	}
	
	void addNode(String path, int value) {
		int l = path.length();
		Node current = root;
		for(int i=0; i<l-1; i++) {
			// traverse
			char p = path.charAt(i);
			current = p == 'L' ? current.getLeft() : current.getRight();
		}
		// add node
		char p = path.charAt(l-1);
		if(p == 'L') {
			current.add2Left(value);
		} else {
			current.add2Right(value);
		}
	}
	
	int diameter() {
		height(root); // process
		return diameter;
	}
	
	int height(Node node) {
		Node left = node.getLeft();
		Node right = node.getRight();
		int lh = (left != null) ? (height(left)+1) : 0;
		int rh = (right != null) ? (height(right)+1) : 0;
		node.setLeftHeight(lh);
		node.setRightHeight(rh);
		// calculate diameter
		int nd = lh + 1 + rh; // new diameter
		if(nd > diameter) {
			diameter = nd; // maximum diameter so far
		}
		return node.maxHeight(); // maximum height
	}
	
	public static void main(String[] args) {
		
		DiameterBinaryTree t = new DiameterBinaryTree();
		
		class PathNode implements Comparable<PathNode>{
			String path;
			int value;
			
			public PathNode(String path, int value) {
				this.path = path;
				this.value = value;
			}
			
			public int compareTo(PathNode node) {
				String path = node.path;
				return new Integer(this.path.length()).compareTo(new Integer(path.length())); // length wise comparator
			};
		}
		
		//Scanner
        Scanner s = new Scanner(System.in);
        int tc = s.nextInt();
        int root = s.nextInt();
        t.addRoot(root);
        
        List<PathNode> inputs = new ArrayList<PathNode>();
        for(int i=0; i<tc-1; i++) { // N-1 nodes
        	String path = s.next();
        	int node = s.nextInt();
        	inputs.add(new PathNode(path, node));
        }
        // sort to ensure the order of tree-node insertion
        Collections.sort(inputs);
        // now safe to insert tree-nodes
        for(PathNode input : inputs) { // N-1 nodes
        	String path = input.path;
        	int node = input.value;
        	t.addNode(path, node);
        }
        System.out.println(t.diameter());
        
        s.close();
	}

}