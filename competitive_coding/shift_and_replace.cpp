#include <bits/stdc++.h>
using namespace std;
// note: this algorithm tracks displacement of elements
class solution {
    private:
    unordered_map<int, int> shift_count;
    // maximum frequency of shifted elements
    map<int, set<int>, greater<int>> top_count;
    int *A;
    int N;
    
    public:
    solution(int n) {
        N = n;
        A = new int[n + 1];
    }
    
    void add(int idx, int a) {
        A[idx] = a;
        if(a <= N) {
            // process only identity elements
            int d = idx - a;
            shift_count[d]++;
        }
    }
    
    // manipulate frequency
    void pre() {
        for(pair<int, int> shift : shift_count) {
            int d = shift.first;
            if(d >= 0) {
                // avoid negative displacement, because it needs more op.
                int c = shift.second;
                top_count[c].insert(d);
            }
        }
    }
    
    // solution after each update
    int query(int idx, int a) {
        int c, d;
        if(A[idx] <= N) {
            // remove previous state
            d = idx - A[idx];
            c = shift_count[d];
            if(d >= 0) {
                top_count[c].erase(d);
                if(top_count[c].empty()) {
                    top_count.erase(c);
                }
            }
            shift_count[d]--;
            c--;
            if(!c) {
                shift_count.erase(d);
            } else {
                if(d >= 0) {
                    top_count[c].insert(d);
                }
            }
        }
        // update new state
        A[idx] = a;
        if(a <= N) {
            d = idx - a;
            shift_count[d]++;
            if(d >= 0) {
                c = shift_count[d];
                top_count[c].insert(d);
            }
        }
        // find minimum operations
        if(top_count.empty()) {
            // worst case
            return N;
        }
        int min_op = INT_MAX, op;
        for(pair<int, set<int>> top : top_count) {
            bool f = false;
            for(int t : top.second) {
                int inv = t - N;
                op = t + N - top.first; // default shift op.
                if(shift_count.find(inv) != shift_count.end()) {
                    // inverse found, need not to change some shifted elements
                    op -= shift_count[inv];
                }
                if(op < min_op) {
                    f = true;
                    min_op = op;
                }
            }
            if(!f) {
                // no more minimization possible
                break;
            }
        }
        // now check for elements already in original position
        if(shift_count.find(0) != shift_count.end()) {
            min_op = min(min_op, N - shift_count[0]);
        }
        return min(N, min_op);
    }
    
    ~solution() {
        delete[] A;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int n, i, q, a;
    cin >> n;
    solution s(n);
    for(i = 0;  i< n; i++) {
        cin >> a;
        s.add(i + 1, a);
    }
    s.pre();
    
    // query process
    cin >> q;
    while(q--) {
        cin >> i >> a;
        cout << s.query(i, a) << "\n";
    }
    
    return 0;
}
