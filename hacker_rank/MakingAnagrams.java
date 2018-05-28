package hackerrank.interview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MakingAnagrams {
	
	String input1;
	String input2;
	
	public MakingAnagrams(String input1, String input2) {
		this.input1 = input1;
		this.input2 = input2;
	}
	
	int make() {
		Map<Character, Integer> map1 = new HashMap<Character, Integer>();
		int l = input1.length();
		for(int i=0; i<l; i++) {
			char ch = input1.charAt(i);
			Integer count = map1.get(ch);
			if(count == null) {
				// first time
				map1.put(ch, 1);
			} else {
				map1.put(ch, count.intValue()+1);
			}
		}
		
		int missing = 0;
		l = input2.length();
		for(int i=0; i<l; i++) {
			char ch = input2.charAt(i);
			Integer count = map1.get(ch);
			if(count == null) {
				// no entry
				missing++;
			} else {
				// decrease count by 1
				if(count == 1) {
					// to be ZERO, remove entry
					map1.remove(ch);
				} else {
					// count-1
					map1.put(ch, count.intValue()-1);
				}
			}
		}
		
		int remaining = 0;
		for(char key : map1.keySet()) {
			remaining += map1.get(key);
		}
		// missing + remaining
		return missing+remaining; 
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input1 = reader.readLine();
		String input2 = reader.readLine();
		MakingAnagrams ma = new MakingAnagrams(input1, input2);
		System.out.println(ma.make());
		reader.close();
	}
}