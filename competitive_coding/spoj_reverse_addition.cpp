#include <bits/stdc++.h>
using namespace std;

typedef long long int LONG;

// reverse add
LONG solve(string a, string b) {
    int l1 = a.length(), l2 = b.length();
    int l = max(l1, l2);
    LONG reverse_sum = 0;
    LONG p = pow(10, l - 1);
    int c = 0, i = 0;
    while(i < l) {
        int d1 = i < l1 ? (a[i] - 48) : 0;
        int d2 = i < l2 ? (b[i] - 48) : 0;
        int d = c + d1 + d2;
        c = d / 10;
        d = d % 10;
        reverse_sum += d * p;
        
        p /= 10;
        i++;
    }
    if(c > 0) {
        reverse_sum += c;
    }
    return reverse_sum;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int t;
    cin >> t;
    while(t--) {
    	string a, b;
    	cin >> a >> b;
    	cout << solve(a, b) << "\n";
    }
    // cout << solve("24", "1") << "\n";
    
    return 0;
}
