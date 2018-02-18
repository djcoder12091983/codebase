package hackerearth.hiring.challenges.date18022018.pm;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class State {
	int x1, y1;
	int x2, y2;
	
	State(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj == this) {
			return true;
		}
		if(obj instanceof State) {
			State s = (State)obj;
			return this.x1 == s.x1 && this.x2 == s.x2 && this.y1 == s.y1 && this.y2 == s.y2;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int h = 13;
		h += h*this.x1;
		h += h*this.y1;
		h += h*this.x2;
		h += h*this.y2;
		return h;
	}
	
	int compute(TreasureIsland t) {
		//System.out.println("<" + x1 + "," + y1 + "><" + x2 + "," + y2 + ">");
		if(x1==x2 && y1==y2) {
			// it's already computed
			return 0;
		}
		Integer precomputed = t.computed.get(this);
		if(precomputed != null) {
			// dynamic programming
			return precomputed.intValue();
		}
		
		int minCost = Integer.MAX_VALUE;
		int l = y2 - y1;
		// vertical cut
		for(int i = 0;i < l;i++) {
			// ORed
			State first = new State(x1, y1, x2, y1+i);
			State second = new State(x1, y1+i+1, x2, y2);
			/*System.out.println("<" + first.x1 + "," + first.y1 + "><" + first.x2 + "," + first.y2 + ">");
			System.out.println("FC1: " + t.cost(first));
			System.out.println("<" + second.x1 + "," + second.y1 + "><" + second.x2 + "," + second.y2 + ">");
			System.out.println("SC1: " + t.cost(second));*/
			int  cost = t.cost(first) + t.cost(second) + first.compute(t) + second.compute(t); // anded
			if(cost < minCost) {
				minCost = cost;
			}
		}
		// horizontal cut
		l = x2 - x1;
		for(int i = 0;i < l;i++) {
			// ORed
			State first = new State(x1, y1, x1+i, y2);
			State second = new State(x1+i+1, y1, x2, y2);
			/*System.out.println("<" + first.x1 + "," + first.y1 + "><" + first.x2 + "," + first.y2 + ">");
			System.out.println("FC2: " + t.cost(first));
			System.out.println("<" + second.x1 + "," + second.y1 + "><" + second.x2 + "," + second.y2 + ">");
			System.out.println("SC2: " + t.cost(second));*/
			int  cost = t.cost(first) + t.cost(second) + first.compute(t) + second.compute(t); // anded
			if(cost < minCost) {
				minCost = cost;
			}
		}
		t.computed.put(new State(x1, y1, x2, y2), minCost);
		return minCost;
	}
}

public class TreasureIsland {
	
	// arrays/RangeSum.TwoDimension
	TwoDimension sum = null;
	Map<State, Integer> computed = new HashMap<State, Integer>();
	int a[][];
	
	public TreasureIsland(int a[][]) {
		sum = new TwoDimension(a);
		this.a = a;
	}
	
	int cost(State state) {
		return sum.sum(state.x1, state.y1, state.x2, state.y2);
	}
	
	public static void main(String[] args) throws Exception {
		
		int a[][];
		
		// input stream
		InputStream in = new FileInputStream("/home/dspace/debasis/NDL/HiringChallenges/data/pm.min.cost.data");
		// System.in
		Scanner s = new Scanner(in);
		int n = s.nextInt();
		int m = s.nextInt();
		a = new int[n][m];
		for(int i = 0;i < n;i++) {
			for(int j = 0;j < m;j++) {
				a[i][j] = s.nextInt();
			}
		}
		State state = new State(0, 0, m-1, n-1);
		System.out.println(state.compute(new TreasureIsland(a)));
		
		s.close();
	}

}