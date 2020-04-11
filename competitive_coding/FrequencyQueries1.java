package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/frequency-queries/problem
public class FrequencyQueries1 {
    
    Map<Integer, Integer> f1 = new HashMap<>(); // number 2 frequency table
    Map<Integer, Set<Integer>> f2 = new HashMap<>(); // frequency count mapping table
    
    StringBuilder output = new StringBuilder();
    static final String NEW_LINE = System.getProperty("line.separator");
    
    // Complete the freqQuery function below.
    void freqQuery(int op, int a) {
        
        if(op == 1) {
            // insert
            int c;
            if(f1.containsKey(a)) {
                c = f1.get(a);
                // remove entry with old c in f2 table
                Set<Integer> inversef = f2.get(c); // inverse frequency table
                inversef.remove(a); // remove entry a with old count
                if(inversef.isEmpty()) {
                    // remove whole entry with count c
                    f2.remove(c);
                }
                c++; // new c
            } else {
                // first entry, so no need to update f2 table
                c = 1;
            }
            f1.put(a, c);

            // now update f2 table
            if(f2.containsKey(c)) {
                f2.get(c).add(a); // entry a with new count
            } else {
                // first time
                Set<Integer> inversef = new HashSet<>(2);
                inversef.add(a); // new entry a
                f2.put(c, inversef);
            }
        } else if(op == 2) {
            // delete
            if(f1.containsKey(a)) {
                // valid a
                int c = f1.get(a); // old c
                Set<Integer> inversef = f2.get(c); // inverse frequency table
                inversef.remove(a); // remove entry a with old count
                if(inversef.isEmpty()) {
                    // remove whole entry with count c
                    f2.remove(c);
                }
                
                if(c == 1) {
                    // remove from f1 table
                    f1.remove(a);
                } else {
                    // update f1 table
                    c--;
                    f1.put(a, c);
                    
                    // now update f2 table
                    if(f2.containsKey(c)) {
                        f2.get(c).add(a); // entry a with new count
                    } else {
                        // first time
                        inversef = new HashSet<>(2);
                        inversef.add(a); // new entry a
                        f2.put(c, inversef);
                    }
                }
            }
        } else if(op == 3) {
            // query
            if(f2.containsKey(a)) {
                // exists
                output.append(1);
                output.append(NEW_LINE);
            } else {
                // not exists
                output.append(0);
                output.append(NEW_LINE);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        FrequencyQueries1 fq = new FrequencyQueries1();
        
        int n = Integer.parseInt(reader.readLine());
        while(--n >= 0) {
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            int op = Integer.parseInt(tokens.nextToken());
            int a = Integer.parseInt(tokens.nextToken());
            
            fq.freqQuery(op, a); // query
        }
        
        // output
        System.out.println(fq.output.toString());
        
        reader.close();
    }
}
