package in.iitkgp.computation.ir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * search engine using inverted index
 * exact keyword match (disjunctive|conjunctive)
 *  
 */
public class InveretdIndexSearchEngine {
	
	// Note: This class should use an abstraction
	
	// input location where raw documents are stored
	String input_location;
	
	// assumed number of unique keywords in all documents will not be too-high
	// this is inverted index (words vs list of document_id)
	// instead of using Set<document_name>(avoid duplicate documents) it's better to use document_id
	// and each document mapped with document_id to get document detail for a given document_id
	Map<String, Set<Long>> inveretd_index_detail = new HashMap<String, Set<Long>>();
	
	// document mapping with document_id as discussed earlier
	// here if the document numbers are too high then in that case we should use tree_map (log N search)
	Map<Long, DocumentDetail> document_mapping = new HashMap<Long, DocumentDetail>();
	
	public InveretdIndexSearchEngine(String input_location) throws Exception {
		this.input_location = input_location;
		
		// mapping
		prepareDocumentMapping();
	}
	
	// prepare document mapping with document_id
	// and keywords mapping with inverted-indexing
	private void prepareDocumentMapping() throws Exception {
		
		File files[] = new File(input_location).listFiles();
		
		long doc_id = 1;
		
		// iterate each document to find keywords
		for(File f : files) {
			
			String doc_name = f.getName();
			System.out.println("Indexing .... " + doc_name);
			
			// mapping of doc_id vs document detail
			document_mapping.put(doc_id, new DocumentDetail(doc_id, doc_name));
			
			BufferedReader reader = null;
			char[] buffer = new char[1024];
			
			try {
				
				StringBuilder keyword = new StringBuilder();
				
				reader = new BufferedReader(new FileReader(f));
				while(true) {
					
					int r = reader.read(buffer);
					
					if(r == -1) {
						// EOF
						break;
					}
					
					// process chars and gets keyword
					for(int i = 0; i < r; i++) {
						
						char ch = buffer[i];
						// accept (numeric and eng-alphabate)
						if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
							keyword.append(ch);
						} else {
							// other than this it's word break
							
							// add to index table
							if(keyword.length() > 0) {
								addToIndexTable(keyword.toString(), doc_id);
							}
							
							// reset
							keyword = new StringBuilder();
						}
					}
				}
				
				// handle last word
				// add to index table
				if(keyword.length() > 0) {
					addToIndexTable(keyword.toString(), doc_id);
				}
			
			} finally {
				
				if(reader != null) {
					reader.close();
				}
			}
			
			doc_id++;
		}
	}
	
	// add keyword to index table
	private void addToIndexTable(String keyword, long doc_id) {
		
		// map the keyword with document_id
		Set<Long> mapping_dtl = inveretd_index_detail.get(keyword);
		if(mapping_dtl == null) {
			// first time entry
			mapping_dtl = new HashSet<Long>();
			inveretd_index_detail.put(keyword, mapping_dtl);
		}
		
		mapping_dtl.add(doc_id);
	}
	
	/**
	 * Search query for a given set keywords and a search option (disjunctive|conjunctive)
	 * @param keywords given keywords for which search query is done
	 * @param option Whether it's disjunctive or conjunctive
	 * @return Set(unique documents) of found documents
	 */
	public List<DocumentDetail> query(String[] keywords, SearchOption option) {
		
		List<DocumentDetail> final_results = new LinkedList<DocumentDetail>();
		
		if(keywords == null) {
			throw new NullPointerException("Keywords not given");
		}
		
		if(keywords.length == 0) {
			// empty keyword
			throw new IllegalArgumentException("No keywrods given");
		}

		Set<Long> full_results = new HashSet<Long>();
		
		switch(option) {
		
		case CONJUNCTIVE:
			// conjunctive query
			// all words exist in documents
			
			int c = 0;
			for(String keyword : keywords) {
				
				Set<Long> sub_results = inveretd_index_detail.get(keyword);
				
				if(sub_results != null) {
					// if found
					// in disjunctive query all set results will have intersection
					
					if(c == 0) {
						
						// for first keyword (search found) have the full result set
						full_results = sub_results;
						
					} else {
						
						// rather than first keyword
						
						Set<Long> intersection_results = new HashSet<Long>();
						
						for(long doc_id : sub_results) {
							
							if(full_results.contains(doc_id)) {
								// intersection
								intersection_results.add(doc_id);
							}
						}
						
						// intersection results
						full_results = intersection_results;
					}
					
					c++;
				}
			}
			
			break;
		
		case DISJUNCTIVE:
			// disjunctive query
			// any of the keywords exists in document
			
			for(String keyword : keywords) {
				
				Set<Long> sub_results = inveretd_index_detail.get(keyword);
				
				if(sub_results != null) {
					// if found
					// in conjunctive query all set results will be added up
					full_results.addAll(sub_results);
				}
			}
			
			break;
			
		default:
			break;
		}
		
		// gets the document detail from found result set
		for(long doc_id : full_results) {
			
			DocumentDetail dd = document_mapping.get(doc_id);
			final_results.add(dd);
		}
		
		return final_results;
	}
	
	// main function
	public static void main(String args[]) throws Exception {
		
		String input_location = "/home/dspace/debasis/IR_assignment/alldocs";
		InveretdIndexSearchEngine engine = new InveretdIndexSearchEngine(input_location);
		
		String query_input_file = "/home/dspace/debasis/IR_assignment/query.txt";
		BufferedReader query_f = null;
		
		String output_location = "/home/dspace/debasis/IR_assignment/java_result";
		BufferedWriter conjunctive_results_f = null;
		BufferedWriter disjunctive_results_f = null;
		
		try {
			
			query_f = new BufferedReader(new FileReader(query_input_file));
			
			// results file
			conjunctive_results_f = new BufferedWriter(new FileWriter(new File(output_location, "conjunctive_results.out")));
			disjunctive_results_f = new BufferedWriter(new FileWriter(new File(output_location, "disjunctive_results.out")));
			
			String l = null;
			while((l = query_f.readLine()) != null) {
				
				System.out.print("Querying ... " + l);
				
				String[] tokens = l.split(" +");
				
				// skip invalid input
				int tl = tokens.length;
				if(tl == 1) {
					continue;
				}
				
				String keywords[] = new String[tokens.length - 1];
				// first token is a query ID
				for(int i = 1; i < tl; i++) {
					keywords[i - 1] = tokens[i];
				}
				
				// conjunctive results
				List<DocumentDetail> conjunctive_results = engine.query(keywords, SearchOption.CONJUNCTIVE);
				
				System.out.print(" [Conjunctive results found: " + conjunctive_results.size() + "]");
				
				// write
				writeResults(conjunctive_results_f, tokens[0], conjunctive_results);
				
				// disjunctive results
				List<DocumentDetail> disjunctive_results = engine.query(keywords, SearchOption.DISJUNCTIVE);
				
				System.out.print(" [Disjunctive results found: " + disjunctive_results.size() + "]");
				
				// write
				writeResults(disjunctive_results_f, tokens[0], disjunctive_results);
				
				System.out.println();
			}
		
		} finally {
			
			if(query_f != null) {
				query_f.close();
			}
			
			if(conjunctive_results_f != null) {
				conjunctive_results_f.close();
			}
			
			if(disjunctive_results_f != null) {
				disjunctive_results_f.close();
			}
		}
		
	}
	
	// write results into out file
	static void writeResults(BufferedWriter f, String query_id, List<DocumentDetail> results) throws Exception {
		
		for(DocumentDetail dd : results) {
			f.write(query_id + " " + dd.document_name);
			f.newLine();
		}
	}
}

enum SearchOption {
	
	CONJUNCTIVE,
	DISJUNCTIVE
}

class DocumentDetail {
	
	long document_id;
	String document_name;
	// may be some other meta-data encapsulation if required
	
	public DocumentDetail(long document_id, String document_name) {
		this.document_id = document_id;
		this.document_name = document_name;
	}
}
