package cp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

// https://www.hackerrank.com/challenges/reverse-shuffle-merge/problem
public class ReverseShuffleMerge {

	// solution
    static String reverseShuffleMerge(String input) {
        
        int l = input.length();
        // incremental character frequency
        List<Map<Character, Integer>> icf = new ArrayList<>(l + 1);
        icf.add(new HashMap<Character, Integer>(2)); // empty map
        
        // positional index
        Map<Character, TreeSet<Integer>> positions = new HashMap<>();
        
        for(int i = 0; i < l; i++) {
            char ch = input.charAt(i);
            
            // copy and merge
            Map<Character, Integer> cf = new HashMap<>(2);
            Map<Character, Integer> pcf = icf.get(i); // previous
            for(char k : pcf.keySet()) {
                cf.put(k, pcf.get(k));
            }
            if(cf.containsKey(ch)) {
                cf.put(ch, cf.get(ch) + 1);
            } else {
                cf.put(ch, 1);
            }
            // add new cf
            icf.add(cf);
            
            // positions
            TreeSet<Integer> pi; // positional index
            if(positions.containsKey(ch)) {
                pi = positions.get(ch);
            } else {
                pi = new TreeSet<>();
                positions.put(ch, pi);
            }
            pi.add(i);
        }
        
        // tentative string character frequency map
        // half of total character frequency
        TreeMap<Character, Integer> tcf = new TreeMap<>();
        Map<Character, Integer> fcf = icf.get(l); // final cf
        for(char k : fcf.keySet()) {
            tcf.put(k, fcf.get(k) / 2); // half
        }
        
        // greedily look for each character and try to create lexicographically smallest A
        StringBuilder lmin = new StringBuilder();
        int lastp = l; // last position found
        while(!tcf.isEmpty()) {
            char suitable; // next suitable character
            Iterator<Character> i = tcf.keySet().iterator();
            // finds next suitable character
            // to fit lexicographically smallest A
            do {
                suitable = i.next();
                // check whether this character will be suitable or not
                // check by position
                TreeSet<Integer> pi = positions.get(suitable);
                Integer p = pi.floor(lastp - 1); // strictly less than last position
                boolean f = true;
                if(p != null) {
                    // now check whether 0 to p character frequency
                    // meets the requirement, remaining characters exist in that range
                    Map<Character, Integer> pf = icf.get(p + 1); // character frequency @ position p
                    for(char k : tcf.keySet()) {
                        int c1 = tcf.get(k);
                        if(pf.containsKey(k)) {
                            int c2 = pf.get(k);
                            if(c2 < c1) {
                                // c2 does not fit c1
                                f = false;
                                break;
                            }
                        } else {
                            // current character can't be fit
                            f = false;
                            break;
                        }
                    }
                } else {
                    // suitable position not found
                    f = false;
                }
                if(f) {
                    // suitable position found
                    lastp = p;
                    break;
                }
            } while(i.hasNext());
            
            // remove from tcf
            int c = tcf.get(suitable);
            if(c == 1) {
                // full entry remove
                tcf.remove(suitable);
            } else {
                // update count
                tcf.put(suitable, --c);
            }
            
            lmin.append(suitable); // add to output
        }
        
        return lmin.toString();
    }
}