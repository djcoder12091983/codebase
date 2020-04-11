package hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class PopularShops {
    
    // solution
    static void solve(int A[], int N) {
        // data along with index
    	class Data {
        	int data, index;
        	
        	Data(int data, int index) {
        		this.data = data;
        		this.index = index;
        	}
        }
    	
    	Queue<Data> cqueue = new PriorityQueue<>(new Comparator<Data>() {
    		// reverse order to get most popular shops
    		public int compare(Data d1, Data d2) {
    			int c = Integer.valueOf(d2.data).compareTo(Integer.valueOf(d1.data));
    			if(c == 0) {
    				// consider index
    				return Integer.valueOf(d1.index).compareTo(Integer.valueOf(d2.index));
    			} else {
    				return c;
    			}
    		};
		});
    	
    	cqueue.add(new Data(A[0], 1));
        
        for(int  i = 1; i < N; i++) {
            A[i] += A[i - 1];
            cqueue.add(new Data(A[i], i + 1));
        }
        
        // 3 most popular shops
        StringBuilder output = new StringBuilder();
        output.append(cqueue.poll().index);
        
        for(int i = 1; i < 3; i++) {
        	output.insert(0, " ").insert(0, cqueue.poll().index);
        }
        
        System.out.println(output);
    }
    
    public static void main(String args[]) throws Exception {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	
    	int T = Integer.parseInt(reader.readLine());
    	while(--T >= 0) {
    	    StringTokenizer tokens = new StringTokenizer(reader.readLine());
    	    int N = Integer.parseInt(tokens.nextToken());
    	    int M = Integer.parseInt(tokens.nextToken());
    	    
    	    int A[] = new int[N + 1];
    	    // default fill
    	    for(int i = 0; i <= N; i++) {
    	        A[i] = 0;
    	    }
    	    
    	    for(int i = 0; i < M; i++) {
    	        tokens = new StringTokenizer(reader.readLine());
    	        int l = Integer.parseInt(tokens.nextToken());
    	        int r = Integer.parseInt(tokens.nextToken());
    	        // range update
    	        A[l - 1] += 1;
    	        A[r] -= 1;
    	    }
    	    
    	    // solve
    	    solve(A, N);
    	}
    	
    	reader.close();
    }
}