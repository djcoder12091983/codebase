package cp;

// https://www.hackerrank.com/challenges/cut-the-tree/problem
import java.io.*;
import java.util.*;

public class CutTree1 {
    
    int n;
    // adjacency list
    Map<Integer, List<Integer>> tree = new HashMap<>();
    int weights[]; // node weights
    int totalw = 0; // total weights
    
    public CutTree1(int n) {
        this.n = n;
        weights = new int[n];
    }
    
    void addedge(int n1, int n2) {
        // add edge both way
        List<Integer> list = tree.get(n1);
        if(list == null) {
            list = new ArrayList<>(2);
            tree.put(n1, list);
        }
        list.add(n2);
        
        list = tree.get(n2);
        if(list == null) {
            list = new ArrayList<>(2);
            tree.put(n2, list);
        }
        list.add(n1);
    }
    
    void addweight(int i, int weight) {
        // add weight
        weights[i] = weight;
        totalw += weight;
    }
    
    // minimum difference after cut edge
    int mindiff() {
        int mindiff = Integer.MAX_VALUE;
        
        // during DFS node information
        class DFS {
            int node;
            int nextnode = 0; // next node to visit
            int totalw = 0; // total weight
            DFS parent; // parent node
            
            DFS(int node, DFS parent) {
                this.parent = parent;
                this.node = node;
                this.totalw += weights[node - 1]; // node weight itself
            }
        }
        
        // calculate weights DFS using stack
        Stack<DFS> stack = new Stack<>();
        stack.push(new DFS(1, null)); // start with 1
        
        boolean visited[] = new boolean[n];
        for(int i = 0; i < n; i++) {
            // initially marked as false
            visited[i] = false;
        }
        
        while(!stack.isEmpty()) {
            // until stack is empty
            DFS dfsnode = stack.peek();
            int node = dfsnode.node;
            
            // marked as visited
            visited[node - 1] = true;
            
            int nextnode = dfsnode.nextnode;
            List<Integer> list = tree.get(node);
            if(nextnode < list.size()) {
                // valid next node index
                nextnode = list.get(nextnode);
                if(!visited[nextnode - 1]) {
                    // not visited yet
                    stack.push(new DFS(nextnode, dfsnode));
                }
                // move to next-index
                dfsnode.nextnode++;
            } else {
                // all adjacent nodes visited
                // remove from stack
                stack.pop();
                
                DFS parent = dfsnode.parent;
                if(parent != null) {
                    // not root node
                    // add total weights
                    parent.totalw += dfsnode.totalw;
                }
                
                if(node != 1) {
                    // and try to find minimum difference possible or not
                    int w1 = dfsnode.totalw;
                    int w2 = totalw - w1;
                    
                    int diff = Math.abs(w1 - w2);
                    if(diff < mindiff) {
                        // new minimum difference
                        mindiff = diff;
                    }
                }
            }
        }
        
        return mindiff;
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(reader.readLine());
        CutTree1 ct = new CutTree1(n);
        
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        // add weights
        for(int i = 0; i < n; i++) {
            ct.addweight(i, Integer.parseInt(tokens.nextToken()));
        }
        
        // add edges
        for(int i = 0; i < n - 1; i++) {
            tokens = new StringTokenizer(reader.readLine());
            int n1 = Integer.parseInt(tokens.nextToken());
            int n2 = Integer.parseInt(tokens.nextToken());
            ct.addedge(n1, n2);
        }
        
        System.out.println(ct.mindiff());
        
        reader.close();
    }
}