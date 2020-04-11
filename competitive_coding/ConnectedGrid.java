package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid/problem
public class ConnectedGrid {
    
    static boolean visited[][];
    static boolean matrix[][];
    
    // temporary counter
    static int t = 0;
    
    static void dfs(int u, int v, int n, int m) {
        if(visited[u][v]) {
            // already visited
            return;
        }
        
        visited[u][v] = true;
        t++;
        
        // recursive for all connected cells
        int c[][] = new int[][]{
                {u + 1, v},
                {u, v + 1},
                {u + 1, v + 1},
                {u + 1, v - 1},
                {u - 1, v - 1},
                {u - 1, v + 1},
                {u - 1, v},
                {u, v - 1}
        };
        
        int l = c.length;
        for(int i = 0 ; i < l; i++) {
            u = c[i][0];
            v = c[i][1];
            
            // recursive call
            if(u >= 0 && u < n && v >= 0 && v < m && matrix[u][v] && !visited[u][v]) {
                // valid cell to traverse
                dfs(u, v, n, m);
            }
        }
    }
    
    // solution
    static int find(int n, int m) {
        // traverse all nodes
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(matrix[i][j] && !visited[i][j]) {
                    // start with this node(DFS)
                    dfs(i, j, n, m);
                    if(t > max) {
                        // new max
                        max = t;
                    }
                    // reset t
                    t = 0;
                }
            }
        }
        
        return max;
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(reader.readLine());
        int m = Integer.parseInt(reader.readLine());
        
        matrix = new boolean[n][m];
        visited = new boolean[n][m];
        
        for(int i = 0; i < n; i++) {
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            for(int j = 0; j < m; j++) {
                matrix[i][j] = tokens.nextToken().equals("1");
            }
        }
        
        // solution
        System.out.println(find(n, m));
        
        reader.close();
    }
}