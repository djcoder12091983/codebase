package hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Stack;
import java.util.StringTokenizer;

public class PairSum {
    
    // A B large numbers
    static String sum(String A, String B) {
        Stack<Integer> c = new Stack<>();
        
        int l1 = A.length();
        int l2 = B.length();
        int i = l1 -1, j = l2 - 1;
        int carry = 0;
        while(i >= 0 && j >= 0) {
            int d1 = (int)A.charAt(i) - 48;
            int d2 = (int)B.charAt(j) - 48;
            
            int t = d1 + d2 + carry;
            if(t > 9) {
                c.push(t % 10);
                carry = t / 10;
            } else {
            	carry = 0;
                c.push(t);
            }
            
            i--;
            j--;
        }
        
        // remaining digits
        while(i >= 0) {
            int d = (int)A.charAt(i) - 48;
            
            int t = d + carry;
            if(t > 9) {
                c.push(t % 10);
                carry = t / 10;
            } else {
            	carry = 0;
                c.push(t);
            }
            
            i--;
        }
        
        // remaining digits
        while(j >= 0) {
            int d = (int)B.charAt(j) - 48;
            
            int t = d + carry;
            if(t > 9) {
                c.push(t % 10);
                carry = t / 10;
            } else {
            	carry = 0;
                c.push(t);
            }
            
            j--;
        }
        
        // reverse digits
        StringBuilder C = new StringBuilder();
        while(!c.isEmpty()) {
            C.append(c.pop());
        }
        
        return C.toString();
    }
    
    static BigInteger sum1(String A, String B) {
    	return new BigInteger(A).add(new BigInteger(B));
    }
    
    public static void main(String a[]) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        String line;
        while((line = reader.readLine()) != null) {
            StringTokenizer tokens = new StringTokenizer(line);
            String A = tokens.nextToken();
            String B = tokens.nextToken();
            
            System.out.println(sum1(A, B));
        }
        
        reader.close();
    }
}