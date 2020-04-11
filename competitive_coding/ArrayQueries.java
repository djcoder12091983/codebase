package hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ArrayQueries {
    
    List<Long> A;
    int rotations = 0; // rotations
    int N;
    
    public ArrayQueries(int N) {
        this.N = N;
        A = new ArrayList<>(N);
    }
    
    // add to list
    public void add(long v) {
        A.add(v);
    }
    
    // find actual index (in case of after rotations) using modulo arithmetic
    // note: index is 0 based
    int findIndex(int idx) {
        int t = idx - rotations;
        if(t >= 0) {
            return t % N;
        } else {
            // negative case
            return N - Math.abs(t) % N;
        }
    }

    // query serve
    public void query(String query) {
        StringTokenizer tokens = new StringTokenizer(query);
        String op = tokens.nextToken();
        if(op.equals("Left")) {
            // left rotation
            rotations--;
        } else if(op.equals("Right")) {
            // right rotation
            rotations++;
        } else if(op.equals("Update")) {
            // update value @ index
            int idx = Integer.parseInt(tokens.nextToken());
            idx = findIndex(idx - 1);
            long v = Long.parseLong(tokens.nextToken());
            A.set(idx, v);
        } else if(op.equals("Increment")) {
            // update by 1 @ index
            int idx = Integer.parseInt(tokens.nextToken());
            idx = findIndex(idx - 1);
            long v = A.get(idx);
            A.set(idx, ++v);
        } else if(op.equals("?")) {
            // find @ index
            int idx = Integer.parseInt(tokens.nextToken());
            System.out.println(A.get(findIndex(idx - 1)));
        }
    }
    
    public static void main(String args[]) throws Exception {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	
    	int N = Integer.parseInt(reader.readLine());
    	ArrayQueries aq = new ArrayQueries(N);
    	
    	StringTokenizer tokens = new StringTokenizer(reader.readLine());
    	for(int i = 0; i < N; i++) {
    		long v = Long.parseLong(tokens.nextToken());
    		aq.add(v);
    	}
    	
    	// query
    	int Q = Integer.parseInt(reader.readLine());
    	while(--Q >= 0) {
    		aq.query(reader.readLine());
    	}
    	
    	reader.close();
    }
}