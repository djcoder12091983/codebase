#include <bits/stdc++.h>
using namespace std;

typedef unsigned long long int UL;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int t, a, n, k;
    cin >> t;
    while(t--) {
        cin >> n >> k;
        unordered_map<int, UL> dp;

        unordered_map<int, UL>::iterator j;
        for(int i = 0; i < n; i++) {
            cin >> a;
            unordered_map<int, UL> tdp; // temporary
            for(j = dp.begin(); j != dp.end(); j++) {
                if(j->first + 1 <= k) {
                    // valid
                    tdp[j->first + 1] = j->second * a;
                }
            }
            
            // copy to dp from tdp
            for(j = tdp.begin(); j != tdp.end(); j++) {
                if(dp[j->first]) {
                    dp[j->first] += j->second;
                } else {
                    dp[j->first] = j->second;
                }
            }
            
            if(dp[1]) {
                dp[1] += a;
            } else {
                dp[1] = a;
            }
        }
        
        // output
        cout << dp[k] << "\n";
    }
    
    return 0;
}
