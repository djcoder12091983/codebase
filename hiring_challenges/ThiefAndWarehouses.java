package hackerearth.hiring.challenges.date04022018.lenskart;

public class ThiefAndWarehouses {
	
	int max(int a[]) {
		
		int l = a.length;
		int r = 0;
		// left 2 right
		for(int i = 0; i < l; i++) {
			int pos = i;
			for(int j = i+1; j < l; j++) {
				if(a[j] < a[i]) {
					pos = j;
					break;
				}
			}
			int t = a[i]*(pos-i);
			if(t > r) {
				// better result found 
				r = t; 
			}
		}
		// right 2 left
		for(int i = l-1; i >= 0; i--) {
			int pos = i;
			for(int j = i-1; j >= 0; j--) {
				if(a[j] < a[i]) {
					pos = j;
					break;
				}
			}
			int t = a[i]*(i-pos);
			if(t > r) {
				// better result found 
				r = t; 
			}
		}
		// best result
		return r;
	}
	
	public static void main(String[] args) {
		
		ThiefAndWarehouses p = new ThiefAndWarehouses();
		
		System.out.println(p.max(new int[]{2, 4, 3, 2, 1}));
		System.out.println(p.max(new int[]{3, 0, 5, 4, 4, 4}));
	}

}