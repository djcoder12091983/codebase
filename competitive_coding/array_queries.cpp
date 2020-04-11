#include <bits/stdc++.h>
using namespace std;

// find actual index (in case of after rotations) using modulo arithmetic
// note: index is 0 based
int find_index(int rotations, int idx, int N) {
    int t = idx - rotations;
    if(t >= 0) {
        return t % N;
    } else {
        // negative case
        return (N - (-1 * t)) % N;
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int N, Q;
    
    cin >> N;
    long A[N];
    for(int i = 0; i < N; i++) {
        cin >> A[i];
    }
    
    // query map
    map<string, int> query_map = {
        {"Left", 1},
        {"Right", 2},
        {"Update", 3},
        {"Increment", 4},
        {"?", 5}
    };
    
    // query
    cin >> Q;
    int rotations = 0;
    while(Q--) {
        string op;
        cin >> op;
        
        switch(query_map[op]) {
            // left rotations
            case 1:
            rotations--;
            break;
            
            // right rotations
            case 2:
            rotations++;
            break;
            
            // update @ index
            case 3:
            int idx;
            long val;
            cin >> idx >> val;
            idx = find_index(rotations, idx - 1, N);
            A[idx] = val;
            break;
            
            // increment @ index by 1
            case 4:
            cin >> idx;
            idx = find_index(rotations, idx - 1, N);
            A[idx]++;
            break;
            
            // find @ index
            case 5:
            cin >> idx;
            idx = find_index(rotations, idx - 1, N);
            cout << A[idx] << "\n";
        }
    }
    
    return 0;
}
