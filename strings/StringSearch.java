// string search algorithm
public class StringSearch {
    
    String input;
    
    StringSearch(String input) {
        this.input = input;
    }
    
    class Result {
        int start = -1;
        // int end = -1;
        boolean found = false;
    }
    
    // pattern search
    Result kmpSearch(String pattern) {
        
        // populate KMP table for pattern
        int l = pattern.length();
        int kmp[] = new int[l];
        
        int j = 0;
        for(int i = 0; i < l; i++) {
        
            char ch1 = pattern.charAt(i);
            char ch2 = pattern.charAt(j);
            
            if(j < i) {
                
                if(ch1 == ch2) {
                    kmp[i] = j;
                    j++;
                } else {
                    // reset
                    kmp[i] = -1;
                    j = 0;
                }
            } else {
                kmp[i] = -1;
            }
        }
        
        // KMP table print
        /*for(int i = 0; i < l; i++) {
            
            System.out.print(kmp[i] + " ");
        }
        System.out.println("\n");*/
        
        int l1 = input.length();
        
        j = 0;
        int start = -1;
        // int end = -1;
        boolean found = false;
        for(int i = 0; i < l1; i++) {
        
            char ch1 = input.charAt(i);
            char ch2 = pattern.charAt(j);
            
            if(ch1 == ch2) {
            
                if(j == 0) {
                    // first char found
                    start = i;
                }
            
                j++;
                
                if(j == l) {
                    // pattern found
                    found = true;
                    break;
                }
                
            } else if(start != -1) {
                // System.out.println("kmp[j - 1]: " + kmp[j - 1]);
                // some fragment found
                if(kmp[j - 1] != -1) {
                    // prefix found
                    start = i - kmp[j - 1];
                    j = kmp[j - 1] + 1;
                    i--;
                } else {
                    // reset
                    j = 0;
                    start = -1;
                }
            }
        }
        
        Result r = new Result();
        r.start = start;
        r.found = found;
        
        return r;
    }
    
    public static void main(String a[]) {
        
        String input = new StringBuilder("my name is debasis jana.").append(" i am bad boy.").append(" i work in NRIFT.")
                            .append(" i am the most disobidient developer. ").toString();
        StringSearch ss = new StringSearch(input);
        
        String patterns[] = {"debasis jan", "bad boy", "NRIFT", "most disobidient", "debu"};

        for(String p : patterns) {
            Result r = ss.kmpSearch(p);
            System.out.println("'" + p + "' found: " + r.found + ", start: " + r.start);
        }
        
        input = "ASVBTRYIOPXVDFRTYABCDEFFGTYUPABCDEFFGTYUPABCDEFBNHTYUIOPXVBCGTHUICHTYUIOP";
        String pattern1 = "ABCDEFFGTYUPABCDEFBNHTYUIOP";
        String pattern2 = "ABCDEFFGTYUPABCDEFBNHTYUIOPVNHGYTUXCDER";
        
        ss = new StringSearch(input);
        
        Result r = ss.kmpSearch(pattern1);
        System.out.println("'" + pattern1 + "' found: " + r.found + ", start: " + r.start);
        r = ss.kmpSearch(pattern2);
        System.out.println("'" + pattern2 + "' found: " + r.found + ", start: " + r.start);
    }
}