package hackerearth.hiring.challenges.date11022018.niyo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class Mobile {
	int id;
	String os;
	int ram;
	int memory;
	int price;
	int rating;
	
	public Mobile(int id, String os, int ram, int memory, int price, int rating) {
		this.id = id;
		this.os = os;
		this.ram = ram;
		this.memory = memory;
		this.price = price;
		this.rating = rating;
	}
}

public class MobileSelection {
	
	int id = 0;
	
	Map<Integer, Mobile> masterData = new HashMap<Integer, Mobile>();
	Map<String, Set<Integer>> osMapping = new HashMap<String, Set<Integer>>();
	// range query
	TreeMap<Integer, Set<Integer>> ramMapping = new TreeMap<Integer, Set<Integer>>();
	TreeMap<Integer, Set<Integer>> memoryMapping = new TreeMap<Integer, Set<Integer>>();
	TreeMap<Integer, Set<Integer>> priceMapping = new TreeMap<Integer, Set<Integer>>();
	
	void add(String os, int ram, int memory, int price, int rating) {
		id++;
		Mobile mobile = new Mobile(id, os, ram, memory, price, rating);
		masterData.put(id, mobile);
		// add index
		textIndex(os, id, osMapping);
		numericIndex(ram, id, ramMapping);
		numericIndex(memory, id, memoryMapping);
		numericIndex(price, id, priceMapping);
	}
	
	void textIndex(String value, int id, Map<String, Set<Integer>> index) {
		Set<Integer> values = index.get(value);
		if(values == null) {
			values = new HashSet<Integer>(2); // start with 2
			index.put(value, values);
		}
		
		values.add(id);
	}
	
	void numericIndex(int value, int id, Map<Integer, Set<Integer>> index) {
		Set<Integer> values = index.get(value);
		if(values == null) {
			values = new HashSet<Integer>(2); // start with 2
			index.put(value, values);
		}
		
		values.add(id);
	}
	
	int find(String os, int ram, int memory, int price) {
		
		int bestPrice = -1;
		
		Set<Integer> osMobiles = osMapping.get(os);
		Collection<Set<Integer>> ramMobiles = ramMapping.subMap(ram, true, Integer.MAX_VALUE, false).values();
		Collection<Set<Integer>> memoryMobiles = memoryMapping.subMap(memory, true, Integer.MAX_VALUE, false).values();
		Collection<Set<Integer>> priceMobiles = priceMapping.subMap(0, false, price, true).values();
		
		int bestRating = -1;
		for(int id : osMobiles) {
			
			if(contains(id, ramMobiles) && contains(id, memoryMobiles) && contains(id, priceMobiles)) {
				// all criteria full filled
				Mobile mobile = masterData.get(id);
				int rating = mobile.rating;
				if(rating > bestRating) {
					bestPrice = mobile.price;
					bestRating = rating;
				}
			}
		}
		
		return bestPrice;
	}
	
	boolean contains(int id, Collection<Set<Integer>> mobiles) {
		for(Set<Integer> subSet : mobiles) {
			if(subSet.contains(id)) {
				// found
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		
		MobileSelection ms = new MobileSelection();
		
		ms.add("windows", 2, 32, 100, 100);
		ms.add("windows", 4, 64, 10, 52);
		ms.add("android", 2, 32, 56, 9);
		ms.add("ios", 2, 32, 20, 63);
		ms.add("windows", 2, 32, 452, 50);
		
		System.out.println(ms.find("windows", 2, 32, 500));
		System.out.println(ms.find("ios", 4, 32, 100));
	}
	
}