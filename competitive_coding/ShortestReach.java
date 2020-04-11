package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

// https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem
public class ShortestReach {
    
    List<List<Integer>> graph;
    int n;
    
    public ShortestReach(int n) {
        this.n = n;
        graph = new ArrayList<>(n + 1);
        for(int i = 0; i <= n; i++) {
            graph.add(new LinkedList<>());
        }
    }
    
    void addedge(int u, int v) {
        List<Integer> c = graph.get(u);
        c.add(v);
        c = graph.get(v);
        c.add(u);
    }
    
    // single source shortest path (positive edges)
    int[] path(int s) {
        
        class PQ {
            // distance to node mapping
            TreeMap<Integer, Set<Integer>> pq = new TreeMap<>();
            // node to distance mapping
            Map<Integer, Integer> mapping = new HashMap<>();
            
            // extract min
            int[] poll() {
                int d = pq.firstKey();
                Iterator<Integer> nodes = pq.get(d).iterator();
                int node = nodes.next();
                // remove node
                Set<Integer> nodes1 = pq.get(d);
                nodes1.remove(node); // remove from old distance
                if(nodes1.isEmpty()) {
                    // remove the whole entry
                    pq.remove(d);
                }
                
                mapping.remove(node);
                
                return new int[]{node, d};
            }
            
            // node exists
            boolean exists(int node) {
                return mapping.containsKey(node);
            }
            
            void update(int node, int d) {
                if(mapping.containsKey(node)) {
                    // check if new value is less than existing
                    int old = mapping.get(node);
                    if(d < old) {
                        // minimum distance
                        mapping.put(node, d);
                        // now update pq
                        Set<Integer> nodes = pq.get(old);
                        nodes.remove(node); // remove from old distance
                        if(nodes.isEmpty()) {
                            // remove the whole entry
                            pq.remove(old);
                        }
                        // update with new d
                        nodes = pq.get(d);
                        if(nodes == null) {
                            nodes = new HashSet<>(2);
                            pq.put(d, nodes);
                        }
                        nodes.add(node);
                    }
                } else {
                    // otherwise new entry
                    mapping.put(node, d);
                    Set<Integer> nodes = pq.get(d);
                    if(nodes == null) {
                        nodes = new HashSet<>(2);
                        pq.put(d, nodes);
                    }
                    nodes.add(node);
                }
            }
            
            boolean empty() {
                return pq.isEmpty();
            }
        }
        
        PQ pq = new PQ(); // priority queue to extract min
        
        int path[] = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            // initially not reachable except source
            if(i != s) {
                path[i] = Integer.MAX_VALUE;
                pq.update(i, path[i]); // update PQ
            } else {
                path[i] = 0; // source node
                pq.update(i, 0); // update PQ
            }
        }
        
        // BFS greedy algorithm (dijkstra)
        while(!pq.empty()) {
            int min[] = pq.poll();
            int node = min[0];
            int d = min[1];

            if(d == Integer.MAX_VALUE) {
                // disconnected node
                continue;
            }
            
            path[node] = d; // update distance for current node
            // now do BFS and greedy
            for(int c : graph.get(node)) {
                if(pq.exists(c)) {
                    // add existing node only
                    pq.update(c, d + 6); // each edge 6
                }
            }
        }
        
        return path;
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int t = Integer.parseInt(reader.readLine());
        while(--t >= 0) {
            // for each TC
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(tokens.nextToken());
            int m = Integer.parseInt(tokens.nextToken());
            
            ShortestReach p = new ShortestReach(n);
            while(--m >= 0) {
                // add edge
                tokens = new StringTokenizer(reader.readLine());
                int u = Integer.parseInt(tokens.nextToken());
                int v = Integer.parseInt(tokens.nextToken());
                p.addedge(u, v);
            }
            
            int s = Integer.parseInt(reader.readLine());
            // result
            int path[] = p.path(s);
            for(int i = 1; i <= n; i++) {
                if(i != s) {
                    System.out.print((path[i] == Integer.MAX_VALUE) ? -1 : path[i]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        
        reader.close();
    }
}