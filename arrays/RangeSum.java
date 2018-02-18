package hackerearth.hiring.challenges.date18022018.pm;

// this can be generalized for product also

// constant time
class OneDimension {
	
	int sum[];
	//int l;
	
	OneDimension(int a[]) {
		int l = a.length;
		sum = new int[l];
		int s = 0;
		// left to right
		for(int i = 0;i < l;i++) {
			s += a[i];
			sum[i] = s;
		}
	}
	
	// both inclusive
	int sum(int s, int e) {
		return sum[e] - (s > 0 ? sum[s-1] : 0);
	}
}

// MxN matrix
// linear time (O(n))
class TwoDimension {
	
	int sum[][];
	int l;
	//int horizontal[];
	
	TwoDimension(int a[][]) {
		l = a.length;
		sum = new int[l][];
		for(int i = 0;i < l;i++) {
			int s = 0;
			int l1 = a[i].length;
			sum[i] = new int[l1];
			for(int j = 0;j < l1;j++) {
				s += a[i][j];
				sum[i][j] = s;
			}
		}
	}
	
	int sum(int x1, int y1, int x2, int y2) {
		int s = 0;
		for(int i = 0;i < l;i++) {
			if(i < x1 || i > x2) {
				// invalid range
				continue;
			}
			s += sum[i][y2] - (y1 > 0 ? sum[i][y1-1] : 0);
		}
		return s;
	}
}

// constant time (is it possible?)
class ModifiedTwoDimension {
	
	int vertical[];
	int horizontal[];
	// MxN
	int m, n;
	
	ModifiedTwoDimension(int a[][]) {
		m = a.length;
		n = a[0].length; // assumed data exists
		vertical = new int[n];
		horizontal = new int[m];
		// horizontal
		int prevs = 0;
		for(int i = 0;i < m;i++) {
			int s = 0;
			for(int j = 0;j < n;j++) {
				s += a[i][j];
			}
			horizontal[i] = prevs + s;
			prevs = horizontal[i];
		}
		// vertical
		prevs = 0;
		for(int i = 0;i < n;i++) {
			int s = 0;
			for(int j = 0;j < m;j++) {
				s += a[j][i];
			}
			vertical[i] = prevs + s;
			prevs = vertical[i];
		}
	}
	
	int sum(int x1, int y1, int x2, int y2) {
		// TODO
		return 0;
	}
}

// range sum without segmented tree
public class RangeSum {
	
	public static void main(String[] args) {
		
		int a[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		OneDimension d1 = new OneDimension(a);
		System.out.println(d1.sum(3, 9));
		
		int a2[][] = {
				{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
				{11, 12, 13, 14, 15, 16, 17, 18, 19, 20},
				{21, 22, 23, 24, 25, 26, 27, 28, 29, 30},
				{31, 32, 33, 34, 35, 36, 37, 38, 39, 40},
				{41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
				{51, 52, 53, 54, 55, 56, 57, 58, 59, 60}
		};
		
		TwoDimension d2 = new TwoDimension(a2);
		System.out.println(d2.sum(1, 1, 2, 2));
	}

}