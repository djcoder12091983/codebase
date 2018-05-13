package hackerearth.practice.ds;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class TreeNode {
	int value;
	TreeNode left;
	TreeNode right;
	
	public TreeNode(int value) {
		this.value = value;
	}
}

public class BSTPreorderTraversal {
	
	TreeNode root = null;
	
	void addNode(int value) {
		if(root == null) {
			root = new TreeNode(value);
		} else {
			// traverse
			TreeNode current = root;
			while(true) {
				int currentVal = current.value;
				if(value <= currentVal) {
					if(current.left == null) {
						// insertion point
						current.left = new TreeNode(value);
						break;
					} else {
						// next node
						current = current.left;
					}
				} else {
					if(current.right == null) {
						// insertion point
						current.right = new TreeNode(value);
						break;
					} else {
						// next node
						current = current.right;
					}
				}
			}
		}
	}
	
	List<Integer> preorder(TreeNode node) {
		List<Integer> result = new LinkedList<Integer>();
		
		Stack<TreeNode> nodes = new Stack<TreeNode>();
		nodes.push(node);
		// root left right
		while(!nodes.isEmpty()) {
			// traversal
			TreeNode current = nodes.pop();
			int value = current.value;
			result.add(value);
			// next to visit
			if(current.right != null) {
				// right node visit next
				nodes.push(current.right);
			}
			if(current.left != null) {
				// left node visit earlier
				nodes.push(current.left);
			}
		}
		
		return result;
	}
	
	TreeNode search(int value) {
		TreeNode current  = root;
		TreeNode found = null;
		while(current != null) {
			// traverse
			int currentVal = current.value;
			if(currentVal == value) {
				// found
				found = current;
				break;
			}
			if(value < currentVal) {
				// left move
				current = current.left;
			} else {
				// right move
				current = current.right;
			}
		}
		return found;
	}
	
	public static void main(String[] args) {
		
		BSTPreorderTraversal t = new BSTPreorderTraversal();
		
		//Scanner
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        for(int i=0; i<n; i++) {
        	t.addNode(s.nextInt());
        }
        int q = s.nextInt();
        TreeNode qnode = t.search(q);
        List<Integer> preorder = t.preorder(qnode);
        for(Integer value : preorder) {
        	System.out.println(value);
        }
        
        s.close();
	}

}