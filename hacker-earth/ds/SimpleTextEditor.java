package hackerrank.practice.ds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class SimpleTextEditor {
	
	class Operation {
		int type; // 2 DELETE 1 APPEND
		String action;
		
		public Operation(int type, String action) {
			this.type = type;
			this.action = action;
		}
	}
	
	Stack<Character> editor = new Stack<Character>();
	Stack<Operation> inverseOperations = new Stack<Operation>();
	
	void append(String text, boolean save) {
		int l = text.length();
		for(int i=0; i<l; i++) {
			editor.push(text.charAt(i));
		}
		if(save) {
			// save last operation
			inverseOperations.push(new Operation(2, String.valueOf(l)));
		}
	}
	
	void removelast(int last, boolean save) {
		int c = 0;
		StringBuilder remove = new StringBuilder();
		while(c++<last) {
			remove.append(editor.pop());
		}
		if(save) {
			// save last operation
			inverseOperations.push(new Operation(1, remove.reverse().toString()));
		}
	}
	
	char print(int index) {
		return editor.get(index);
	}
	
	void undo() {
		Operation last = inverseOperations.pop();
		// System.out.println(last.type + " " + last.action);
		if(last.type == 1) {
			// append
			append(last.action, false); // no state save for undo
		} else {
			// remove
			removelast(Integer.parseInt(last.action), false); // no state save for undo
		}
	}
	
	public static void main(String[] args) throws Exception {
		//System.setIn(new FileInputStream("/home/dspace/debasis/NDL/HackerRank/data/sample.te.txt"));
		SimpleTextEditor editor = new SimpleTextEditor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(reader.readLine());
		for(int i=0; i<tc; i++) {
			String tokens[] = reader.readLine().split(" ");
			int type = Integer.parseInt(tokens[0]);
			if(type == 1) {
				// append
				String text = tokens[1];
				editor.append(text, true); // save state for undo operation
			} else if(type == 2) {
				// remove
				editor.removelast(Integer.parseInt(tokens[1]), true); // save state for undo operation
			} else if(type == 3) {
				// print
				System.out.println(editor.print(Integer.parseInt(tokens[1])-1));
			} else {
				// undo
				editor.undo();
			}
		}
		reader.close();
	}

}