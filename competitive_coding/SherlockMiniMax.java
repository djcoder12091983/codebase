package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/sherlock-and-minimax/problem
public class SherlockMiniMax {
    
    static int sherlockAndMinimax(int[] a, int p, int q) {
        int max = 0;
        int res = p;
        
        // sort array
        Arrays.sort(a);
        
        // now try to find maximum difference possible between two numbers
        // if p/q exists in between two numbers
        // suppose  p = 5, q = 30
        // and a = [10, 15, 20, 25]
        // for 5-9 before 10 maximum absolute difference can be 5
        // for 11-14 between 10,15 12 and 13 can cause maximum difference
        
        int l = a.length;
        
        // left element
        int left = a[0];
        if(p < left) {
            int t = left - p;
            if(t > max) {
                // new max
                max = t;
                res = p;
            }
        }
        
        // for every pair of elements (in between)
        for(int i = 0; i < l - 1; i++) {
            int a1 = a[i];
            int a2 = a[i + 1];
            
            int mid = Double.valueOf(Math.ceil((a1 + a2) / 2.0)).intValue();
            
            int t = -1;
            int t1 = res;
            if(p <= mid) {
                if(q >= mid) {
                    t = a2 - mid;
                    int d = a2 - a1 - 1;
                    if(d % 2 == 0) {
                        t1 = mid - 1;
                    } else {
                        t1 = mid;
                    }
                } else if(q > a1) {
                    t = q - a1;
                    t1 = q;
                }
            } else if(a2 > p) {
                t = a2 - p;
                t1 = p;
            }
            
            if(t > max) {
                // new max
                max = t;
                res = t1;
            }
        }
        
        // right element
        int right = a[l - 1];
        if(right < q) {
            int t = q - right;
            if(t > max) {
                // new max
                max = t;
                res = q;
            }
        }
        
        return res;
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(reader.readLine());
        
        int a[] = new int[n];
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(tokens.nextToken());
        }
        
        tokens = new StringTokenizer(reader.readLine());
        int p = Integer.parseInt(tokens.nextToken());
        int q = Integer.parseInt(tokens.nextToken());
        
        System.out.println(sherlockAndMinimax(a, p, q));
        
        reader.close();
    }
}