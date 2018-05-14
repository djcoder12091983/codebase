package hackerearth.practice.ds;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

class TrieNode {
	char ch;
	boolean end = false;
	int weight;
	Map<Character, TrieNode> children = new HashMap<Character, TrieNode>(2); // children
	
	public TrieNode(char ch) {
		this.ch = ch;
	}
	
	void markEnd(int weight) {
		this.weight = weight;
		end = true;
	}
	
	void add(TrieNode node) {
		children.put(node.ch, node);
	}
	
	TrieNode get(char ch) {
		return children.get(ch);
	}
}

/*class WordWeight implements Comparable<WordWeight> {
	
	String word;
	int weight;
	
	public WordWeight(String word, int weight) {
		this.word = word;
		this.weight = weight;
	}
	
	@Override
	public int compareTo(WordWeight ww) {
		// descending order
		return new Integer(ww.weight).compareTo(new Integer(this.weight));
	}
}

class TrieNodeWrapper {
	TrieNode node;
	StringBuilder prefix = new StringBuilder();
	
	public TrieNodeWrapper(TrieNode node, String prefix) {
		this.node = node;
		this.prefix.append(prefix).append(node.ch);
	}
}*/

class TrieDS {
	
	TrieNode root = new TrieNode('R');
	
	void add(String word, int weight) {
		int l = word.length();
		TrieNode current = root;
		for(int i=0; i<l-1; i++) {
			char ch = word.charAt(i);
			TrieNode temp = current.get(ch);
			if(temp == null) {
				// node does not exist
				temp = new TrieNode(ch);
				current.add(temp);
			}
			current = temp;
		}
		// last word with end marking
		char last = word.charAt(l-1);
		TrieNode temp = current.get(last);
		if(temp == null) {
			// node does not exist
			temp = new TrieNode(last);
			current.add(temp);
		}
		temp.markEnd(weight);
	}
	
	int maxWeight(String prefix) {
		//List<WordWeight> words = new LinkedList<WordWeight>();
		
		int l = prefix.length();
		TrieNode current = root;
		boolean flag = false;
		for(int i=0; i<l; i++) {
			char ch = prefix.charAt(i);
			current = current.get(ch);
			if(current == null) {
				// no such string exists
				flag = true;
				break;
			}
		}
		
		if(flag) {
			// no such string exists
			return -1;
		} else {
			// scan the subtree of 'current-node'
			// BFS traversal
			int max = Integer.MIN_VALUE;
			Queue<TrieNode> nodes = new LinkedList<TrieNode>();
			nodes.add(current);
			while(!nodes.isEmpty()) {
				TrieNode node = nodes.poll();
				boolean end = node.end;
				if(end) {
					// end word
					int w = node.weight;
					if(w > max) {
						// new MAX
						max = w;
					}
				}
				nodes.addAll(node.children.values()); //add next nodes
			}
			return max;
		}
		
		/*// get max
		int max = Integer.MIN_VALUE;
		for(WordWeight ww : words) {
			int w = ww.weight;
			if(w > max) {
				max = w;
			}
		}
		return max;*/
	}
}

public class SEAutoComplete {
	
	public static void main(String[] args) {
		
		TrieDS t = new TrieDS();
		/*t.add("hackerearth", 10);
		t.add("hackerrank", 9);
		t.add("application", 7);
		t.add("apple", 11);
		System.out.println(t.maxWeight("hacker"));
		System.out.println(t.maxWeight("xyz"));
		System.out.println(t.maxWeight("app"));*/
		
		// cache to save time
		Map<String, Integer> maxScores = new HashMap<String, Integer>();
		
		//Scanner
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int q = s.nextInt();
        for(int i=0; i<n; i++) {
        	t.add(s.next(), s.nextInt());
        }
        for(int i=0; i<q; i++) {
        	String prefix = s.next();
        	Integer max = maxScores.get(prefix);
        	if(max == null) {
        		// not in cache, cache it
        		max = t.maxWeight(prefix);
        		maxScores.put(prefix, max);
        	}
        	
        	System.out.println(max);
        }
        
        s.close();
	}

}