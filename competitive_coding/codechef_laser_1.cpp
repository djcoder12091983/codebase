#include <bits/stdc++.h>
using namespace std;

// segment tree node data
typedef struct node_data {
    vector<int> start, end;
    
    void add(pair<int, int> segment) {
        start.push_back(segment.first);
        end.push_back(segment.second);
    }
    
    // merge two data
    void merge_data(node_data data1, node_data data2) {
        merge(data1.start.begin(), data1.start.end(),
              data2.start.begin(), data2.start.end(),
              back_inserter(start));
        merge(data1.end.begin(), data1.end.end(),
              data2.end.begin(), data2.end.end(),
              back_inserter(end));
    }
    
    // find segment intersections count
    int find(int point) {
        // note: inclusion exclusion count
        int c = start.size();
        // starting after point
        c -= start.end() - upper_bound(start.begin(), start.end(), point);
        // ending before point
        c -= upper_bound(end.begin(), end.end(), point - 1) - end.begin();
        
        return c;
    }
} node_data;

// note: using segment tree we can find line segment intersections for a given range
class intersection_segment {
    private:
    int n;
    node_data *segment_tree;
  
    // internal usage  
    int query(int idx, int st, int end, int l, int r, int p) {
        if (r < st || l > end) {
            // out of range
            return 0;
        }
        if (l <= st && end <= r) {
            // within range
            return segment_tree[idx].find(p);
        }
        
        // merge both
        int mid = (st + end) / 2;
        return query(2 * idx + 1, st, mid, l, r, p) + query(2 * idx + 2, mid + 1, end, l, r, p);
    }
    
    // segment tree construction
    void build(int idx, int st, int end, pair<int, int>* segments) {
        if(st == end) {
            segment_tree[idx].add(segments[st]);
            return; 
        }
  
        int mid = (st + end) / 2;
        int lidx = 2 * idx + 1, ridx = 2 * idx + 2;
        build(lidx, st, mid, segments);
        build(ridx, mid + 1, end, segments);
  
        segment_tree[idx].merge_data(segment_tree[lidx], segment_tree[ridx]);
    }
    
    public:
    intersection_segment(pair<int, int>* segments, int n) {
        this->n = n;
        int h = (int)ceil(log2(n));
        h = 2 * (1 << h) - 1;
        segment_tree = new node_data[h];
        // build
        build(0, 0, n - 1, segments);
    }
    
    // solution query (segment intersections for a given range)
    int query(int l, int r, int point) {
        return query(0, 0, n - 1, l, r, point);
    }
    
    ~intersection_segment() {
        // free memory
        delete[] segment_tree;
    }
};

pair<int, int> create_segment(int p1, int p2) {
    if(p1 < p2) {
        return pair<int, int>(p1, p2);
    } else {
        return pair<int, int>(p2, p1);
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int t, n, q;
    cin >> t;
    while(t--) {
        cin >> n >> q;

        // prepare
        pair<int, int> segments[n - 1];
        int p1, p2;
        cin >> p1;
        for(int i = 1; i < n; i++) {
            cin >> p2;
            segments[i - 1] = create_segment(p1, p2);
            
            p1 = p2;
        }
        
        intersection_segment driver(segments, n - 1);
        
        while(q--) {
            int l, r, p;
            cin >> l >> r >> p;
            cout << driver.query(l - 1, r - 2, p) << "\n";
        }
    }

    return 0;
}
