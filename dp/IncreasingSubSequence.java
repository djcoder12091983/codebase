import java.util.*;

// finds increasing sub sequence
public class IncreasingSubSequence {

	int input[];
	
	IncreasingSubSequence(int input[]) {
		this.input = input;
	}
	
	class Result {
		List<Integer> maxSequence = null;
		List<List<Integer>> possibleSequences = null;
	}

	// maximum increasing sequence
	Result maxSequence() {
		
		List<List<Integer>> dp = new LinkedList<List<Integer>>();
		
		int l = input.length;
		for(int i = 0; i < l; i++) {
		
			int data = input[i];
			
			// finds suitable position
			// TODO top elemenet of each sequence can be matained in BST manner so 
			// than find would be logarithm instead of linear
			int maxSequence = 0;
			List<Integer> suitableSequence = null;
			for(List<Integer> sequence : dp) {
				
				int size = sequence.size();
				int last = ((LinkedList<Integer>)sequence).getLast();
				
				if(data > last) {
					// can be a part of sequence
					int newSize = size + 1;
					if(maxSequence < newSize) {
						suitableSequence = sequence;
						maxSequence = newSize;
					}
				}
			}
			
			if(suitableSequence == null) {
				// possible the first element
				suitableSequence = new LinkedList<Integer>();
				dp.add(suitableSequence);
			}
			
			// adds to sequence
			suitableSequence.add(data);
		}
		
		// max increasing sequence
		int maxIncreasingSequence = 0;
		List<Integer> resultantSequence = null;
		for(List<Integer> sequence : dp) {
		
			int s = sequence.size();
			if(maxIncreasingSequence < s) {
				maxIncreasingSequence = s;
				resultantSequence = sequence;
			}
		}
		
		Result r = new Result();
		r.maxSequence = resultantSequence;
		r.possibleSequences = dp;
		
		return r;
	}
	
	// test
	public static void main(String a[]) {
	
		int input[] = {1, 3, 11, 4, 5, 6, 23, 45, 13, 14, 15, 17, 21, 100, 110, 55, 67, 69, 71, 73, 123, 234, 115};
		
		IncreasingSubSequence s = new IncreasingSubSequence(input);
		Result r = s.maxSequence();
		
		System.out.println("--------------Input data---------------");
		for(int data : input) {
			System.out.print(data + " ");
		}
		System.out.println("\n");
		
		System.out.println("--------------Possible sequences---------------");
		for(List<Integer> sequence : r.possibleSequences) {
			
			for(int data : sequence) {
				System.out.print(data + " ");
			}
			
			System.out.println();
		}
		System.out.println("\n");
		
		System.out.println("--------------Maximum sequence---------------");
		for(int data : r.maxSequence) {
			System.out.print(data + " ");
		}
	}
}