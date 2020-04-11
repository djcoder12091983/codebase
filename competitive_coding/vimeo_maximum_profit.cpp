#include <bits/stdc++.h>
using namespace std;

typedef unsigned long long int UL;

int main() {

    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

	int t, n, m, k;

	cin >> t;
	while(t--) {
		cin >> n >> m;
		UL prefix_cost[n + 1];
		prefix_cost[0] = 0;

        	int cost;
		for(int i = 0; i < n; i++) {
			cin >> cost;
			// prefix cost to answer range query
			prefix_cost[i + 1] = prefix_cost[i] + cost;
		}

        // note: pre-process data for most frequently visted shop
        int most_f[n + 1];
        for(int i = 0; i <= n; i++) {
            most_f[i] = 0;
        }
        // answer each range query
        int l, r;
		vector<UL> range_cost;
        while(m--) {
            cin >> l >> r;
            most_f[l - 1] += 1;
            most_f[r] -= 1;
            range_cost.push_back(prefix_cost[r] - prefix_cost[l - 1]);
        }

        cin >> k;
        // now choose top max k
        sort(range_cost.begin(), range_cost.end(), greater<UL>());
        UL max_profit = 0;
        for(int i = 0; i < k; i++) {
            max_profit += range_cost[i];
        }
        cout << max_profit << "\n";

        // now find most frequently visited shop
        int max = 0;
        int most_vs;
        for(int i = 1; i < n; i++) {
            most_f[i] += most_f[i - 1];
            if(most_f[i] > max) {
                // new max
                max = most_f[i];
                // most visited shop
                most_vs = i + 1;
            }
        }
        cout << most_vs << "\n";
	}

	return 0;
}
