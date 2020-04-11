package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://www.hackerrank.com/challenges/special-palindrome-again/problem
public class SpecialStringAgain {
    
    class Data {
        int start;
        int l;
        
        Data(int start, int l) {
            this.start = start;
            this.l = l;
        }
        
        @Override
        public String toString() {
            // for testing
            return "data@{" + start + ", " + l + "}";
        }
    }
    
    Map<Character, List<Data>> map = new HashMap<>();
    
    // count special strings
    long count(String s) {
        
        long c = 0;
        
        int l = s.length();
        int start = 0;
        int c1 = 1;
        for(int i = 1; i < l; i++) {
            char ch1 = s.charAt(i - 1);
            char ch2 = s.charAt(i);
            
            if(ch1 != ch2) {
                // sequence break
                List<Data> list = map.get(ch1);
                if(list == null) {
                    list = new ArrayList<>(2);
                    map.put(ch1, list);
                }
                
                list.add(new Data(start, c1));
                
                // reset
                start = i;
                c1 = 1;
            } else {
                // sequence continue
                c1++;
            }
        }
        
        // last entry
        char ch1 = s.charAt(l - 1);
        List<Data> list = map.get(ch1);
        if(list == null) {
            list = new ArrayList<>(2);
            map.put(ch1, list);
        }
        
        list.add(new Data(start, c1));
        
        // now count
        for(char k : map.keySet()) {
            List<Data> dlist = map.get(k);
            
            int l1 = dlist.size();
            long t = dlist.get(0).l;
            c += t * (t + 1) / 2; // individual count of first entry
            // for each list check validity whether special strings formation possible
            for(int i = 1; i < l1; i++) {
                Data d1 = dlist.get(i - 1);
                Data d2 = dlist.get(i);
                
                if(d1.start + d1.l + 1 == d2.start) {
                    // special strings formation possible
                    c += Math.min(d1.l, d2.l);
                }
                
                t = d2.l;
                c += t * (t + 1) / 2;// individual count
            }
        }
        
        return c;
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        reader.readLine(); // skip line
        String s = reader.readLine();
        // result
        SpecialStringAgain p = new SpecialStringAgain();
        System.out.println(p.count(s));
        
        reader.close();
    }
}