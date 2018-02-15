package in.iitkgp.computation.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Node {
	char ch;
	Map<Character, Node> children = new HashMap<Character, Node>(2);
	boolean end = false;
	int id;
	
	public Node() {
		// blank
	}
	
	public Node(char ch) {
		this.ch = ch;
	}
}

public class PrefixTreeIndexing {
	Node root = new Node();
	int idCounter = 0;
	Map<Integer, String> masterData = new HashMap<Integer, String>();
	
	// id internally managed
	void add(String data) {
		masterData.put(++idCounter, data);
		add(data, idCounter);
	}
	
	// id externally managed
	void add(String data, int id) {
		Node node = root;
		int l = data.length();
		int c = 0;
		while(c < l) {
			char ch = data.charAt(c);
			Map<Character, Node> children = node.children;
			node = children.get(ch);
			if(node == null) {
				// create
				node = new Node(ch);
				children.put(ch, node);
			}
			c++;
		}
		node.end = true;
		node.id = id;
	}
	
	boolean matchPrefix(String longText) {
		boolean found = false;
		Node node = root;
		int l = longText.length();
		int c = 0;
		while(node != null && c < l) {
			found = node.end;
			char ch = longText.charAt(c);
			Map<Character, Node> children = node.children;
			node = children.get(ch);
			c++;
		}
		return found;
	}
	
	Set<String> getData(String data) {
		Set<Integer> results = getID(data);
		Set<String> texts = new HashSet<String>();
		for(int id : results) {
			texts.add(masterData.get(id));
		}
		return texts;
	}
	
	Set<Integer> getID(String data) {
		Set<Integer> results = new HashSet<Integer>();
		Node node = root;
		int l = data.length();
		int c = 0;
		while(node != null && c < l) {
			char ch = data.charAt(c);
			Map<Character, Node> children = node.children;
			node = children.get(ch);
			c++;
		}
		if(node == null) {
			// empty
			return results;
		}
		// BFS on node
		Queue<Node> nodes = new LinkedList<Node>();
		nodes.add(node);
		while(!nodes.isEmpty()) {
			node = nodes.poll();
			if(node.end) {
				// found
				results.add(node.id);
			}
			for(Node child : node.children.values()) {
				nodes.add(child);
			}
		}
		return results;
	}
	
	public static void main(String[] args) {
		PrefixTreeIndexing p = new PrefixTreeIndexing();
		p.add("application");
		p.add("apple");
		p.add("deba");
		p.add("debasis");
		Set<String> texts = p.getData("appl");
		for(String text : texts) {
			System.out.println(text);
		}
		System.out.println(p.getData("debaxy").size());
		System.out.println(p.matchPrefix("apple fell down"));
		System.out.println(p.matchPrefix("application form"));
		System.out.println(p.matchPrefix("deb"));
	}

}