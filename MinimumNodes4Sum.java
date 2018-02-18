package hackerearth.hiring.challenges.date16022018.ee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Node {
	//int label;
	Node parent = null;
	int v;
	List<Node> children = null;
	
	Node(int v) {
		this.v = v;
		children = new LinkedList<Node>();
	}
}

class SumNode {
	int sum = 0;
	int c = 1;
	Node node;
	
	SumNode(int sum, int c, Node node) {
		this.sum = sum;
		this.c =  c;
		this.node = node;
	}
}

class Tree4Sum {
	
	List<Integer> values = new ArrayList<Integer>();
	Map<Integer, Node> map = new HashMap<Integer, Node>();
	Node root = null;
	
	void addValue(int v) {
		values.add(v);
	}
	
	void addEdge(int u, int v) {
		Node parent = map.get(u);
		if(parent == null) {
			int val = values.get(u-1);
			parent = new Node(val);
			map.put(u, parent);
			if(u == 1) {
				// root
				root = parent;
			}
		}
		Node child = map.get(v);
		if(child == null) {
			int val = values.get(v-1);
			child = new Node(val);
			map.put(v, child);
		}
		
		child.parent = parent; // both way link
		parent.children.add(child);
	}
	
	int query(int x, int k) {
		int c = Integer.MAX_VALUE;
		Node start = map.get(x);
		c = BFS(start, start.v, 1, c, k, null); // BFS, top down
		if(start != root) {
			// go to parent again top down
			int nc = 1;
			Node skip = start;
			int sum = start.v;
			start = start.parent;
			while(start != null) {
				sum += start.v;
				nc++;
				c = BFS(start, sum, nc, c, k, skip); // BFS top down
				skip = start;
				start = start.parent;
			}
		}
		
		return c;
	}
	
	int BFS(Node start, int sum, int nc, int c, int k, Node skip) {
		Queue<SumNode> nodes = new LinkedList<SumNode>();
		nodes.add(new SumNode(sum, nc, start));
		// BFS
		while(!nodes.isEmpty()) {
			SumNode node = nodes.poll();
			List<Node> children = node.node.children;
			for(Node child : children) {
				if(child == skip) {
					// skip node
					continue;
				}
				int newsum = node.sum + child.v;
				int newc = node.c + 1;
				if(newsum < k) {
					// keep on adding until K
					nodes.add(new SumNode(newsum, newc, child));
				} else {
					if(newc < c) {
						// min count
						c = newc;
					}
				}
			}
		}
		
		return c;
	}
}

public class MinimumNodes4Sum {
	
	public static void main(String[] args) {
		
		Tree4Sum tree = new Tree4Sum();
		tree.addValue(2);
		tree.addValue(3);
		tree.addValue(4);
		tree.addValue(5);
		tree.addEdge(1, 2);
		tree.addEdge(2, 3);
		tree.addEdge(1, 4);
		System.out.println(tree.query(1, 6));
		System.out.println(tree.query(2, 5));
		
		/*Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int q = s.nextInt();
		for(int i = 0; i < n; i++) {
			tree.addValue(s.nextInt());
		}
		for(int i = 0; i < n-1; i++) {
			tree.addEdge(s.nextInt(), s.nextInt());
		}
		for(int i = 0; i < q; i++) {
			System.out.println(tree.query(s.nextInt(), s.nextInt()));
		}
		s.close();*/
	}

}