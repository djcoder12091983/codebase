package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/find-maximum-index-product/problem
public class MaximumIndexProduct {

    static long solve(int[] arr) {
        // data along with index
        class Data {
            int data;
            int idx;

            Data(int data, int idx) {
                this.data = data;
                this.idx = idx;
            }
        }

        int l = arr.length;
        // index
        long left[] = new long[l];
        long right[] = new long[l];
        
        // using stack, from left to right get right index
        Stack<Data> s = new Stack<>();
        s.push(new Data(arr[0], 0));

        for(int i = 1; i < l; i++) {
            if(arr[i] > s.peek().data) {
                // check data in stack till larger one found
                while(!s.isEmpty() && arr[i] > s.peek().data) {
                    // right index update
                    Data top = s.pop();
                    right[top.idx] = i + 1;
                }
            }
            // push
            s.push(new Data(arr[i], i));
        }

        while(!s.isEmpty()) {
            // no right index found
            Data top = s.pop();
            right[top.idx] = 0;
        }

        // from right to left get left index
        s.push(new Data(arr[l - 1], l - 1));

        for(int i = l - 2; i >= 0; i--) {
            if(arr[i] > s.peek().data) {
                // check data in stack till larger one found
                while(!s.isEmpty() && arr[i] > s.peek().data) {
                    // left index update
                    Data top = s.pop();
                    left[top.idx] = i + 1;
                }
            }
            // push
            s.push(new Data(arr[i], i));
        }

        while(!s.isEmpty()) {
            // no left index found
            Data top = s.pop();
            left[top.idx] = 0;
        }

        // find max index product
        long max = Long.MIN_VALUE;
        for(int i = 0; i < l; i++) {
             long p = left[i] * right[i];
            if(p > max) {
                max = p;
            }
        }

        return max;
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(reader.readLine());
        int a[] = new int[n];
        
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(tokens.nextToken());
        }
        
        // result
        System.out.println(solve(a));
        
        reader.close();
    }
}