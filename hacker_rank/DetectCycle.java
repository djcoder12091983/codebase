package hackerrank.interview;



public class DetectCycle {
	
	class Node {
		int data;
        Node next;
	}
	
	boolean hasCycle(Node head) {
		if(head == null) {
			// no data exists
			return false;
		}
		Node hare = head;
		Node tortoise = head;
		// hare moves by two
		hare = hare.next;
		if(hare != null) {
			hare = hare.next;
		}
		// tortoise moves by one
		tortoise = tortoise.next;
		while(hare != null && tortoise != null) {
			if(hare == tortoise) {
				// cycle
				return true;
			}
			// hare moves by two
			hare = hare.next;
			if(hare != null) {
				hare = hare.next;
			} else {
				// hare won't match with tortoise
				return false;
			}
			// tortoise moves by one
			tortoise = tortoise.next;
		}
		return false;
	}

}