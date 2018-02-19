package rookierank4.date19022018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ExamRush {
	
	List<Integer> times = new ArrayList<Integer>();
	
	void add(int time) {
		times.add(time);
	}
	
	// greedy
	int max(int timeLeft) {
		// sort
		Collections.sort(times);
		int c = 0;
		int t = 0;
		for(int time : times) {
			t += time;
			if(t > timeLeft) {
				// no more add
				break;
			} else {
				c++;
			}
		}
		return c;
	}
	
	public static void main(String[] args) {		
		ExamRush e = new ExamRush();
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
        int t = in.nextInt();
        for(int i = 0; i < n; i++){
        	e.add(in.nextInt());
        }
        System.out.println(e.max(t));
        in.close();
	}

}