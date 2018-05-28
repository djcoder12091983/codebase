package hackerrank.interview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BalancedBrackets {
	
	String expression;
	
	public BalancedBrackets(String expression) {
		this.expression = expression;
	}
	
	boolean balanced() {
		Deque<Character> remember = new LinkedList<Character>();
		Map<Character, Character> map = new HashMap<Character, Character>(4);
		// this mapping can be extensible
		map.put('(', ')');
		map.put('{', '}');
		map.put('[', ']');
		
		int l = expression.length();
		boolean bal = true;
		for(int i=0; i<l; i++) {
			char symbol = expression.charAt(i);
			if(map.containsKey(symbol)) {
				// start '(', '{', '['
				remember.push(symbol);
			} else {
				// assumed end ')', '}', ']'
				if(remember.isEmpty()) {
					// mismatch
					bal = false;
					break;
				}
				char last = remember.peek();
				if(map.get(last) == symbol) {
					remember.pop(); // match, so pop
				} else {
					// mismatch
					bal = false;
					break;
				}
			}
		}
		if(bal) {
			// checks whether if stack is empty or not
			// empty means all match is done
			bal = remember.isEmpty();
		}
		return bal;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.valueOf(reader.readLine());
		for(int i=0; i<tc; i++) {
			String ex = reader.readLine();
			BalancedBrackets bb = new BalancedBrackets(ex);
			System.out.println(bb.balanced() ? "YES" : "NO");
		}
		reader.close();
	}

}