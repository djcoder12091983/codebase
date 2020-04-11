#include <bits/stdc++.h>
using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int t;
    cin >> t;
    int c = 0;
    while(++c <= t) {
        int N, K, P;
        cin >> N >> K >> P;
        int sum[N + 1][K + 1];
        for(int i = 0; i < N; i++) {
            sum[i + 1][0] = 0;
        }
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < K; j++) {
                int a;
                cin >> a;
                sum[i + 1][j + 1] = sum[i + 1][j] + a;
            }
        }
        int DP[N + 1][P + 1];
        for(int i = 0; i <= N; i++) {
            DP[i][0] = 0;
        }
        for(int i = 0; i <= P; i++) {
            DP[0][i] = 0;
        }
        for(int i = 1; i <= N; i++) {
            for(int j = 0; j <= P; j++) {
                DP[i][j] = 0;
                int l = min(j, K);
                for(int x = 0; x <= l; x++) {
                    DP[i][j] = max(DP[i][j], sum[i][x] + DP[i - 1][j - x]);
                }
            }
        }
        cout << "Case #" << c << ": " << DP[N][P] << "\n";
    }
    
    return 0;
}
