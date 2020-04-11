#include <bits/stdc++.h>
using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int t, n;
    cin >> t;
    while(t--) {
        cin >> n;
        int A[n + 1];
        for(int i = 0; i < n; i++) {
            cin >> A[i];
        }
        // sort and take consecutive XOR
        sort(A, A + n);
        int min_xor = INT_MAX; 

        // calculate min xor of consecutive pairs 
        for (int i = 0; i < n - 1; i++) { 
            int xor_val = A[i] ^ A[i + 1]; 
            if(min_xor > xor_val) {
                // new min
                min_xor = xor_val;
            }
        } 
      
        cout << min_xor << "\n";
    }
    
    return 0;
}
