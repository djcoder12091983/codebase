package hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

//union/find for cycle detection
class DisjointSet {

	class Node {
		int node;
		int size;
		Node parent;

		Node(int x) {
			this.node = x;
			size = 1;
			// initially points to self
			parent = this;
		}
	}

	Node nodes[];

	public DisjointSet(int n) {
		nodes  = new Node[n + 1];
		// initially all are disjoint
		for(int i = 1; i <= n; i++) {
			nodes[i] = new Node(i);
		}
	}

	// find
	Node find(int x) {
		Node node = nodes[x];
		while(node.parent != node) {
			node = node.parent;
		}

		// path compression
		Node node1 = nodes[x];
		while(node1.parent != node) {
			node1.parent = node; // cache
			node1 = node1.parent;
		}

		return node;
	}

	// union by size
	boolean union(int x, int y) {
		Node xroot = find(x);
		Node yroot = find(y);

		if(xroot == yroot) {
			// nothing to do, already connected
			// it's a cycle case
			return false;
		}

		if(xroot.size < yroot.size) {
			// swap
			Node t = xroot;
			xroot = yroot;
			yroot = t;
		}

		// merge yroot into xroot
		yroot.parent = xroot;
		xroot.size += yroot.size;

		return true;
	}
}

public class BuildingRoads {

	// road connecting cities
	class Road {
		int u, v;
		long c;

		Road(int u, int v, long c) {
			this.u = u;
			this.v = v;
			this.c = c;
		}
	}

	List<Road> roads;
	int n;

	public BuildingRoads(int n, int m) {
		roads = new ArrayList<>(m);
		this.n = n;
	}

	// connects road
	void connect(int u, int v, long c) {
		roads.add(new Road(u, v, c));
	}

	// cost minimization (finding MST in undirected graph)
	long minimizeCost() {

		// note: kruskal's algorithm to find MST
		Collections.sort(roads, new Comparator<Road>() {
			@Override
			public int compare(Road r1, Road r2) {
				// sort by cost
				return Long.valueOf(r1.c).compareTo(Long.valueOf(r2.c));
			}
		});

		DisjointSet ds = new DisjointSet(n);

		long c = 0;
		int rc = 0;
		// greedy searching
		for (Road road : roads) {
			int u = road.u;
			int v = road.v;

			// add to disjoint set
			if (ds.union(u, v)) {
				// consider current dealer
				// it will be be part of MST
				c += road.c;
				rc++; // how many road considered to check graph connectivity
			}
		}

		if (rc < n - 1) {
			// that means graph is disconnected
			// error: need more dealers
			return -1;
		}

		return c;
	}

	// driver program
	public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int t = Integer.parseInt(reader.readLine());
		while (--t >= 0) {

			// for each query
			StringTokenizer tokens = new StringTokenizer(reader.readLine());
			int n = Integer.parseInt(tokens.nextToken());
			int m = Integer.parseInt(tokens.nextToken());

			BuildingRoads br = new BuildingRoads(n, m);

			for (int i = 0; i < m; i++) {
				// add road
				tokens = new StringTokenizer(reader.readLine());
				int u = Integer.parseInt(tokens.nextToken());
				int v = Integer.parseInt(tokens.nextToken());
				long c = Long.parseLong(tokens.nextToken());

				br.connect(u, v, c);
			}

			// solve
			long c = br.minimizeCost();
			if (c != -1) {
				System.out.println(c);
			} else {
				// error: need more dealers
				System.out.println("Need More Dealers");
			}
		}

		reader.close();
	}
}