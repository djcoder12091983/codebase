package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/playing-with-numbers/problem
public class AbsoluteElementSums {
    
    // positive and negative number tracking
    List<Integer> negatives = new ArrayList<>();
    List<Integer> positives = new ArrayList<>();
    
    int positivePrefixSum[];
    int negativePrefixSum[];
    
    long sum;
    long X = 0; // for each query add x to X
    int l1, l2;
    
    // add numbers
    void add(int a) {
        if(a >= 0) {
            // positive
            positives.add(a);
        } else {
            // negative
            negatives.add(a);
        }
    }
    
    // sort them and do prefix sum
    void pre() {
        l1 = positives.size();
        l2 = negatives.size();
        
        positivePrefixSum = new int[l1 + 1];
        negativePrefixSum = new int[l2 + 1];
        
        // sort to do BS
        Collections.sort(positives);
        Collections.sort(negatives);
        
        // prefix sums
        positivePrefixSum[0] = 0;
        for(int i = 0; i < l1; i++) {
            positivePrefixSum[i + 1] = positivePrefixSum[i] + positives.get(i);
        }
        
        negativePrefixSum[0] = 0;
        for(int i = 0; i < l2; i++) {
            negativePrefixSum[i + 1] = negativePrefixSum[i] + negatives.get(i);
        }
        
        // initial sum
        sum = positivePrefixSum[l1] + Math.abs(negativePrefixSum[l2]);
    }
    
    // BS(binary search) floor
    // list is array-list
    static int BS(List<Integer> list, long x) {
        int l = list.size();
        if(l == 0) {
            return -1;
        }
        int start = 0;
        int end = l - 1;
        while(start < end) {
            int mid = (start + end) / 2;
            int a = list.get(mid);
            if(a == x) {
                return mid;
            } else if(a < x) {
                // move right
                start = mid + 1;
            } else {
                // move left
                end = mid - 1;
            }
        }
        if(list.get(start) <= x) {
            return start;
        } else {
            return start - 1;
        }
    }
    
    // for each query
    long query(int x) {
        if(x == 0) {
            // previous sum
            return sum;
        }
        
        long res = 0;
        X += x; // total X
        if(X > 0) {
            // straight way add positive sum
            res += X * l1 + positivePrefixSum[l1];
            // look floor into negative prefix sum
            int p = BS(negatives, -X);
            res += Math.abs(X * (p + 1) + negativePrefixSum[p + 1]); // negative part
            // positive part (don't need to absolute)
            res += X * (l2 - p - 1) + (negativePrefixSum[l2] - negativePrefixSum[p + 1]);
        } else {
            // straight way add negative sum
            res += Math.abs((X * l2) + negativePrefixSum[l2]);
            // look floor into positive prefix sum
            int p = BS(positives, -X);
            res += Math.abs(X * (p + 1) + positivePrefixSum[p + 1]); // negative part
            // positive part (don't need to absolute)
            res += X * (l1 - p - 1) + (positivePrefixSum[l1] - positivePrefixSum[p + 1]);
        }
        
        sum = res; // saves previous sum        
        return res;
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        AbsoluteElementSums p = new AbsoluteElementSums();
        
        int n = Integer.parseInt(reader.readLine());
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        while(--n >= 0) {
            int a = Integer.parseInt(tokens.nextToken());
            p.add(a);
        }
        // set up
        p.pre();
        
        // for each query
        n = Integer.parseInt(reader.readLine());
        tokens = new StringTokenizer(reader.readLine());
        
        StringBuilder res = new StringBuilder();
        String nl = System.getProperty("line.separator");
        
        while(--n >= 0) {
            int x = Integer.parseInt(tokens.nextToken());
            res.append(p.query(x));
            res.append(nl);
        }
        // final result
        System.out.println(res);
        
        reader.close();
    }
}