#include <bits/stdc++.h>
#define LOWERCASE_CHARACTERS 26

using namespace std;

class character_frequency {
    
    // note: DP based LCA find
    
    private:
    int n, *levels, **dp, log_h, **char_freq;
    char *labels;
    vector<int> *tree;
    
    public:
    character_frequency(int n) {
        this->n = n;
        tree = new vector<int>[n + 1];
        
        // DP based LCA find
        log_h = (int)ceil(log2(n));
        dp = new int*[n + 1];
        for(int i = 0; i <= n; i++) {
            dp[i] = new int[log_h + 1];
        }
  
        // node level and label
        levels = new int[n + 1];
        labels = new char[n + 1];
        // default DP fill
        for (int i = 0; i <= n; i++) {
            memset(dp[i], -1, sizeof dp[i]);
        }
        
        // character frequency
        char_freq = new int*[n + 1];
        for(int i = 0; i <= n; i++) {
            char_freq[i] = new int[LOWERCASE_CHARACTERS];
        }
        // default fill
        for (int i = 0; i <= n; i++) {
            memset(char_freq[i], 0, sizeof char_freq[i]);
        }
    }
    
    //add label
    void add_label(int node, char label) {
        labels[node] = label;
    }
    
    // add edge
    void add_edge(int u, int v) {
        // both way (undirected)
        tree[u].push_back(v);
        tree[v].push_back(u);
    }
    
    // DFS based processing of LCA related DP data
    void dfs(int u, int parent) {
        // merge character frequency
        for(int i = 0; i < LOWERCASE_CHARACTERS; i++) {
            char_freq[u][i] = char_freq[parent][i];
        }
        // add label
        char_freq[u][labels[u] - 97]++;
        
        dp[u][0] = parent;
        for(int i = 1; i <= log_h; i++) {
            dp[u][i] = dp[dp[u][i - 1]][i - 1];
        }
        // all adjacent nodes
        for(int v : tree[u]) {
            if(v != parent) {
                // avoid loop
                levels[v] = levels[u] + 1;
                dfs(v, u);
            }
        }
    }
    
    // LCA of u and v
    int lca(int u, int v) {
        // start from farthest node
        if(levels[u] < levels[v]) {
            swap(u, v);
        }
      
        // ancestor of u @ same level as v
        for(int i = log_h; i >= 0; i--) {
            if((levels[u] - (1 << i)) >= levels[v]) {
                u = dp[u][i];
            }
        }
      
        // best case
        if (u == v) {
            return u;
        }
      
        // node closest to the root but not CA of u and v
        // ex: node x is not the CA of u and v but dp[x][0]
        for (int i = log_h; i >= 0; i--) {
            if(dp[u][i] != dp[v][i]) {
                u = dp[u][i];
                v = dp[v][i];
            }
        }
        // found first ancestor
        return dp[u][0];
    }
    
    // find character frequency
    char query_char_freq(int u, int v) {
        int lca_node = lca(u, v); // find lca to get simple path

        // get character frequency for u->u simple path
        int *lca_cf= char_freq[lca_node];
        int *u_cf = char_freq[u];
        int *v_cf = char_freq[v];
        
        for(int i = 0; i < LOWERCASE_CHARACTERS; i++) {
            u_cf[i] -= lca_cf[i];
        }
        for(int i = 0; i < LOWERCASE_CHARACTERS; i++) {
            v_cf[i] -= lca_cf[i];
        }
        int cf[LOWERCASE_CHARACTERS];
        for(int i = 0; i < LOWERCASE_CHARACTERS; i++) {
            cf[i] = u_cf[i] + v_cf[i];
        }
        cf[labels[lca_node] - 97]++;
        
        // get maximum frequency
        int max_f = 0;
        char maxc;
        for(int i = 0; i < LOWERCASE_CHARACTERS; i++) {
            if(cf[i] > max_f) {
                // new max
                maxc = 97 + i;
                max_f = cf[i];
            }
        }
        
        return maxc;
    }
    
    // destructor
    ~character_frequency() {
        // free resources
        delete[] labels;
        delete[] levels;
        delete[] tree;
        for(int i = 0; i <= n; i++) {
            delete[] dp[i];
        }
        for(int i = 0; i <= n; i++) {
            delete[] char_freq[i];
        }
    }
};

int main() {
    
    int n, u, v, q;
    char label;
    
    cin >> n;
    character_frequency cf_p(n);
    
    // labels
    for(int i = 1; i <= n ; i++) {
        cin >> label;
        cf_p.add_label(i, label);
    }
    
    // edges
    for(int i = 1; i < n ; i++) {
        cin >> u >> v;
        cf_p.add_edge(u, v);
    }
    
    // LCA DP processing
    cf_p.dfs(1, 1);
    
    // solution
    cin >> q;
    while(q--) {
        cin >> u >> v;
        cout << cf_p.query_char_freq(u, v) << "\n";
    }
    
    return 0;
}
