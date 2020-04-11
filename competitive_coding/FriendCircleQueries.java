package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/friend-circle-queries/problem
public class FriendCircleQueries {
    
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
    
    Map<Integer, Node> nodes = new HashMap<>();
    int max = Integer.MIN_VALUE;
    
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
        
        // System.out.println("T: " + xroot.size);
        
        if(max < xroot.size) {
            // new max
            max = xroot.size;
        }
    }
    
    // initially all are disjoint
    void add(int x) {
        if(!nodes.containsKey(x)) {
            // not exists
            Node xnode = new Node(x);
            nodes.put(x, xnode); // map to find node by x
            
            if(max < 1) {
                max = 1;
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        FriendCircleQueries fq = new FriendCircleQueries();
        
        int t = Integer.parseInt(reader.readLine());
        while(--t >= 0) {
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokens.nextToken());
            int y = Integer.parseInt(tokens.nextToken());
            
            // incremental friends adding
            fq.add(x);
            fq.add(y);
            
            fq.union(x, y);
            System.out.println(fq.max);
        }
        
        reader.close();
    }
}