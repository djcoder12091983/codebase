package hackerearth.hiring.challenges.date11022018.niyo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class TreeNode {
	char letter;
	Set<TreeNode> children;
	
	public TreeNode(char letter) {
		this.letter = letter;
		children = new HashSet<TreeNode>(4); // default size 4
	}
}

class WordTree {
	
	TreeNode root;
	List<TreeNode> nodes;
	List<Character> letters;
	int size;
	
	WordTree(int size) {
		this.size = size;
		nodes = new ArrayList<TreeNode>(size);
		for(int i = 0; i < size; i++) {
			nodes.add(null); // default NULLs
		}
		letters = new ArrayList<Character>(size);
	}
	
	void addLetter(char letter) {
		letters.add(letter);
	}
	
	void addEdge(int start, int end) {
		
		TreeNode parent = null;
		TreeNode child = null;
		
		int startIdx = start - 1;
		int endIdx = end - 1;
		char startLetter = letters.get(startIdx);
		char endLetter = letters.get(endIdx);
		
		TreeNode found = nodes.get(startIdx);
		if(found != null) {
			parent = found;
			child = new TreeNode(endLetter);
			nodes.set(endIdx, child);
		} else {
			found = nodes.get(endIdx);
			if(found != null) {
				parent = found;
				child = new TreeNode(startLetter);
				nodes.set(startIdx, child);
			}
		}
		
		if(parent == null) {
			// start->end
			parent = new TreeNode(startLetter);
			child = new TreeNode(endLetter);
			
			nodes.set(startIdx, parent);
			nodes.set(endIdx, child);
		}
		
		parent.children.add(child);
		
		if(root == null && (start == 1 || end == 1)) {
			root = parent;
		}
		
	}
	
	int find(int idx, String word) {
		
		int min = 0;
		int index = idx - 1;
		
		Map<Character, Integer> counter = new HashMap<Character, Integer>();
		Queue<TreeNode> nextNodes = new LinkedList<TreeNode>();
		
		// sub-tree @ idx
		TreeNode node = nodes.get(index);
		nextNodes.add(node);
		increment(node, counter);
		// BFS
		while(!nextNodes.isEmpty()) {
			
			TreeNode nextNode = nextNodes.poll();
			for(TreeNode child : nextNode.children) {
				increment(child, counter);
				nextNodes.add(child);
			}
		}
		
		// match with word
		int l = word.length();
		for(int i = 0; i < l; i++) {
			char letter = word.charAt(i);
			Integer count = counter.get(letter);
			if(count == null) {
				// no entry
				min++;
			} else {
				int newCount = count.intValue() - 1;
				if(newCount == 0) {
					counter.remove(letter); // remove
				} else {
					counter.put(letter, newCount); // decrement
				}
			}
		}
		
		return min;
	}
	
	void increment(TreeNode node, Map<Character, Integer> counter) {
		Integer count = counter.get(node.letter);
		if(count == null) {
			counter.put(node.letter, 1);
		} else {
			counter.put(node.letter, count.intValue() + 1);
		}
	}
}

public class WordsAndTrees {
	
	public static void main(String[] args) throws Exception {
		
		/*WordTree wt = new WordTree(8);
		// o v s l v p d i
		wt.addLetter('o');
		wt.addLetter('v');
		wt.addLetter('s');
		wt.addLetter('l');
		wt.addLetter('v');
		wt.addLetter('p');
		wt.addLetter('d');
		wt.addLetter('i');
		// edges
		wt.addEdge(1, 3);
		wt.addEdge(8, 3);
		wt.addEdge(4, 8);
		wt.addEdge(6, 1);
		wt.addEdge(5, 3);
		wt.addEdge(7, 6);
		wt.addEdge(2, 3);
		// find
		System.out.println(wt.find(7, "ifwrxl"));
		System.out.println(wt.find(4, "eyljywnm"));
		System.out.println(wt.find(3, "llvse"));*/
		
		Scanner s = new Scanner(System.in);
        int size = s.nextInt();
        WordTree wt = new WordTree(size);
        
        int tc = s.nextInt();
        
        for(int i = 0; i < size; i++) {
        	char letter = s.next().charAt(0);
        	wt.addLetter(letter);
        }
        
        for(int i = 0; i < size - 1; i++) {
        	int start = s.nextInt();
        	int end = s.nextInt();
        	
        	wt.addEdge(start, end);
        }
        
        // TC
        for(int i = 0; i < tc ; i++) {
        	int idx = s.nextInt();
        	String word = s.next();
        	System.out.println(wt.find(idx, word));
        }
        
        s.close();
	}

}