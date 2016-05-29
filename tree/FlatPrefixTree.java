// flat (not-compressed) prefix tree

import java.util.*;

public class FlatPrefixTree {

	class PrefixNode {
		char prefix;
		
		// node represents whether value exists
		boolean valueExists = false;
		
		PrefixNode(char prefix) {
			this.prefix = prefix;
		}
		
		Map<Character, PrefixNode> prefixes = new HashMap<Character, PrefixNode>(4);
	}
	
	PrefixNode root = new PrefixNode('R');
	
	// adds
	void add(String v) {
		
		// walk through tree
		// List<Character> prefixNodes = new LinkedList<Character>();
		
		PrefixNode currentNode = root;
		PrefixNode insertionNode = null;
		
		int c = -1;
		int l = v.length();
		while(currentNode != null) {
		
			insertionNode = currentNode;
			
			Map<Character, PrefixNode> prefixNodes = currentNode.prefixes;
			// look up
			++c;
			if(c == l) {
				// break, this condition appears when same string gets entry
				break;
			}
			char ch = v.charAt(c);
			currentNode = prefixNodes.get(ch);
		}
		
		// adds to 'insertionNode'
		for(int i = c; i < l; i++) {
			char ch = v.charAt(i);
			PrefixNode newNode = new PrefixNode(ch);
			insertionNode.prefixes.put(ch, newNode);
			
			// trun on exists flag
			if(i == l - 1) {
				newNode.valueExists = true;
			}
			
			// move insertion node
			insertionNode = newNode;
		}
	}
	
	// finds exact string whether exists
	boolean find(String v) {
	
		int l = v.length();
		
		// walk through tree
		PrefixNode currentNode = root;
		// PrefixNode parentNode = null;
		
		int c = -1;
		// boolean found = true;
		while(currentNode != null) {
		
			// parentNode = currentNode;
		
			Map<Character, PrefixNode> prefixNodes = currentNode.prefixes;
			// look up
			++c;
			if(c == l) {
				break;
			} 
			char ch = v.charAt(c);
			currentNode = prefixNodes.get(ch);
		}
		
		// System.out.println("c: " + c + ", flag: " + currentNode.valueExists);
		
		// returns true whether eact string found (not prefix string eixts)
		return c == l && currentNode.valueExists == true;
	}
	
	// find strings by prefix
	List<String> findByPrefix(String prefix) {
		
		int l = prefix.length();
		
		// walk through tree
		PrefixNode currentNode = root;
		// PrefixNode parentNode = null;
		
		int c = -1;
		// boolean found = true;
		while(currentNode != null) {
		
			// parentNode = currentNode;
		
			Map<Character, PrefixNode> prefixNodes = currentNode.prefixes;
			// look up
			++c;
			if(c == l) {
				break;
			} 
			char ch = prefix.charAt(c);
			currentNode = prefixNodes.get(ch);
		}
		
		if(c == l) {
			// prefix found
			// gets all possible values from sub-tree
			
			List<String> values = new LinkedList<String>();
			
			// gets possible branches
			Map<Character, PrefixNode> prefixNodes = currentNode.prefixes;
			Set<Character> keys = prefixNodes.keySet();
			
			if(keys.isEmpty()) {
				// no key found
				if(currentNode.valueExists) {
					values.add(prefix);
					return values;
				} else {
					return values;
				}
			}
			
			// PrefixNode startNode = currentNode;
			Collection<PrefixNode> levelNodes = new LinkedList<PrefixNode>();
			List<StringBuffer> levelValues = new LinkedList<StringBuffer>();
			
			levelNodes.add(currentNode);
			levelValues.add(new StringBuffer(prefix));
			
			while(!levelNodes.isEmpty()) {
			
				List<PrefixNode> newLevelNodes = new LinkedList<PrefixNode>();
				List<StringBuffer> newLevelValues = new LinkedList<StringBuffer>();
				
				Iterator<StringBuffer> levelValuesIterator = levelValues.iterator();
			
				for(PrefixNode node : levelNodes) {
				
					// extract possible values on the basis of flag
					StringBuffer levelValue = levelValuesIterator.next();
					if(node.valueExists) {
						values.add(levelValue.toString());
					}
					
					Collection<PrefixNode> nextLevelNodes = node.prefixes.values();
					// next level details
					for(PrefixNode nextNode : nextLevelNodes) {
						newLevelNodes.add(nextNode);
						
						StringBuffer copy = new StringBuffer(levelValue);
						newLevelValues.add(copy.append(nextNode.prefix));
					}
				}
				
				// new next details
				levelNodes = newLevelNodes;
				levelValues = newLevelValues;
			}
			
			// returns found values
			return values;
			
		} else {
			// no prefix found
			return new LinkedList<String>();
		}
	}
	
	// prints BFS for testing
	void printBFS() {
	
		List<PrefixNode> nodes = new LinkedList<PrefixNode>();
		nodes.add(root);
		
		System.out.println("[" + root.prefix + "]");
		
		while(!nodes.isEmpty()) {
		
			List<PrefixNode> newNodes = new LinkedList<PrefixNode>();
			
			for(PrefixNode node : nodes) {
			
				Collection<PrefixNode> prefixes = node.prefixes.values();
				newNodes.addAll(prefixes);
				
				System.out.print("[");
				for(PrefixNode prefx : prefixes) {
					System.out.print(prefx.prefix + "(" + (prefx.valueExists ? "T" : "F") + ") ");
				}
				System.out.print("] ");
			}
			
			System.out.println();
			
			// next level nodes
			nodes = newNodes;
		}
	}
	
	public static void main(String a[]) {
		
		FlatPrefixTree tree = new FlatPrefixTree();
		
		String words[] = {
				"apple", "application", "appearance",
				"ball", "ballot", "balley", "boy",
				"cat", "cancel", "circle", "circulation", "circumference",
				"dog", "doggy", "dumble", "drum", "drama", "double"
			};
		
		// add words
		for(String word : words) {
			
			tree.add(word);
		}
		
		// tree.find("apple");
		
		// testing BFS
		/* System.out.println("-------------------BFS-------------------\n");
		
		for(String w : words) {
			System.out.print(w + " ");
		}
		System.out.println("\n");
		
		tree.printBFS(); */
		
		String findWords[] = {"apple", "appy", "bishop", "bye", "door", "dog", "doggy", "circle"};
		
		// exact find
		System.out.println();
		for(String w : findWords) {
			System.out.println(w + " is found: " + tree.find(w));
		}
		
		// find by prefix
		String findPrefix[] = {"app", "ba", "cc", "do", "dog", "bi", "cir"};
		
		System.out.println("");
		for(String prefix : findPrefix) {
			System.out.print(prefix + " prefix results: [");
			
			List<String> foundWords = tree.findByPrefix(prefix);
			for(String w : foundWords) {
				System.out.print(w + " ");
			}
			System.out.println("]");
		}
	}
}