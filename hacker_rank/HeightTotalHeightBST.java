package rookierank4.date19022018;

import java.util.Scanner;

class TreeNode {
	int data;
	TreeNode left;
	TreeNode right;
	TreeNode parent;
	int height;
	
	TreeNode(int data) {
		this.data = data;
	}
	
	void addLeft(int child) {
		// left child
		left = new TreeNode(child);
		left.parent = this;
	}
	
	void addRight(int child) {
		// right child
		right = new TreeNode(child);
		right.parent = this;
	}
}

class BSTTreeManager {
	
	TreeNode root;
	int totalHeight;
	int rootHeight;
	
	boolean find(int data) {
		TreeNode node = root;
		boolean found = false;
		while(node != null) {
			int d = node.data;
			if(data > d) {
				// right
				node = node.right;
			} else if(data < d) {
				// left
				node = node.left;
			} else {
				// equals
				found = true;
				break;
			}
		}
		return found;
	}
	
	void add(int data) {
		if(root == null) {
			// first
			root = new TreeNode(data);
		} else {
			// traverse
			TreeNode node = root;
			while(true) {
				int d = node.data;
				if(data > d) {
					// right
					TreeNode nextNode = node.right;
					if(nextNode == null) {
						node.addRight(data);
						break;
					} else {
						node = nextNode;
					}
				} else if(data < d) {
					// left
					TreeNode nextNode = node.left;
					if(nextNode == null) {
						node.addLeft(data);
						break;
					} else {
						node = nextNode;
					}
				} else {
					// equals
					// NO OP. skip the data
					break;
				}
			}
		}
	}
	
	// tags height @ each node
	int tagHeight(TreeNode node) {
		if(node == null) {
			// best case
			return 0;
		}
		TreeNode left = node.left;
		TreeNode right = node.right;
		int leftHeight = tagHeight(left);
		int rightHeight = tagHeight(right);
		boolean exists = left != null || right != null;
		// take maximum and add 1 in case of child exists
		node.height = (exists ? 1 : 0) + (leftHeight > rightHeight ? leftHeight : rightHeight);
		totalHeight += node.height; 
		return node.height;
	}
	
	
	void calculateHeights() {
		totalHeight = 0; // reset
		rootHeight = tagHeight(root);
	}
}

public class HeightTotalHeightBST {
	
	public static void main(String[] args) throws Exception {
		BSTTreeManager bst = new BSTTreeManager();
		//InputStream file = new FileInputStream(new File("/home/dspace/debasis/NDL/HackerRank/data/bst.tc2"));
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		for(int i = 0; i < n; i++){
			bst.add(in.nextInt());
		}
		bst.calculateHeights();
		System.out.println(bst.rootHeight);
		System.out.println(bst.totalHeight);
		in.close();
	}

}
