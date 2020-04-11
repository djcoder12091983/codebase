package cp;

import java.io.*;
import java.util.*;

// https://www.hackerrank.com/challenges/find-the-nearest-clone/problem
public class FindNearestClone1 {

    int n;
    List<List<Integer>> graph;
    Map<Integer, Integer> colors2node = new HashMap<>();
    int[] colors;
    
    public FindNearestClone1(int n) {
        this.n = n;
        
        graph = new ArrayList<>(n + 1);
        for(int i = 0; i <= n; i++) {
            graph.add(new LinkedList<>()); // empty list
        }
        
        colors = new int[n + 1]; // node 2 colors mapping
    }
    
    void addedge(int u, int v) {
        // 1 based index
        List<Integer> c = graph.get(u);
        c.add(v);
        c = graph.get(v);
        c.add(u);
    }
    
    void addcolor(int v, int c) {
        colors[v] = c;
        colors2node.put(c, v);
    }
    
    int find(int c) {
        Integer v = colors2node.get(c); // node v to start
        
        // BFS domain class
        class BFS {
            int node;
            int level;
            
            BFS(int node, int level) {
                this.node = node;
                this.level = level;
            }
        }
        
        boolean visited[] = new boolean[n + 1];
        Queue<BFS> q = new LinkedList<>(); // BFS queue
        q.add(new BFS(v, 0));
        
        int min = Integer.MAX_VALUE;
        while(!q.isEmpty()) {
            BFS node = q.poll();
            v = node.node;
            int l = node.level;
            if(visited[v]) {
                // nothing to do
                continue;
            }
            
            // mark as visited
            visited[v] = true;
            
            // next nodes to traverse
            for(int next : graph.get(v)) {
                if(!visited[next]) {
                    if(colors[next] == c) {
                        // target color
                        if(l + 1 < min) {
                            // new min
                            min = l + 1;
                        }
                        q.add(new BFS(next, 0)); // reset level 
                    } else {
                        // simply add to queue
                        q.add(new BFS(next, 1 + l)); // reset level
                    }
                }
            }
        }
        
        if(min == Integer.MAX_VALUE) {
            // not possible
            return -1;
        }
        
        return min;
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(tokens.nextToken());
        int m = Integer.parseInt(tokens.nextToken());
        
        FindNearestClone1 p = new FindNearestClone1(n);
        
        int edegs[][] = new int[m][2];
        for(int i = 0; i < m; i++) {
            // add edge
            tokens = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(tokens.nextToken());
            int v = Integer.parseInt(tokens.nextToken());
            edegs[i][0] = u;
            edegs[i][1] = v;
        }
        
        // add colors
        tokens = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n ; i++) {
            int c = Integer.parseInt(tokens.nextToken());
            p.addcolor(i + 1, c);
        }

        int c = Integer.parseInt(reader.readLine());
        Integer v = p.colors2node.get(c);
        if(v == null) {
            // not possible
            System.out.println("-1");
        } else {
            // add edges
            for(int i = 0; i < m; i++) {
                p.addedge(edegs[i][0], edegs[i][1]);
            }
            // result
            System.out.println(p.find(c));
        }
        
        reader.close();
    }
}