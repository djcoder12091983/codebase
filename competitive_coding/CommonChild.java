package cp;

// https://www.hackerrank.com/challenges/common-child/problem
public class CommonChild {

	static int commonChild(String s1, String s2) {
        // longest common subsequence
        // using dynamic programming (DP)
        int l = s1.length();
        
        int lcs[] = new int[l + 1];
        lcs[0] = 0;
        for(int i = 1; i <= l; i++) {
            lcs[i] = 0;
        }
        
        for(int i = 0; i < l; i++) {
            
            int nlcs[] = new int[l + 1];
            nlcs[0] = 0;
            
            for(int j = 0; j < l; j++) {
                char ch1 = s1.charAt(i);
                char ch2 = s2.charAt(j);
                if(ch1 == ch2) {
                    // 1 + DP(i-1, j-1)
                    nlcs[j + 1] = 1 + lcs[j];
                } else {
                    // max(DP(i, j-1), DP(i-1, j))
                    nlcs[j + 1] = Math.max(nlcs[j], lcs[j + 1]);
                }
            }
            
            // copy to lcs from nlcs
            for(int j = 0; j <= l; j++) {
                lcs[j] = nlcs[j];
            }
        }
        
        return lcs[l]; // result
    }
}