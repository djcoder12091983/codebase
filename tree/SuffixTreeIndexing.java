package in.iitkgp.computation.tree;

import java.util.Set;

interface SuffixFinder {
	String[] find(String data);
}

public class SuffixTreeIndexing<T> {
	PrefixTreeIndexing<T> prefixTreeIndexer;
	SuffixFinder suffixFinder;
	
	// see PrefixTreeIndexing.Splitter reference
	public SuffixTreeIndexing(Splitter<T> splitter, SuffixFinder suffixFinder) {
		prefixTreeIndexer = new PrefixTreeIndexing<T>(splitter);
		this.suffixFinder = suffixFinder;
	}
	
	void add(String data) {
		int id = prefixTreeIndexer.add2Master(data);
		String[] suffixes = suffixFinder.find(data);
		for(String suffix : suffixes) {
			prefixTreeIndexer.add(suffix, id);
		}
	}
	
	// id externally managed
	void add(String data, int id) {
		String[] suffixes = suffixFinder.find(data);
		for(String suffix : suffixes) {
			prefixTreeIndexer.add(suffix, id);
		}
	}
	
	Set<String> getData(String suffix) {
		return prefixTreeIndexer.getData(suffix);
	}
	
	Set<Integer> getID(String suffix) {
		return prefixTreeIndexer.getID(suffix);
	}
	
	public static void main(String[] args) {
		SuffixTreeIndexing<Character> p = new SuffixTreeIndexing<Character>(new Splitter<Character>() {
			@Override
			public Character[] split(String data) {
				int l = data.length();
				Character[] tokens = new Character[l];
				for(int i = 0; i < l; i++) {
					tokens[i] = data.charAt(i);
				}
				return tokens;
			}
		}, new SuffixFinder() {
			
			@Override
			public String[] find(String data) {
				int l = data.length();
				String[] suffixes = new String[l];
				for(int i = 0; i< l; i++) {
					suffixes[i] = data.substring(i);
				}
				return suffixes;
			}
		});
		p.add("debasis");
		p.add("xybaxyz");
		Set<String> texts = p.getData("xy");
		for(String text : texts) {
			System.out.println(text);
		}
	}

}