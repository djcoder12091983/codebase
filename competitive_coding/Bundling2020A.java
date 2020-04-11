package google.kickstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Bundling2020A {
	
	static final String NEW_LINE = System.getProperty("line.separator");
	
	// note: prefix tree based approach
	
	class Node {
		char key;
		Map<Character, Node> children = new HashMap<>(2);
		// additional information
		int count = 0, used = 0;
		
		// blank
		Node() {}
		
		Node(char key) {
			this.key = key;
		}
	}
	
	Node root = new Node();
	int score = 0;
	
	int K;
	
	public Bundling2020A(int K) {
		this.K = K;
	}
	
	// add word
	void add(String word) {
		int l = word.length();
		Node node = root;
		for(int i = 0; i < l; i++) {
			char key = word.charAt(i);
			Node cnode = node.children.get(key);
			if(cnode == null) {
				cnode = new Node(key);
				node.children.put(key, cnode);
			}
			node = cnode;
		}
		node.count++; // end indicator
	}
	
	class Pair {
		int count = 0, used = 0;
		
		// blank
		Pair() {}
		
		Pair(int count, int used) {
			this.count = count;
			this.used = used;
		}
	}
	
	// solve, note: DFS based approach to compute words count and level on each node
	Pair callDFS(Node node, int level) {
		if(node == null) {
			return new Pair();
		}
		Pair t = new Pair();
		Map<Character, Node> children = node.children;
		for(Node cnode : children.values()) {
			Pair ct = callDFS(cnode, level + 1);
			t.count += ct.count;
			t.used += ct.used;
		}
		node.count += t.count;
		int rem = node.count - t.used;
		if(rem >= K) {
			int c = rem / K;
			score += c * level;
			node.used = t.used + c * K;
		} else {
			node.used = t.used;
		}
		
		return new Pair(node.count, node.used);
	}
	
	void solve() {
		callDFS(root, 0);
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(reader.readLine());
		int c = 0;
		StringBuilder result = new StringBuilder();
		while(++c <= T) {
			int N, K;
			StringTokenizer tokens = new StringTokenizer(reader.readLine());
			N = Integer.parseInt(tokens.nextToken());
			K = Integer.parseInt(tokens.nextToken());
			
			Bundling2020A driver = new Bundling2020A(K);
			
			for(int i = 0; i < N; i++) {
				String word = reader.readLine();
				driver.add(word);
			}
			
			// solution
			driver.solve();
			result.append("Case #").append(c).append(": ").append(driver.score).append(NEW_LINE);
		}
		
		System.out.println(result);
		
		reader.close();
	}
}