// sum subset problem using DP
// works for positive number
import java.util.*;

public class SumSubsetProblem {

	int[] input;
	
	SumSubsetProblem(int[] input) {
		this.input = input;
	}
	
	class InternalNode {
		
		int value;
		int usedValue;
		int index;
		boolean cache = false;
		
		InternalNode parentNode = null;
		
		// used value list
		List<Integer> usedValues = new LinkedList<Integer>();
		
		void addUsedValue(int v) {
			if(parentNode != null) {
				usedValues.addAll(parentNode.usedValues);
			}
			usedValues.add(v);
		}
		
		InternalNode(int value) {
			this.value = value;
		}
		
		List<InternalNode> children = new LinkedList<InternalNode>();
		
		void addChildLeft(InternalNode child) {
			children.add(child);
		}
		
		void addChildRight(InternalNode child) {
			// adds at last
			((LinkedList)children).addLast(child);
		}
	}
	
	class OutputList {
		List<Integer> output = new LinkedList<Integer>();
		
		OutputList(List<Integer> output) {
			this.output.addAll(output);
		}
		
		void add(int data) {
			output.add(data);
		}
		
		void print() {
			for(int v : output) {
				System.out.print(v + " ");
			}
		}
	}
	
	List<OutputList> findPossibleSubsets(int s) {
	
		List<OutputList> output = new LinkedList<OutputList>();
	
		// my custom class
		// this list avoids un-necessary branching
		int[] sort = new MergeSort(input).sort();
		
		// Map<Integer, InternalNode> exploredSubTree = new HashMap<Integer, InternalNode>();
	
		InternalNode root = new InternalNode(s);
		root.index = -1;
		
		Stack<InternalNode> recursiveStack = new Stack<InternalNode>();
		// root
		recursiveStack.push(root);
		
		// create combinations of number
		// use DP to avoid explore same sub-tree
		while(!recursiveStack.isEmpty()) {
			
			InternalNode node = recursiveStack.pop();
			
			int v = node.value;
			int idx = node.index;
			int l = sort.length;
			
			// System.out.println("Node value: " + v);
			
			if(v == 0) {
				// treminating condition
				// here is my one of found solutions
				output.add(new OutputList(node.usedValues));
				continue;
			}
			
			// check in explorer map
			// InternalNode explored = exploredSubTree.get(v);
			// let's explore all subtrees
			// TODO reuse explored sub-tree
			/*if(explored != null) {
				// re-use explorer sub-tree
				node.cache = true;
				// no need to re-explore
				continue;
			} else {		
				// save exlored sub-tree
				exploredSubTree.put(v, node);
				
				if(v == 0) {
					// treminating condition
					// here is my one of found solutions
					output.add(new OutputList(node.usedValues));
					continue;
				}
			}*/
			
			// possible branches
			// List<InternalNode> branches = new LinkedList<InternalNode>();
			
			// find start index from right side
			int left = 0;
			int right = l - 1;
			int rightStartIdx = right;
			while(left < right) {
			
				/*if(left == right) {
					// single element
					rightStartIdx = left - 1;
					break;
				}*/
				
				if(right - left == 1) {
					// exactly two elements
					if(right == v) {
						rightStartIdx  = right;
					} else if(left == v) {
						rightStartIdx = left;
					} else if(left < v && right > v) {
						rightStartIdx = right - 1;
					} else {
						// outside
						rightStartIdx = right;
					}
					break;
				}
				
				// int m = new Double(left + Math.ceil((right - left) / 2.0)).intValue();
				int m = left + ((right - left) / 2);
				int midVal = sort[m];
				
				if(v == midVal) {
					rightStartIdx = m;
					break;
				} else if(v < midVal) {
					// left side partition
					right = m;
				} else {
					// right side partition
					left = m;
				}
				
				// System.out.println("left: " + left + ", right: " + right);
			}
			
			// System.out.println("rightStartIdx of " + v + ": " + rightStartIdx + ". Value: " + sort[rightStartIdx] + ", idx: " + idx);
			
			// branch creation
			for(int i = rightStartIdx; i > idx ; i--) {
			
				if(sort[i] > v) {
					// avoid unecessary branch creation
					break;
				}
				
				// create branches
				InternalNode child = new InternalNode(v - sort[i]);
				child.index = i;
				child.usedValue = sort[i];
				
				// adds child
				node.addChildRight(child);
				// parent node
				child.parentNode = node;
				
				// used values update
				child.addUsedValue(sort[i]);
				
				// push into stack
				recursiveStack.push(child);
			}
		}
		
		return output;
	}
	
	int[] findSubset(int s) {
    
        int l = input.length;
        
        boolean dp[][] = new boolean[l][s + 1];
        
        for(int i = 0; i < l; i++) {
            dp[i][0] = true;
        }
        
        for(int i = 0; i < l; i++) {
            
            for(int j = 1; j <= s; j++) {
                
                if(i > 0) {
                    int diff = j - input[i];
                    
                    if(dp[i - 1][j] == true) {
                        dp[i][j] = true;
                    } else if(diff >= 0) {
                        dp[i][j] = dp[i - 1][diff];
                        // System.out.println(input[i] + " selected");
                    } else {
                        dp[i][j] = false;
                    }
                
                } else {
                    dp[i][j] = input[i] == j;
                    
                    /*if(dp[i][j] == true) {
                        System.out.println(input[i] + " selected");
                    }*/
                }
            }
        }
        
        // for testing print DP matrix
        for(int i = 0; i < l; i++) {
            for(int j = 0; j <= s; j++) {
                System.out.print((dp[i][j] == true ? 'T' : 'F') + " ");
            }
            
            System.out.println();
        }
        
        // blank output
        // TODO
        int output[] = {};
        
        return output;
    }
	
	public static void main(String a[]) {
	
		int input[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		// , 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		
		SumSubsetProblem p = new SumSubsetProblem(input);
		List<OutputList> output = p.findPossibleSubsets(12);
		
		// print output
		System.out.println("----------------Possible outputs---------------\n");
		for(OutputList outputList : output) {
			// print each solution
			outputList.print();
			
			System.out.println();
		}
		
		// System.out.println(Math.ceil(1/2.0));
		
		// int[] output = p.findSubset(12);
		
		/* int l = output.length;
		System.out.println("------subset solution-------");
		for(int i = 0; i < l; i++) {
			if(output[i] == -1) {
				if(i == 0) {
					System.out.println("No solution available");
				}
				break;
			}
			
			System.out.print(output[i] + " ");
		}*/
		
		// input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // p = new SumSubsetProblem(input);
        
        p.findSubset(100);
	}
}