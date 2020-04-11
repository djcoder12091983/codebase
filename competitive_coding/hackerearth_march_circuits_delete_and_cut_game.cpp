#include <bits/stdc++.h>
using namespace std;

typedef long long int LONG;
const LONG MODULO = 1000000007;

// note: modulo inverse
LONG extended_euclidean(LONG a, LONG b, LONG *x, LONG *y) {
    if(a == 0) {
        *x = 0, *y = 1;
        return b;
    }
    LONG x1, y1;
    LONG cf = extended_euclidean(b % a, a, &x1, &y1);
    *x = y1 - (b / a) * x1;
    *y = x1;
    return cf;
}

LONG mod_inverse(LONG a) {
    LONG x, y;
    // assumed modulo inverse exists
    extended_euclidean(a, MODULO, &x, &y);
    return (x % MODULO + MODULO) % MODULO;
}

// graph data structure with required utilities
// note: finding bridge with no. of vertices in each components
// dfs based approached
class graph {
    
    private:
    int V, E;
    vector<int> *edges;
    unordered_set<int> visited;
    int *low_time, *visited_time, *parent, *node_count;
    int t, win, bridges;

    public:
    graph(int V, int E) {
        this->V = V;
        this->E = E;
        edges = new vector<int>[V + 1];
        visited_time = new int[V + 1];
        low_time = new int[V + 1];
        parent = new int[V + 1];
        node_count = new int[V + 1];
        t = win = bridges = 0;
        
        for(int i = 1; i <= V; i++) {
            parent[i] = 0;
            node_count[i] = 1;
        }
    }
    
    // add edge
    void add_edge(int u, int v) {
        edges[u].push_back(v);
        edges[v].push_back(u);
    }
    
    // probability calculation
    long long p_calc(int c) {
        int cf = __gcd(c, bridges);
        int p = c / cf, q = bridges / cf;
        return (p * mod_inverse(q)) % MODULO;
    }
    
    // solution
    void solve() {
        // find A winning edges
        dfs(1);
        
        // now calculate probability along with modulo arithmetic
        if(bridges == 0) {
            // neither A nor B can win
            cout << "0 0\n";
        } else if(win == 0) {
            // only B can win
            cout << "0 1\n";
        } else if(bridges == win ){
            // only A can win
            cout << "1 0\n";
        } else {
            cout << p_calc(win) << " " << p_calc(bridges - win) << "\n";
        }
    }
    
    // dfs call to get A winning edges
    void dfs(int node) {
        visited.insert(node); // add to visted list

        visited_time[node] = low_time[node] = ++t;
        for(int cnode : edges[node]) {
            // for adjacent nodes
            if(visited.find(cnode) == visited.end()) {
                // not visited yet
                parent[cnode] = node;
                dfs(cnode);
                // update sub tree node count for each node
                node_count[node] += node_count[cnode];
                
                // update low_time fot parent node
                low_time[node]  = min(low_time[node], low_time[cnode]);
                // back edge not exist, edge is a bridge
                if(low_time[cnode] > visited_time[node]) {
                    bridges++;
                    int c1 = node_count[cnode];
                    int c2 = V - c1;
                    if(!(c1 & 1) && !(c2 & 1)) {
                        // note: A winning edge
                        // this edge can break into even connected nodes
                        win++;
                    }
                }
            } else {
                if(cnode != parent[node]) {
                    // update low_time for parent node
                    low_time[node]  = min(low_time[node], visited_time[cnode]);
                }
            }
        }
    }
    
    ~graph() {
        delete[] edges;
        delete[] low_time;
        delete[] visited_time;
        delete[] parent;
        delete[] node_count;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int V, E;
    cin >> V >> E;
    graph g(V, E);
    for(int i = 0; i < E; i++) {
        int u, v;
        cin >> u >> v;
        g.add_edge(u, v);
    }
    // solution
    g.solve();
    
    return 0;
}
