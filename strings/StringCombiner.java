import java.util.*;

// combination of strings
// for example, abcd of 2 produces ab, ac, ad, bc, bd and cd
// abcd of 3 produces abc, abd, acd and bcd
class StringCombiner {

	private String input;
	
	public StringCombiner(String input) {
		this.input = input;
	}

	public List<String> combination(int c) {
	
		List<String> level = new LinkedList<String>();
		level.add("");
		List<Integer> levelIndex = new LinkedList<Integer>();
		levelIndex.add(0);
		
		int l = input.length();
		
		int i = c;
		while(i > 0) {
		
			// System.out.println("i=>" + i);
			
			List<String> newLevel = new LinkedList<String>();
			List<Integer> newLevelIndex = new LinkedList<Integer>();
			
			int limit = l - i;
			
			int s = level.size();
			for(int j = 0; j < s; j++) {
				String levelVal = level.get(j);
				int k = levelIndex.get(j);
				
				// System.out.println("k=>" + i + ", limit=>" + limit);
				
				for(int m = k; m <= limit; m++) {
					newLevel.add(levelVal + input.charAt(m));
					newLevelIndex.add(m + 1);
				}
			}
			
			level = newLevel;
			levelIndex = newLevelIndex;
			
			i--;
		}
		
		return level;
	}
	
	public String getInput() {
		return input;
	}
	
	// calculation of (n choose r)
	public int nCr(int n, int r) {
		
		int factn = 1;
		for(int i = 1; i <= n; i++) {
			factn *= i;
		}
		
		int factn_r = 1;
		for(int i = 1; i <= (n - r); i++) {
			factn_r *= i;
		}
		
		int factr = 1;
		for(int i = 1; i <= r; i++) {
			factr *= i;
		}
		
		return factn / (factn_r * factr);
	}
	
	public static void main(String a[]) {
		
		StringCombiner combiner = new StringCombiner(a[0]);
		List<String> combinations = combiner.combination(Integer.parseInt(a[1]));
		
		System.out.println("N=>" + a[0].length() + ", C=" + a[1] + ", Combinations of: " + a[0] + "\n");
		int colLimit = 20;
		int i = 0;
		for(String c : combinations) {
			System.out.print(c + " ");
			
			if(i++ > colLimit) {
				i = 0;
				System.out.println("");
			}
		}
		
		int n = a[0].length();
		int r = Integer.parseInt(a[1]);
		
		System.out.println("\n\n" + n + "C" + r+ ": Total=>" + combinations.size() + ", Expected=>" + combiner.nCr(n, r));
	}

}