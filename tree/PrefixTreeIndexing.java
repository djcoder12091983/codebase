package in.iitkgp.computation.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Node<T> {
	T t;
	Map<T, Node<T>> children = new HashMap<T, Node<T>>(2);
	boolean end = false;
	List<Integer> id = new LinkedList<Integer>();
	int count = 0;
	
	public Node() {
		// blank
	}
	
	public Node(T t) {
		this.t = t;
	}
}

interface Splitter<T> {
	T[] split(String data);
}

public class PrefixTreeIndexing<T> {
	Node<T> root = new Node<T>();
	int idCounter = 0;
	Map<Integer, String> masterData = new HashMap<Integer, String>();
	Splitter<T> splitter;
	
	public PrefixTreeIndexing(Splitter<T> splitter) {
		this.splitter = splitter;
	}
	
	int add2Master(String data) {
		idCounter++;
		masterData.put(idCounter, data);
		return idCounter;
	}
	
	// id internally managed
	void add(String data) {
		if(add(data, idCounter + 1)) {
			add2Master(data);
		}
	}
	
	// id externally managed
	boolean add(String data, int id) {
		T[] tokens = splitter.split(data);
		Node<T> node = root;
		int l = tokens.length;
		int c = 0;
		while(c < l) {
			T t = tokens[c];
			Map<T, Node<T>> children = node.children;
			node = children.get(t);
			if(node == null) {
				// create
				node = new Node<T>(t);
				children.put(t, node);
			}
			c++;
		}
		node.count++;
		if(!node.end) {
			// new entry
			node.end = true;
			node.id.add(id);
			return true;
		} else {
			// duplicate entry
			return false;
		}
	}
	
	boolean matchPrefix(String longText) {
		T[] tokens = splitter.split(longText);
		boolean found = false;
		Node<T> node = root;
		int l = tokens.length;
		int c = 0;
		while(node != null && c < l) {
			found = node.end;
			T t = tokens[c];
			Map<T, Node<T>> children = node.children;
			node = children.get(t);
			c++;
		}
		return found;
	}
	
	Set<String> getData(String prefix) {
		Set<Integer> results = getID(prefix);
		Set<String> texts = new HashSet<String>();
		for(int id : results) {
			texts.add(masterData.get(id));
		}
		return texts;
	}
	
	Set<Integer> getID(String prefix) {
		T[] tokens = splitter.split(prefix);
		Set<Integer> results = new HashSet<Integer>();
		Node<T> node = root;
		int l = tokens.length;
		int c = 0;
		while(node != null && c < l) {
			T t = tokens[c];
			Map<T, Node<T>> children = node.children;
			node = children.get(t);
			c++;
		}
		if(node == null) {
			// empty
			return results;
		}
		// BFS on node
		Queue<Node<T>> nodes = new LinkedList<Node<T>>();
		nodes.add(node);
		while(!nodes.isEmpty()) {
			node = nodes.poll();
			if(node.end) {
				// found
				results.addAll(node.id);
			}
			for(Node<T> child : node.children.values()) {
				nodes.add(child);
			}
		}
		return results;
	}
	
	public static void main(String[] args) {
		PrefixTreeIndexing<Character> p = new PrefixTreeIndexing<Character>(new Splitter<Character>() {
			@Override
			public Character[] split(String data) {
				int l = data.length();
				Character[] tokens = new Character[l];
				for(int i = 0; i < l; i++) {
					tokens[i] = data.charAt(i);
				}
				return tokens;
			}
		});
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