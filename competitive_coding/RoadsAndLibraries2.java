package cp;

import java.io.*;
import java.util.*;

// https://www.hackerrank.com/challenges/torque-and-development/problem
public class RoadsAndLibraries2 {
    
    int V;
    LinkedList<Integer>[] adjListArray;
      
    // constructor
    RoadsAndLibraries2(int V) {
        this.V = V;
        adjListArray = new LinkedList[V];
  
        for(int i = 0; i < V ; i++){
            adjListArray[i] = new LinkedList<Integer>();
        } 
    } 
      
    // Adds an edge to an undirected graph 
    void addEdge( int src, int dest) {
        // 0 based indexing
        adjListArray[--src].add(--dest);
        adjListArray[dest].add(src); 
    }
    
    // temporary counter
    int t = 0;
    
    void DFSUtil(int v, boolean[] visited) {
        // Mark the current node as visited and print it
        if(visited[v]) {
            return;
        }
        
        visited[v] = true;
        t++;
        
        // Recur for all the vertices
        // adjacent to this vertex
        for (int x : adjListArray[v]) {
            if(!visited[x]) {
                DFSUtil(x,visited);
            }
        }
  
    }

    List<Integer> connectedComponents() {
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];
        
        List<Integer> components = new ArrayList<>();
        for(int v = 0; v < V; ++v) {
            if(!visited[v]) {
                DFSUtil(v,visited);
                
                // reset
                components.add(t);
                t = 0;
            }
        }
        
        return components;
    }
    
    // solution
    long roadsAndLibraries(long clib, long croad) {
        long c = 0; // cost
        
        List<Integer> components = connectedComponents();
        for(int v : components) {
            if(clib <= croad) {
                // then put library in all city instead of repairing roads
                // and putting one library
                c += clib * v;
            } else {
                // otherwise repair roads (v - 1) and put one library
                c += croad * (v - 1) + clib;
            }
        }
        
        return c;
     }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int q = Integer.parseInt(reader.readLine());
        while(--q >= 0) {
            // for each query
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(tokens.nextToken());
            int m = Integer.parseInt(tokens.nextToken());
            long clib = Long.parseLong(tokens.nextToken());
            long croad = Long.parseLong(tokens.nextToken());
            
            RoadsAndLibraries2 gt = new RoadsAndLibraries2(n);
            while(--m >= 0) {
                // add city
                tokens = new StringTokenizer(reader.readLine());
                int u = Integer.parseInt(tokens.nextToken());
                int v = Integer.parseInt(tokens.nextToken());
                gt.addEdge(u, v);
            }
            
            System.out.println(gt.roadsAndLibraries(clib, croad));
        }
    
        reader.close();
    }
}