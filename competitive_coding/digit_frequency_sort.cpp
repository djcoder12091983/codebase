#include <bits/stdc++.h>
using namespace std;

// solution
string solve(string input) {
    // as input consists of digits [0-9]
    int d_f[10];
    memset(d_f, 0, sizeof d_f);
    
    int l = input.length();
    for(int i = 0; i < l; i++) {
        d_f[input[i] - 48]++; // because 48 is ascii value of '0'
    }
    
    string output;
    
    // now sort d_f(digit frequency) on the basis of frequency
    // limit is 10 so .. using O(n^2) sort
    int min_f = 1, min_idx;
    for(int i = 0; i < 10; i++) {
        // for every find min frequency
        int min_t = INT_MAX; // some large value
        for(int j = 0; j < 10; j++) {
            if(d_f[j] == min_f) {
                // same frequency so consider minimum digit
                min_idx = j;
                min_t = min_f;
                break;
            } else if(d_f[j] > min_f) {
                // next minimum but greater than previous minimum
                if(d_f[j] < min_t) {
                    min_t = d_f[j];
                    min_idx = j;
                }
            }
        }
        if(min_t == INT_MAX) {
            // no more frequency found
            break;
        }
        // write result into output
        for(int j = 0; j < min_t; j++) {
            output.push_back(min_idx + 48); // because 48 is ascii value of '0'
        }
        // reset
        min_f = min_t;
        d_f[min_idx] = 0;
    }

    return output;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    string input;
    cin >> input;
    
    // /solution
    cout << solve(input) << "\n";
    
    return 0;
}
