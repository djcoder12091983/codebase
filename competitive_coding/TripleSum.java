package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

// https://www.hackerrank.com/challenges/triple-sum/problem
public class TripleSum {
    
    // remove duplicate elements from each array and sort them
    static long[] removeduplicatesAndSort(long[] A) {
        Set<Long> set = new TreeSet<>();
        for(long a : A) {
            set.add(a);
        }
        long newa[] = new long[set.size()];
        int i = 0;
        for(long a : set) {
            newa[i++] = a;
        }
        return newa;
    }

    // Complete the triplets function below.
    static long triplets(long[] a, long[] b, long[] c) {
        
        // remove duplicate elements from each array and sort them
        a = removeduplicatesAndSort(a);
        b = removeduplicatesAndSort(b);
        c = removeduplicatesAndSort(c);
        
        long count = 0;

        // merge b and c so that for each element in b
        // it can be found how far it's from c elements
        int i = 0;
        int j = 0;
        int bl = b.length;
        int cl = c.length;
        // b prefix count
        // for each element in b how far it's from c elements
        long bprefixcount[] = new long[bl + 1];
        bprefixcount[0] = 0;
        long t = 0;
        int k = 1;
        while(i < bl && j < cl) {
            if(b[i] >= c[j]) {
                // how far from c elements
                j++;
                t++;
            } else {
                // for each b set prefix count
                i++;
                bprefixcount[k] = bprefixcount[k - 1] + t;
                k++;
            }
        }
        
        while(i < bl) {
            // for each b set prefix count
            i++;
            bprefixcount[k] = bprefixcount[k - 1] + t;
            k++;
        }
        
        // now merge a and b
        // reuse `bprefixcount`
        i = 0;
        j = 0;
        t = 0;
        int al = a.length;
        while(i < al && j < bl) {
            if(a[i] <= b[j]) {
                // how far from b elements
                i++;
                t++;
            } else {
                // now triplet count happens
                count += t * (bprefixcount[bl] - bprefixcount[j]);
                t = 0; // reset t
                j++;
            }
        }
        
        // last count
        count += t * (bprefixcount[bl] - bprefixcount[j]);
        
        return count;
    }
    
    public static void main(String args[]) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int al = Integer.parseInt(tokens.nextToken());
        int bl = Integer.parseInt(tokens.nextToken());
        int cl = Integer.parseInt(tokens.nextToken());

        long a[] = new long[al];
        long b[] = new long[bl];
        long c[] = new long[cl];
        // A
        int i = 0;
        tokens = new StringTokenizer(reader.readLine());
        while(i < al) {
            a[i++] = Long.parseLong(tokens.nextToken());
        }
        // B
        i = 0;
        tokens = new StringTokenizer(reader.readLine());
        while(i < bl) {
            b[i++] = Long.parseLong(tokens.nextToken());
        }
        // C
        i = 0;
        tokens = new StringTokenizer(reader.readLine());
        while(i < cl) {
            c[i++] = Long.parseLong(tokens.nextToken());
        }

        // solution
        System.out.println(triplets(a, b, c));

        reader.close();
    }
}