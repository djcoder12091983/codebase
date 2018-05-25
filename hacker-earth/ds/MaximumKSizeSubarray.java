package hackerearth.practice.ds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class QueuePositionNumber implements Comparable<QueuePositionNumber>{
	int index;
	int number;
	
	public QueuePositionNumber(int number, int index) {
		this.number = number;
		this.index = index;
	}
	
	@Override
	public int compareTo(QueuePositionNumber second) {
		return Integer.valueOf(second.number).compareTo(Integer.valueOf(this.number));
	}
}

public class MaximumKSizeSubarray {
	
	Queue<QueuePositionNumber> queue = new PriorityQueue<QueuePositionNumber>();
	int size, k;
	List<Integer> numbers = null;
	
	public MaximumKSizeSubarray(int size, int k) {
		numbers = new ArrayList<Integer>(size);
		this.size = size;
		this.k = k;
	}
	
	void add(int number) {
		numbers.add(number);
	}
	
	void result() {
		for(int i=0; i<size; i++) {
			queue.add(new QueuePositionNumber(numbers.get(i), i));
			if(i+1>=k) {
				// start printing
				int lowerindex = i+1-k;
				QueuePositionNumber top;
				while((top=queue.peek()).index < lowerindex) {
					// extract invalid numbers from queue
					// those which less than lower-limit
					queue.poll();
				}
				// maximum of sub-array k
				System.out.print(queue.peek().number + " ");
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int n = Integer.valueOf(tokens.nextToken());
        int k = Integer.valueOf(tokens.nextToken());
        MaximumKSizeSubarray mks = new MaximumKSizeSubarray(n, k);
        tokens = new StringTokenizer(reader.readLine());
        for(int i=0; i<n; i++) {
        	mks.add(Integer.valueOf(tokens.nextToken()));
        }
        mks.result();
	}
	
}