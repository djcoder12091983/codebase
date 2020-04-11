package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
 
public class RestoringTrees2 {
	
	class TNode {
		int node;
		List<TNode> children = new LinkedList<>();
		TNode parent;
		int end; // temporary usage
		
		public TNode(int node) {
			this.node = node;
		}
		
		TNode add(int node) {
			TNode cnode = new TNode(node);
			cnode.parent = this;
			children.add(cnode);
			return cnode;
		}
	}
	
	// restore tree by start and finish time
	int[] restore(int starts[], StringTokenizer endtokens) {
		class Time {
			int start;
			int end;
			int vertex;
			
			public Time(int vertex, int start, int end) {
				this.vertex = vertex;
				this.start = start;
				this.end = end;
			}
		}
		
		int l = starts.length;
		int tree[] = new int[l]; // tree output
		Time[] times = new Time[l];
		for(int i = 0; i < l; i++) {
			int s = starts[i];
			int e = Integer.parseInt(endtokens.nextToken());
			int v = i + 1; // 1 based
			Time t = new Time(v, s, e);
			times[s] = t; // sort the data by start time
		}
		
		// restore tree
		Stack<TNode> track = new Stack<>();
		Time first = times[0];
		int fv = first.vertex;
		TNode root = new TNode(fv); // root node
		root.end = first.end;
		track.push(root);
		tree[fv - 1] = 0;
		TNode node = root; // current acting node
		boolean leaf = first.end - first.start == 1;
		for(int i = 1; i < l; i++) {
			Time t = times[i];
			int s = t.start;
			int e = t.end;
			int v = t.vertex;
			if(leaf) {
				// previous node is leaf, then next node position computation
				node = find(s, track).parent;
			}
			// keep on adding
			node = node.add(v);
			node.end = e;
			track.push(node);
			
			// tree output population
			tree[v - 1] = node.parent.node;
			leaf = e - s == 1;
		}
		
		return tree; // output
	}
	
	// find suitable position by start time if previous node is leaf
	// track of nodes help
	TNode find(int start, Stack<TNode> track) {
		TNode node = null;
		while(track.peek().end == start) {
			node = track.pop();
		}
		return node;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(reader.readLine());
		StringTokenizer tokens = new StringTokenizer(reader.readLine());
		int i = 0;
		int starts[] = new int[n];
		while(i < n) {
			starts[i++] = Integer.parseInt(tokens.nextToken());
		}
		tokens = new StringTokenizer(reader.readLine());
		// execute
		RestoringTrees2 rt = new RestoringTrees2();
		int[] tree = rt.restore(starts, tokens);
		for(int tn : tree) {
			System.out.print(tn + " ");
		}
		
		reader.close();
	}
}