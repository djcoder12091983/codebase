package hackerrank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/components-in-graph/problem
public class CG {

    Map<Integer, Node> nodes = new HashMap<>();
    Map<Integer, Integer> countf = new HashMap<>(); // count frequency by size

    // union/find
    
    class Node {
        int node;
        int size;
        Node parent;
        
        Node(int x) {
            this.node = x;
            size = 1;
            // initially points to self
            parent = this;
        }
    }

    CG(int n) {
        // individual nodes
        // initially all are disjoint
        int N = 2 * n;
        for(int i = 1; i <= N; i++) {
            Node inode = new Node(i);
            nodes.put(i, inode); // map to find node
        }

        countf.put(1, N);
    }
    
    // find
    Node find(int x) {
        Node node = nodes.get(x);
        while(node.parent != node) {
            node = node.parent;
        }

        // path compression
        Node node1 = nodes.get(x);
        while(node1.parent != node) {
            node1.parent = node; // cache
            node1 = node1.parent;
        }
        
        return node;
    }
    
    // union by size
    void union(int x, int y) {
        Node xroot = find(x);
        Node yroot = find(y);

        int s1 = xroot.size;
        int s2 = yroot.size;

        if(xroot == yroot) {
            // nothing to do, already connected
            return;
        }
        
        if(xroot.size < yroot.size) {
            // swap
            Node t = xroot;
            xroot = yroot;
            yroot = t;
        }
        
        // merge yroot into xroot
        yroot.parent = xroot;
        xroot.size += yroot.size;
 
        // remove old size frequency
        if(s1 == s2) {
        	countf.put(s1, countf.get(s1) - 2);
        	if(countf.get(s1) == 0) {
        		countf.remove(s1);
        	}
        } else {
        	countf.put(s1, countf.get(s1) - 1);
        	countf.put(s2, countf.get(s2) - 1);
        	if(countf.get(s1) == 0) {
        		countf.remove(s1);
        	}
        	if(countf.get(s2) == 0) {
        		countf.remove(s2);
        	}
        }

        // new size entry
        int s = xroot.size;
        if(countf.containsKey(s)) {
            countf.put(s, countf.get(s) + 1);
        } else {
            countf.put(s, 1);
        }
    }

    // gets smallest/largest components
    int[] minmax() {
        countf.remove(1); // remove single nodes
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int s : countf.keySet()) {
            if(s > max) {
                max = s;
            }
            if(s < min) {
                min = s;
            }
        }

        return new int[]{min, max};
    }

    public static void main(String args[]) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());
        CG cg = new CG(n);
        // add edge
        for(int i = 1; i <= n; i++) {
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            int g = Integer.parseInt(tokens.nextToken());
            int b = Integer.parseInt(tokens.nextToken());
            cg.union(g, b);
        }

        // result
        int res[] = cg.minmax();
        System.out.println(res[0] + " " + res[1]);

        reader.close();
    }
}