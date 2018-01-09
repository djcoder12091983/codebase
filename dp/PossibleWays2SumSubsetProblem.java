package in.iitkgp.computation.dp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// find all possible ways to find `sum subset` problem
public class PossibleWays2SumSubsetProblem {
	
	List<Integer> numbers = new ArrayList<Integer>();
	
	public PossibleWays2SumSubsetProblem() {
		// blank
	}
	
	public PossibleWays2SumSubsetProblem(List<Integer> numbers) {
		this.numbers.addAll(numbers);
	}
	
	void add(int number) {
		this.numbers.add(number);
	}
	
	boolean find(int number) {
		
		int l = numbers.size();
		// sumExists(i,j) jth element adds up to i
		boolean sumExists[][] = new boolean[number + 1][l + 1];
		
		sumExists[0][0] = false;
		for(int i = 1; i <= l; i++) {
			sumExists[0][i] = true;
		}
		for(int i = 1; i <= number; i++) {
			sumExists[i][0] = false;
		}
		
		for(int i = 1; i <= number; i++) {
			for(int k = 1; k<= l; k++) {
				if(sumExists[i][k - 1]) {
					// does not include kth element
					sumExists[i][k] = true;
				} else {
					int kth = numbers.get(k - 1);
					int behind = i - kth;
					if(behind >= 0 && sumExists[behind][k - 1]) {
						// kth included
						sumExists[i][k] = true;
					} else {
						// sum does not exist
						sumExists[i][k] = false;
					}
				}
			}
		}
		
		//System.out.println("Sum: " + number + " Possible: " + sumExists[number][l]);
		
		return sumExists[number][l];
	}
	
	class PossibleSumResult {
		
		boolean sumExists = false;
		List<List<Integer>> results = new LinkedList<List<Integer>>();
		
		public PossibleSumResult(boolean sumExists) {
			this.sumExists = sumExists;
		}
		
		void copyResult(PossibleSumResult sum, Integer add) {
			
			if(results.isEmpty()) {
				// no results exist
				if(add != null) {
					// added data provided, need to add
					List<Integer> result = new LinkedList<Integer>();
					result.add(add);
					
					sum.results.add(result);
				}
			} else {
				// results already exists
				for(List<Integer> result : results) {
					
					List<Integer> clone = new LinkedList<Integer>();
					clone.addAll(result);
					
					if(add != null) {
						clone.add(add);
					}
					
					sum.results.add(clone);
				}
			}
		}
		
		void copyResult(PossibleSumResult sum) {
			copyResult(sum, null);
		}
	}
	
	PossibleSumResult findResults(int number) {
		
		int l = numbers.size();
		// sumExists(i,j) jth element adds up to i
		PossibleSumResult sumResults[][] = new PossibleSumResult[number + 1][l + 1];
		
		sumResults[0][0] = new PossibleSumResult(false);
		for(int i = 1; i <= l; i++) {
			sumResults[0][i] = new PossibleSumResult(true);
		}
		for(int i = 1; i <= number; i++) {
			sumResults[i][0] = new PossibleSumResult(false);
		}
		
		for(int i = 1; i <= number; i++) {
			for(int k = 1; k<= l; k++) {
				
				boolean found = false;
				if(sumResults[i][k - 1].sumExists) {
					// does not include kth element
					sumResults[i][k] = new PossibleSumResult(true);
					// only clone
					sumResults[i][k - 1].copyResult(sumResults[i][k]);
					found = true;
				}
				
				int kth = numbers.get(k - 1);
				int behind = i - kth;
				if(behind >= 0 && sumResults[behind][k - 1].sumExists) {
					// kth included
					if(!found) {
						// first case not satisfied
						sumResults[i][k] = new PossibleSumResult(true);
					}
					// clone with add one
					sumResults[behind][k - 1].copyResult(sumResults[i][k], kth);
					found = true;
				}
				
				// sum does not exist
				if(!found) {
					// if first 
					sumResults[i][k] = new PossibleSumResult(false);
				}
			}
		}
		
		return sumResults[number][l];
	}
	
	public static void main(String[] args) {
		
		PossibleWays2SumSubsetProblem p = new PossibleWays2SumSubsetProblem();
		
		// test
		for(int i = 1; i <= 10; i++) {
			p.add(i);
		}
		
		int n = 45;
		
		System.out.println("Sum: " + n + " is possible: " + p.find(n));
		System.out.println();
		
		PossibleSumResult sumResult = p.findResults(n);
		
		if(sumResult.sumExists) {
			// sum exists
			System.out.println("Sum: " + n + " is possible");
			
			// result test
			System.out.println("Result:");
			List<List<Integer>> results = sumResult.results;
			for(List<Integer> result : results) {
				for(int data : result) {
					System.out.print(data + " ");
				}
				System.out.println();
			}
		} else {
			// sum does not exist
			System.out.println("Sum: " + n + " is not possible");
		}
		
	}

}