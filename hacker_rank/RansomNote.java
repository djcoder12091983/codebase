package hackerrank.interview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RansomNote {
	
	Map<String, Integer> dictionary = new HashMap<String, Integer>();
	
	void add(String word) {
		Integer count = dictionary.get(word);
		if(count == null) {
			// first time
			dictionary.put(word, 1);
		} else {
			dictionary.put(word, count.intValue()+1);
		}
	}
	
	boolean replicate(String note) {
		// space separated note
		StringTokenizer words = new StringTokenizer(note);
		while(words.hasMoreTokens()) {
			String word = words.nextToken();
			if(!dictionary.containsKey(word)) {
				// can't replicate
				return false;
			} else {
				int count = dictionary.get(word);
				if(count == 1) {
					// to be ZERO, remove entry
					dictionary.remove(word);
				} else {
					// decrease the count
					dictionary.put(word, count-1);
				}
			}
		}
		// no exception occurs
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		
		RansomNote rn = new RansomNote();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tokens = new StringTokenizer(reader.readLine());
		int n = Integer.valueOf(tokens.nextToken());
		//int n2 = Integer.valueOf(tokens.nextToken());
		tokens = new StringTokenizer(reader.readLine());
		for(int i=0; i<n; i++) {
			rn.add(tokens.nextToken());
		}
		boolean replica = rn.replicate(reader.readLine());
		System.out.println(replica ? "Yes" : "No");
		
		reader.close();
	}

}