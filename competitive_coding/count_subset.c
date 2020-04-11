#include<stdio.h>
#define MODULO 1000000007

typedef unsigned long long int UL;

int main() {
    
    int t, i, j, n, k, a;
    UL dp[11], tdp[11], c; // note: k is @ most 10
    
    scanf("%d", &t);
    while(t--) {
        scanf("%d%d", &n, &k);
        
        memset(dp, 0, sizeof dp);
        c = 0;
        for(i = 0; i < n; i++) {
            scanf("%d", &a);
            // note: k is small
            memset(tdp, 0, sizeof tdp);
            for(j = 1; j < k; j++) {
                if(dp[j]) {
                    tdp[j + 1] += dp[j] * a;
                    if(tdp[j + 1] >= MODULO) {
                        // modulo reduction
                        tdp[j + 1] %= MODULO;
                    }
                } else {
                    break;
                }
            }
            
            // copy to dp again
            for(j = 1; j <= k; j++) {
                dp[j] += tdp[j];
                if(dp[j] >= MODULO) {
                        // modulo reduction
                        dp[j] %= MODULO;
                    }
            }
            
            // self
            dp[1] += a;
            /*if(dp[1] >= MODULO) {
                // modulo reduction
                dp[1] %= MODULO;
            }*/
        }
        
        printf("%lu\n", dp[k]);
    }
    
    return 0;
}
