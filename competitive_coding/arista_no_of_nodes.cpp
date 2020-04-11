#include<bits/stdc++.h>
using namespace std;

int global_c = 0;

// solution
unordered_set<char> dfs_solution(int u, int p, vector<int> *tree, char *values) {
	unordered_set<char> c_set;
	for(int node : tree[u]) {
		if(node != p) {
			// avoid loop
			unordered_set<char> subt_c_set = dfs_solution(node, u, tree, values);
			c_set.insert(subt_c_set.begin(), subt_c_set.end());
		}
	}
	if(c_set.find(values[u]) == c_set.end()) {
		// not same as node
		global_c++;
	}
	
	c_set.insert(values[u]);
	return c_set;
}

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int t, n, u, v;
	cin >> t;

	while(t--) {
		cin >> n;
		char values[n + 1];
		vector<int> tree[n + 1];

		for(int i = 0; i < n; i++) {
			cin >> values[i + 1];
		}
		for(int i = 0; i < n - 1; i++) {
			cin >> u >> v;
			// both way
			tree[u].push_back(v);
			tree[v].push_back(u);
		}

		// solution
		global_c = 0;
		dfs_solution(1, 1, tree, values);

		cout << global_c << "\n";
	}

	return 0;
}
