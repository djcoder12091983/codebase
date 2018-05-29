package hackerrank.interview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class RunningMedian {
	
	Queue<Integer> medianTrack = new PriorityQueue<Integer>(new Comparator<Integer>() {
		@Override
		public int compare(Integer first, Integer second) {
			// reverse order
			return second.compareTo(first);
		}
	});
	// the values are discarded
	// natural ordering
	Queue<Integer> discardTrack = new PriorityQueue<Integer>();
	// total numbers consumed
	int globalSize = 0;
	
	double addAndMedian(int data) {
		
		globalSize++;
		int size = medianTrack.size();
		int expected = globalSize/2+1;
		
		// add
		if(expected > size) {
			// needs to blind-add
			if(!discardTrack.isEmpty() && discardTrack.peek() < data) {
				// data-candidate is from discard-list
				// original data added to discard-track
				discardTrack.add(data);
				// data-candidate is from discard-list
				data = discardTrack.poll();
			}
			medianTrack.add(data);
		} else {
			if(medianTrack.peek() > data) {
				// remove max to fit next smaller item
				// remove item added to discard-track
				discardTrack.add(medianTrack.poll());
				medianTrack.add(data);
			} else {
				// original data added to discard-track
				discardTrack.add(data);
			}
		}
		
		if(globalSize%2 == 0) {
			// even
			int last = medianTrack.poll();
			int secondlast = medianTrack.peek();
			medianTrack.add(last);
			return (last+secondlast)/2.0;
		} else {
			// odd
			return medianTrack.peek();
		}
	}
	
	public static void main(String[] args) throws Exception {
		RunningMedian rm = new RunningMedian();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.valueOf(reader.readLine());
		for(int i=0; i<n; i++) {
			System.out.println(rm.addAndMedian(Integer.valueOf(reader.readLine())));
		}
		reader.close();
	}
}