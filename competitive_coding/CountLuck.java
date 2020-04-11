package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/count-luck/problem
public class CountLuck {
    
    char[][] matrix;
    int sx, sy;
    int ex, ey;
    int n, m;
    
    public CountLuck(String input[], int n, int m) {
        this.n = n;
        this.m = m;
        
        matrix = new char[n][m];
        
        for(int i = 0; i < n; i++) {
            String s = input[i];
            for(int j = 0; j < m; j++) {
                char ch = s.charAt(j);
                matrix[i][j] = ch;
                
                if(ch == 'M') {
                    // found source
                    sx = i;
                    sy = j;
                } else if(ch == '*') {
                    // found destination
                    ex = i;
                    ey = j;
                }
            }
        }
    }
    
    // DFS
    // return total cost and whether destination reached or not
    int[] dfs(int x, int y) {
        //System.out.println(x + " " + y);
        matrix[x][y] = 'V'; // mark as visited
        if(ex == x && ey == y) {
            // destination reached
            return new int[]{0, 1};
        }
        
        // get options and choose one recursively
        int t = 0;
        int c = 0;
        int tp[][] = new int[][]{
            {x - 1, y}, // left
            {x + 1, y}, // right
            {x, y - 1}, // up
            {x, y + 1} // down
        };
        
        // now choose one recursively
        boolean f = false;
        for(int i = 0; i < 4; i++) {
            int tx = tp[i][0];
            int ty = tp[i][1];
            
            if(tx >= 0 && tx < n && ty >= 0 && ty < m) {
                char ch = matrix[tx][ty];
                if(ch == '.' || ch == '*') {
                    // valid option
                    c++;
                    if(!f) {
                        // destination not found yet
                        int t1[] = dfs(tx, ty); // call recursively
                        if(t1[1] == 1) {
                            // destination found on this path
                            t += t1[0];
                            f = true;
                        }
                    }
                }
            }
        }
        
        // if multiple options then wand required
        if(c > 1) {
            t++;
        }
        
        return f ? new int[]{t, 1} : new int[]{t, 0};
    }
    
    boolean countLuck(int k) {
        int[] res = dfs(sx, sy);
        return k == res[0];
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int t = Integer.parseInt(reader.readLine());
        while(--t >= 0) {
            // for each query
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(tokens.nextToken());
            int m = Integer.parseInt(tokens.nextToken());

            String input[] = new String[n];
            for(int i = 0; i < n; i++) {
                input[i] = reader.readLine();
            }

            int k = Integer.parseInt(reader.readLine());
            
            CountLuck cl = new CountLuck(input, n, m);
            System.out.println(cl.countLuck(k) ? "Impressed" : "Oops!");
        }
        
        reader.close();
    }
}