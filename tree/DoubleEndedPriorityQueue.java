package in.iitkgp.computation.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class QueueElement<K, E extends Comparable<E>> {	
	K key;
	E element;
	
	public QueueElement(E element) {
		this.element = element;
	}
	
	public QueueElement(K key, E element) {
		this.key = key;
		this.element = element;
	}
}

// elements mapped by key, OPTIONAL
public class DoubleEndedPriorityQueue<K, E extends Comparable<E>> {
	static short MIN = 0;
	static short MAX = 1;
	static short BOTH = 2;
	
	Map<K, E> elementsMap = new HashMap<K, E>(); // optional
	List<List<QueueElement<K, E>>> elements = new ArrayList<List<QueueElement<K, E>>>(2);	
	List<Map<K, Integer>> elementsPosition = new ArrayList<Map<K, Integer>>(2); // optional
	short queueType = BOTH; // default
	
	public DoubleEndedPriorityQueue(short type) {
		if(type == BOTH) {
			this.elements.add(new ArrayList<QueueElement<K, E>>());
			this.elements.add(new ArrayList<QueueElement<K, E>>());
			this.elementsPosition.add(new HashMap<K, Integer>());
			this.elementsPosition.add(new HashMap<K, Integer>());
		} else {
			if(type == MAX) {
				// NULL element
				this.elements.add(null);
				this.elementsPosition.add(null);
			}
			this.elements.add(new ArrayList<QueueElement<K, E>>());
			this.elementsPosition.add(new HashMap<K, Integer>());
		}
		queueType = type;
	}
	
	public DoubleEndedPriorityQueue() {
		this(BOTH);
	}
	
	public DoubleEndedPriorityQueue(List<QueueElement<K, E>> elements, short type) {
		int l = elements.size();
		if(type == BOTH) {
			// both
			this.elements.add(new ArrayList<QueueElement<K, E>>(l));
			this.elements.add(new ArrayList<QueueElement<K, E>>(l));
			this.elementsPosition.add(new HashMap<K, Integer>());
			this.elementsPosition.add(new HashMap<K, Integer>());
		} else {
			// particular
			if(type == MAX) {
				this.elements.add(null); // NULL element
				this.elementsPosition.add(null);
			}
			this.elements.add(new ArrayList<QueueElement<K, E>>(l));
			this.elementsPosition.add(new HashMap<K, Integer>());
		}
		
		int i = 0;
		for(QueueElement<K, E> element : elements) {
			K k = element.key;
			E e = element.element;	
			elementsMap.put(k, e);
			if(type == BOTH) {
				// both
				elementsPosition.get(0).put(k, i);
				elementsPosition.get(1).put(k, i);
				this.elements.get(0).add(element);
				this.elements.get(1).add(element);
			} else {
				// particular
				this.elements.get(type).add(element);
			}
			
			i++;
		}
		queueType = type;
		// build queue
		if(type == BOTH) {
			buildQueue(MIN);
			buildQueue(MAX);
		} else {
			// particular
			buildQueue(type);
		}
	}
	
	public DoubleEndedPriorityQueue(List<QueueElement<K, E>> elements) {
		this(elements, BOTH);
	}
	
	QueueElement<K, E> get(int index, short type) {
		int size = elements.get(type).size();
		if(index >= size) {
			// invalid index;
			return null;
		}
		return elements.get(type).get(index);
	}
	
	int compare(E first, E second, short type) {
		int c = first.compareTo(second);
		if(c == 0) {
			// min flag OPTIONAL
			return c;
		}
		return type == MIN ? c : -c;
	}
	
	// min/max
	void moveDown(int index, short type) {
		int size = elements.get(type).size();
		int leftIndex = 2*index + 1;
		if(leftIndex >= size) {
			// no further operation
			return;
		}
		
		QueueElement<K, E> e = get(index, type);
		QueueElement<K, E> l = get(leftIndex, type);		
		int updatedIndex = index;
		int c = compare(e.element, l.element, type);
		if(c > 0) {
			// exchange
			updatedIndex = leftIndex;
			e = l;
		}
		int rightIndex = 2*index + 2;
		if(rightIndex < size) {
			// valid right index
			QueueElement<K, E> r = get(rightIndex, type);
			c = compare(e.element, r.element, type);
			if(c > 0) {
				// exchange
				updatedIndex = rightIndex;
			}
		}
		if(index != updatedIndex) {
			// need to exchange a recursion
			exchange(index, updatedIndex, type);
			// recursion
			moveDown(updatedIndex, type);
		}
	}
	
	void exchange(int index1, int index2, short type) {
		QueueElement<K, E> old = get(index1, type);
		QueueElement<K, E> oldNext = get(index2, type);
		QueueElement<K, E> t = get(index1, type);
		elements.get(type).set(index1, elements.get(type).get(index2));
		elements.get(type).set(index2, t);
		// positions update
		Map<K, Integer> positions = elementsPosition.get(type);
		int p = positions.get(old.key);
		positions.put(old.key, positions.get(oldNext.key));
		positions.put(oldNext.key, p);
	}
	
	// min/max
	void buildQueue(short type) {
		int size = elements.get(type).size();
		for(int i = size/2; i >= 0; i--) {
			moveDown(i, type); // bottom up
		}
	}
	
	E peek(short type) {
		if(elements.get(type).size() > 0) {
			return get(0, type).element;
		} else {
			// no such element
			return null; // assumed no NULL element can be inserted
		}
	}
	
	// particular
	E peek() {
		return peek(queueType);
	}
	
	E extract(short type) {
		int l = elements.get(type).size();
		if(l > 0) {
			QueueElement<K, E> first = get(0, type);
			// re-arrange
			elements.get(type).set(0, elements.get(type).get(l - 1));
			elements.get(type).remove(l - 1);
			elementsPosition.get(type).remove(first.key);
			elementsMap.remove(first.key);
			moveDown(0, type);
			return first.element;
		} else {
			// no such element
			return null; // assumed no NULL element can be inserted
		}
	}
	
	E extract() {
		return extract(queueType);
	}
	
	void add(QueueElement<K, E> element) {
		if(queueType == BOTH) {
			// BOTH
			add(element, MIN);
			add(element, MAX);
		} else {
			// particular
			add(element, queueType);
		}
	}
	
	void add(QueueElement<K, E> element, short type) {
		Map<K, Integer> positions = elementsPosition.get(type);
		K k = element.key;
		Integer p = positions.get(k);
		if(p != null) {
			// exists
			update(p, element, type, false);
		} else {
			// new
			List<QueueElement<K, E>> data = elements.get(type);
			int s = data.size() - 1;
			elementsMap.put(k, element.element);
			positions.put(k, s);
			data.add(element);
			update(s, element, type, true);
		}
	}
	
	void moveUp(int index, short type) {
		List<QueueElement<K, E>> data = elements.get(type);
		int parentIndex = new Double(Math.ceil(index/2)).intValue() - 1;
		while(parentIndex >= 0) {
			if(compare(data.get(index).element, data.get(parentIndex).element, type) < 0) {
				// repeat
				exchange(index, parentIndex, type);
				index = parentIndex;
				parentIndex = new Double(Math.ceil(index/2)).intValue() - 1;
			} else {
				// balanced, stop
				break;
			}
		}
	}
	
	// update by position
	void update(int index, QueueElement<K, E> updated, short type, boolean add) {
		if(add) {
			// always move upwards
			moveUp(index, type);
		} else {
			// need to decide
			QueueElement<K, E> old = elements.get(type).get(index);
			boolean up = compare(updated.element, old.element, type) < 0;
			elements.get(type).set(index, updated);
			if(up) {
				// move up
				moveUp(index, type);
			} else {
				// move down
				moveDown(index, type);
			}	
		}
	}
	
	public static void main(String[] args) {
		
		List<QueueElement<String, Integer>> elements = new ArrayList<QueueElement<String, Integer>>();
		elements.add(new QueueElement<String, Integer>("J", 98));
		elements.add(new QueueElement<String, Integer>("A", 10));
		elements.add(new QueueElement<String, Integer>("B", 20));
		elements.add(new QueueElement<String, Integer>("C", 15));
		elements.add(new QueueElement<String, Integer>("D", 30));
		elements.add(new QueueElement<String, Integer>("E", 40));
		elements.add(new QueueElement<String, Integer>("F", 50));
		elements.add(new QueueElement<String, Integer>("G", 44));
		elements.add(new QueueElement<String, Integer>("H", 100));
		elements.add(new QueueElement<String, Integer>("I", 99));
		
		DoubleEndedPriorityQueue<String, Integer> queue = new DoubleEndedPriorityQueue<String, Integer>(elements);
		//System.out.println("Min: " + queue.peek(MIN));
		//System.out.println("Max: " + queue.peek(MAX));
		/*System.out.println("Min: " + queue.extract(MIN));
		System.out.println("Max: " + queue.extract(MAX));*/
		queue.add(new QueueElement<String, Integer>("A", 105));
		/*System.out.println("Min: " + queue.extract(MIN));
		System.out.println("Max: " + queue.extract(MAX));*/
		//System.out.println("Min: " + queue.extract(MIN));
		Integer e;
		while((e = queue.extract(MIN)) != null) {
			System.out.println(e);
		}
	}
}