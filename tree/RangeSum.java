import java.util.*;

// range sum using segment tree
// update operation is not implemented

public class RangeSum {

	public static void main(String a[]) throws Exception {
	
		// IntegerOperation op = new IntegerOperation(new IntegerNode(100), new IntegerNode(200));
		// System.out.println("Sum: " + op.sum());
		
		SegmentTree4Sum tree = new SegmentTree4Sum();
		
		int data[] = {1,2,3,4,5,6,7,8,9};
		// data feed
		for(int d : data) {
			tree.addData(d);
		}
		
		// construct tree
		tree.constructTree();
		
		// print
		// tree.BFSPrint();
		
		// test sum of all possible ranges
		// System.out.println("==================All possible sum ranges=============\n\n");
		int l = data.length;
		for(int i = 0; i < l; i++) {
			for(int j = i; j < l; j++) {
				System.out.println("[" + i + "," + j + "]=> " + tree.sum(i, j));
			}
		}
		
		// System.out.println(tree.sum(0, 2));
		
		// reset
		tree.reset();
	}
}

class NumericNode {
	int data;
	int modifiedData;
	
	NumericNode(int data) {
		this.data = data;
		this.modifiedData = getData();
	}
	
	protected int getData() {
		return data;
	}
	
	int sum(NumericNode anotherData) {
		return anotherData.modifiedData + this.modifiedData;
	}
}

class SegmentTreeNode {
	NumericNode treeNode;
	
	SegmentTreeNode leftNode;
	SegmentTreeNode rightNode;
	// range
	int start, end;
	
	SegmentTreeNode(NumericNode treeNode, int start, int end) {
		this.treeNode = treeNode;
		this.start = start;
		this.end = end;
	}
}

class SegmentTree4Sum {

	SegmentTreeNode root;
	List<NumericNode> nodes = new ArrayList<NumericNode>();

	void addData(int data) {
		nodes.add(new NumericNode(data));
	}
	
	void reset() {
		// TODO
		// nodes.removeAll();
		root = null;
	}
	
	void constructTree() {
		int s = nodes.size();
		if(s == 0) {
			// no tree should be constructed
			return ;
		}
		
		root = iterativeConstruction(nodes, 0, s-1);
	}
	
	// gets sum for a given range
	int sum(int start, int end) {
		
		if(start > end) {
			throw new IllegalArgumentException("start: " + start + " is greater than end: " + end);
		}
		
		if(root == null) {
			throw new IllegalStateException("Tree not constructed yet");
		}
		
		int sum = iterativeSum(root, start, end);
		
		return sum;
	}
	
	int iterativeSum(SegmentTreeNode node, int start, int end) {
	
		int nodeStart = node.start;
		int nodeEnd = node.end;
		
		// compare the range
		if(nodeEnd < start || nodeStart > end) {
			// outside the range
			// System.out.println("[" + nodeStart + ", " + nodeEnd + "]=> Outside the range[" + start + ", " + end + "]");
			return 0;
		} else if(nodeStart >= start && nodeEnd <= end) {
			// within the range
			// System.out.println("[" + nodeStart + ", " + nodeEnd + "]=> Within the range[" + start + ", " + end + "]");
			return node.treeNode.modifiedData;
		} else if(nodeStart <= start || nodeEnd >= end) {
			// part of the range
			SegmentTreeNode left = node.leftNode;
			SegmentTreeNode right = node.rightNode;
			
			// System.out.println("[" + nodeStart + ", " + nodeEnd + "]=> Part the range[" + start + ", " + end + "]");
			
			// sum of two nodes
			return iterativeSum(left, start, end) + iterativeSum(right, start, end);
		} else {
			// should not happen
			return -1;
		}
	}
	
	SegmentTreeNode iterativeConstruction(List<NumericNode> subNodes, int start, int end) {
		int s = subNodes.size();
		if(s == 1) {
			int val = subNodes.get(0).modifiedData;
			return new SegmentTreeNode(new NumericNode(val), start, end);
		}
		int mid = s/2;
		
		// System.out.println("S: " + s + ", Mid: " + mid);
		
		List<NumericNode> firstHalves = subNodes.subList(0, mid);
		List<NumericNode> secondHalves = subNodes.subList(mid, s);
		
		SegmentTreeNode leftNode = iterativeConstruction(firstHalves, start, start + (mid - 1));
		SegmentTreeNode rightNode = iterativeConstruction(secondHalves, start + mid, end);
		
		NumericNode leftTreeNode = leftNode.treeNode;
		NumericNode rightTreeNode = rightNode.treeNode;
		
		int sumVal = leftTreeNode.sum(rightTreeNode);
		
		SegmentTreeNode newNode = new SegmentTreeNode(new NumericNode(sumVal), start, end);
		newNode.leftNode = leftNode;
		newNode.rightNode = rightNode;
		
		return newNode;
	}
	
	void BFSPrint() {
	
		if(root == null) {
			System.out.println("No root defined");
			return;
		} else {
			// print BFS
			System.out.println("=====================Segment Tree=======================\n\n");
			
			List<SegmentTreeNode> levelOrder = new LinkedList<SegmentTreeNode>();
			levelOrder.add(root);
			
			int s;
			while((s = levelOrder.size()) != 0) {
			
				List<SegmentTreeNode> newLevelOrder = new LinkedList<SegmentTreeNode>();
			
				// level order print
				for(SegmentTreeNode node : levelOrder) {
					System.out.print(node.treeNode.modifiedData + "(" + node.start + ", " + node.end + ") ");
					
					// add both children if exists
					if(node.leftNode != null) {
						newLevelOrder.add(node.leftNode);
					}
					if(node.rightNode != null) {
						newLevelOrder.add(node.rightNode);
					}
				}
				
				// new line
				System.out.println("");
				
				// updates level order
				levelOrder = newLevelOrder;
			}
		}
	}
}