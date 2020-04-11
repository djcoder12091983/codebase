package hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MinAndXOROr {
	
	final static String NEW_LINE = System.getProperty("line.separator");
	
	// note: bit wise prefix tree based approach
	
	class PrefixNode {
		boolean digit;
		int count = 0; // subtree count inclusive
		PrefixNode left; // left is 0
		PrefixNode right; // right is 1
		
		PrefixNode(boolean digit) {
			this.digit = digit;
		}
	}
	
	public MinAndXOROr(int n) {
		this.n = n;
		A = new ArrayList<Integer>(n);
	}
	
	int n;
	PrefixNode root = new PrefixNode(false);
	List<Integer> A;
	
	// build prefix tree for each number
	void add(int x) {
		A.add(x);
		
		PrefixNode node = root; // starts with root
		node.count++;
		
		// 32 bit positive integer
		for(int i = 30; i >= 0; i--) {
			if((x & (1 << i)) == 0) {
				// reset bit
				if(node.left == null) {
					node.left = new PrefixNode(false);
				}
				node = node.left;
			} else {
				// set bit
				if(node.right == null) {
					node.right = new PrefixNode(true);
				}
				node = node.right;
			}
			node.count++;
		}
	}
	
	// solution
	int solve() {
		// note: try to find same bit then XOR result will be minimized
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < n; i++) {
			int x = A.get(i);
			// find possible minimum number
			
			PrefixNode node = root;
			// flag indicates whether diverted path followed or not
			// note: diverted path chosen to avoid same element 
			boolean f = false;
			int choose = 0;
			for(int j = 30; j >= 0; j--) {
				if((x & (1 << j)) == 0) {
					// reset bit
					if(node.left != null) {
						if(!f && node.left.count == 1) {
							if(node.right != null) {
								node = node.right;
								f = true;
							} else {
								node = node.left;
							}
						} else {
							node = node.left;
						}
					} else {
						node = node.right;
						f = true;
					}
				} else {
					// set bit
					if(node.right != null) {
						if(!f && node.right.count == 1) {
							if(node.left != null) {
								node = node.left;
								f = true;
							} else {
								node = node.right;
							}
						} else {
							node = node.right;
						}
					} else {
						node = node.left;
						f = true;
					}
				}
				// XOR computation
				choose += (node.digit ? 1 : 0) * (1 << j);
			}
			
			int xor = (x & choose) ^ (x | choose);
			if(xor < min) {
				// new min
				min = xor;
			}
		}		
		return min;
	}
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(reader.readLine());
		StringBuffer result = new StringBuffer();
    	while(--t >= 0) {
    		int n = Integer.parseInt(reader.readLine());
    		MinAndXOROr p = new MinAndXOROr(n);
    		
    		StringTokenizer tokens = new StringTokenizer(reader.readLine());
    		for(int i = 0; i < n; i++) {
    			p.add(Integer.parseInt(tokens.nextToken()));
    		}
    		
    		// solve
    		result.append(p.solve()).append(NEW_LINE);
    	}
    	System.out.println(result.toString());
		
		reader.close();
	}
}