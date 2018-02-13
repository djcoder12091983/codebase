// for simplicity shake all classes are written here instead of having separate file
// default package

import java.util.*;
import java.io.*;

/**
 * This class handles sentiment analysis using naive bayes (a generative model)
 * It takes an input file and splits the whole data into USER_DEFINED ratio for training and testing data
 * 
 * Note: For testing purpose we can verify the accuracy as clazz are already there
 */
public class SentimentAnalysis {
	
	static final double TRAINING_DATA_FACTOR = 0.9;
	
	// full corpus file name
	String fullCorpusFile;
	String logLocation;
	
	// sentiment clazz trained data details (likelihood of words/features etc.)
	List<SentimentClazzData> trainDetails = new LinkedList<SentimentClazzData>();
	// prpeared data from given data set
	Data preparedDataSet;
	// assumed at most 4 sentiment classes
	// P(class)
	Map<String, Double> clazzLikelihoodMapping = new HashMap<String, Double>(4);
	
	// constructor
	SentimentAnalysis(String fullCorpusFile, String logLocation) {
		this.fullCorpusFile = fullCorpusFile;
		this.logLocation = logLocation;
	}
	
	// main program
	public static void main(String args[]) throws Exception {
		
		String corpusFile = "/home/debasis/debasis/IR/sentiment_analysis/data/FullCorpus.csv";
		String logLocation = "/home/debasis/debasis/IR/sentiment_analysis/output/";
		
		SentimentAnalysis analysis = new SentimentAnalysis(corpusFile, logLocation);
		
		// training
		analysis.train();
		
		// testing
		analysis.test();
		
		System.out.println();
		System.out.println();
		System.out.println("Done!!");
	}
	
	// test data
	// throws exception in case of file writing error
	void test() throws IOException {
		
		System.out.println();
		System.out.println();
		
		System.out.println("Testing starts .....");
		
		System.out.println();
		System.out.println();
		
		Map<String, List<String>> testDataList = preparedDataSet.getTestData();
		
		System.out.println("Test data size: " + preparedDataSet.getTestDataSize() + ".");
		
		// int limit = 10;
		
		Set<String> clazzs = testDataList.keySet();
		for(String clazz : clazzs) {
			
			/*if(clazz.equals("neutral")) {
				// testing purpose
				// TODO to be removed
				continue;
			}*/
			
			// log writer for each class
			String fileName = clazz + "_output.log";
			BufferedWriter logWriter = new BufferedWriter(new FileWriter(new File(logLocation, fileName)));
			
			int truePositive = 0;
			
			// likeliehood estimation of each test tesxt
			
			List<String> textList = testDataList.get(clazz);
			// int count = 0;
			for(String text : textList) {
				
				// TODO to be removed
				// System.out.println(text);
				// System.out.println("Originally: " + clazz);
				
				logWriter.write(text);
				logWriter.newLine();
				
				// words/features
				List<String> words = splitWords(text);
				
				double mle = 0;
				String selectedClazz = null;
				// gets all likeliehood for all available sentiment classes
				for(SentimentClazzData clazzData : trainDetails) {
					
					String trainedClazz = clazzData.clazz;
					
					// TODO to be removed
					// System.out.println("--------------------------------" + trainedClazz + "--------------------------");
					
					// P(clazz)
					double clazzLikelihood = clazzLikelihoodMapping.get(trainedClazz);
					
					// P(text|clazz)
					double textLikelihood = 1;
					for(String word : words) {
						double likelihood = clazzData.likelihoodOfWord(word);
						
						textLikelihood = textLikelihood*likelihood;
					}
					
					// likelihood estimation, P(text|class)*P(class)
					double le = clazzLikelihood*textLikelihood;
					
					// log each class likelihood estimation
					logWriter.write("Class[" + trainedClazz + "]: " + String.format("%.20g", le));
					logWriter.newLine();
					
					if(le > mle) {
						// maximum likelihood estimation
						mle = le;
						// selected class
						selectedClazz = trainedClazz;
					}
				}
				
				// TODO to be removed
				// System.out.println("Selected Class: " + selectedClazz);
				
				// write test output
				logWriter.write("Selected Class: " + selectedClazz);
				logWriter.newLine();
				logWriter.newLine();
				
				if(clazz.equals(selectedClazz)) {
					// matched with original data
					truePositive++;
				}
				
				/*if(++count == limit) {
					// testing purpose
					// TODO to be removed
					break;
				}*/
			}
			
			int s = textList.size();
			String result = "Class: " + clazz + ". Found data: " + s
							+ ". Accurate result: " + truePositive
							+ ". Accuracy: " + String.format("%.2g", (truePositive/new Double(s))*100) + "%.";
			logWriter.newLine();
			logWriter.write(result);
			
			logWriter.close();
			
			System.out.println(result);
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("Testing ends .....");
	}
	
	// train data
	// throws @IOException in case if training data access error occurs
	void train() throws IOException {
		
		System.out.println("Training starts .....");
		long startTime = System.currentTimeMillis();
		
		System.out.println();
		System.out.println();
		
		// assumptions
		// 1. only english words considered, no emotion signs considered (TODO)
		// but emotion signs should be part of analysis because it takes part important role in sentiments
		// 2. it normalizes the word (gets converted to lowercase)
		// 3. word separation is done based on any non english character, a sequential english characters treated as word/feature
		// 4. TODO: need to analyze whether a numeric value can take part in sentiment analysis or not
		// 5. TODO: negation is not handled. a simple logic can be applied if a word is like NOT(n't etc.)
		// then all subsequent words will be PREFIXED as NOT_ till a punctuation 
		// 6. All words are taking part in analysis
		
		// prepare data first
		preparedDataSet = prepareData();
		
		System.out.println("All data size: " + preparedDataSet.getAllDataSize() + ".");
		System.out.println("Training data size: " + preparedDataSet.getTrainingDataSize() + ".");
		System.out.println("Test data size: " + preparedDataSet.getTestDataSize() + ".");
		
		// sentiment clazzs
		Set<String> clazzs = preparedDataSet.getClazzs();
		for(String clazz : clazzs) {
			
			// class likelihood calculation
			clazzLikelihoodMapping.put(clazz, preparedDataSet.likelihoodOfClazz(clazz));
			
			// details
			SentimentClazzData detail = new SentimentClazzData(clazz);
			
			// gets text details by class
			List<String> textList = preparedDataSet.getTextList(clazz);
			
			System.out.println("Class: " + clazz + " is being processed.");
			
			// extract words/features from each text
			int wc = 0;
			for(String text : textList) {
				
				List<String> words = splitWords(text);
				// add words to dictionary for a given sentiment class
				for(String word : words) {
					detail.addWord(word);
					
					wc++;
				}
			}
			
			// save details
			trainDetails.add(detail);
			
			System.out.println("Class: " + clazz + " processed with " + wc + " words.");
		}
		
		System.out.println();
		System.out.println();
		
		long endTime = System.currentTimeMillis();
		System.out.println("Training ends in " + (endTime - startTime)/1000 + " seconds");
		
	}
	
	// prpeare data from full corpus
	Data prepareData() throws IOException {
		
		BufferedReader corpusReader = null;
		Data data = new Data();
		
		try {
			corpusReader = new BufferedReader(new FileReader(fullCorpusFile));
			
			// skip first line (headers)
			String line=corpusReader.readLine();
			
			while((line=corpusReader.readLine()) != null) {
				
				if(line.length() == 0) {
					// skip blank line
					continue;
				}
				
				// handle multiple line
				StringBuilder modifiedLine = new StringBuilder();
				if(line.charAt(0) == '\"') {
					// line starts
					modifiedLine.append(line);
					
					int l = line.length();
					if(line.charAt(l-1) != '\"') {
						// multiple line
						while((line=corpusReader.readLine()) != null) {
							// space separated multiline
							modifiedLine.append(" ").append(line);
							
							l = line.length();
							if(l == 0) {
								// skip blank line
								continue;
							}
							
							if(line.charAt(l-1) == '\"') {
								// break (multiple line CSV contents
								break;
							}
						}
					}
				}
				
				line = modifiedLine.toString();
				int l = line.length();
				
				String tokens[] = line.substring(1, l - 1).split("\",\"");
				
				String clazz = tokens[1];
				if(clazz.equals("irrelevant")) {
					// filter data
					continue;
				}
				
				String text = tokens[4];
				
				// add data
				data.add(new String[]{clazz, text});
			}
		} finally {
			// close the resource
			if(corpusReader != null) {
				corpusReader.close();
			}
		}
		
		// split training and test data
		data.split();
		
		return data;
	}
	
	// split words
	List<String> splitWords(String text) {
		
		List<String> words = new LinkedList<String>();
		
		StringBuilder word = new StringBuilder();
		int l = text.length();
		for(int i = 0; i < l; i++) {
			char ch = text.charAt(i);
			
			boolean upperCase = ch >= 'A' && ch <= 'Z';
			boolean lowerCase = ch >= 'a' && ch <= 'z';
			if(upperCase || lowerCase) {
				// allow english character only
				// normalize to lowercase
				if(upperCase) {
					ch = (char)(ch + 32);
				}
				
				word.append(ch);
			} else {
				// other than english character treat as word separator
				int wl = word.length();
				if(wl > 0) {
					
					// don't consider http or htps URL(s)
					String w = word.toString();
					if(w.equals("http") || w.equals("https")) {
						
						// reset word for next word processing
						word = new StringBuilder();
						
						for(;i < l; i++) {
							ch = text.charAt(i);
							// till space
							if(ch == ' ') {
								break;
							}
						}
						
						continue;
					}
					
					// a word/feature found
					words.add(w);
					
					// reset word for next word processing
					word = new StringBuilder();
				}
			}
		}
		
		// handle last word
		int wl = word.length();
		if(wl > 0) {
			String w = word.toString();
			// a word/feature found
			words.add(w);
		}
		
		return words;
	}
}

// helper classes

// encapsulates sentiment class data
class SentimentClazzData {
	
	String clazz;
	// per word vs count
	Map<String, Long> dictionary = new HashMap<String, Long>();
	
	int fullWordsLength = 0;
	int dictionarySize = 0;
	
	SentimentClazzData(String clazz) {
		this.clazz = clazz;
	}
	
	// add word to dictionary of current sentiment clazz
	void addWord(String word) {
		// add word to dictionary
		Long count = dictionary.get(word);
		if(count == null) {
			// intial count
			dictionary.put(word, new Long(1));
			
			dictionarySize++;
		} else {
			// add count
			dictionary.put(word, count.longValue() + 1);
		}
		
		fullWordsLength++;
	}
	
	// probability of given word/feature of current sentiment clazz
	// if a new word occurs in a text segment then for currnt sentiment clazz
	// the probability will be 0 (whole probability product will be 0)
	// so to avoid that we will simply implement add-one smoothing (count is exactly+1 than what we see)
	double likelihoodOfWord(String word) {
		Long count = dictionary.get(word);
		
		long addOneCount = 0;
		if(count == null) {
			// may happen as explained earlier
			addOneCount = 1;
		} else {
			addOneCount = count.longValue() + 1;
		}
		
		// testing purpose
		// TODO to be removed
		// System.out.println("Word: " + word + ", Count: " + addOneCount);
		
		// here also in denominator part it adss (|dictionary| + 1)
		return addOneCount/new Double(fullWordsLength + dictionarySize + 1);
	}
}

// encapsulates training and testing data
class Data {
	
	// all filtered data
	// assumed at most 4 sentiment classes
	Map<String, List<String>> allData = new HashMap<String, List<String>>(4);
	int allDataSize = 0;
	int trainingDataSize = 0;
	int testDataSize = 0;
	
	int getAllDataSize() {
		return allDataSize;
	}
	
	int getTrainingDataSize() {
		return trainingDataSize;
	}
	
	int getTestDataSize() {
		return testDataSize;
	}
	
	// add data
	void add(String[] tokens) {
		String clazz = tokens[0];
		String text = tokens[1];
		
		List<String> dataList = allData.get(clazz);
		if(dataList == null) {
			// first time
			dataList = new LinkedList<String>();
			allData.put(clazz, dataList);
		}
		
		dataList.add(text);
	}
	
	void split() {
		// split all data @ USER_DEFINED ratio (training and test data respectively)
		Set<String> clazzs = allData.keySet();
		for(String clazz : clazzs) {
		
			// class-wise USER_DEFINED data ratio
			List<String> dataList = allData.get(clazz);
			int s = dataList.size();
			int trainingDataLength = (int)(s * SentimentAnalysis.TRAINING_DATA_FACTOR);
			// trainig data
			for(int i = 0; i < trainingDataLength; i++) {
				String text = dataList.get(i);
				addTrainingData(clazz, text);
				
				trainingDataSize++;
				allDataSize++;
			}
			
			// test data
			for(int i = trainingDataLength; i < s; i++) {
				String text = dataList.get(i);
				addTestData(clazz, text);
				
				testDataSize++;
				allDataSize++;
			}
		}
	}
	
	// this maps hold sentiment class vs corresponding text list
	// assumed at most 4 sentiment class
	// for testing purpose we can verify the accuracy as clazz are already there
	Map<String, List<String>> trainingData = new HashMap<String, List<String>>(4);
	Map<String, List<String>> testData = new HashMap<String, List<String>>(4);
	
	// add training data
	void addTrainingData(String clazz, String text) {
		List<String> dataList = trainingData.get(clazz);
		if(dataList == null) {
			// first time
			dataList = new LinkedList<String>();
			trainingData.put(clazz, dataList);
		}
		
		dataList.add(text);
	}
	
	// add test data
	void addTestData(String clazz, String text) {
		List<String> dataList = testData.get(clazz);
		if(dataList == null) {
			// first time
			dataList = new LinkedList<String>();
			testData.put(clazz, dataList);
		}
		
		dataList.add(text);
	}
	
	Map<String, List<String>> getTestData() {
		return testData;
	}
	
	Set<String> getClazzs() {
		return trainingData.keySet();
	}
	
	List<String> getTextList(String clazz) {
		return trainingData.get(clazz);
	}
	
	// probability of given sentiment class
	double likelihoodOfClazz(String clazz) {
		List<String> textList = trainingData.get(clazz);
		if(textList == null) {
			// won't happen(new sentiment)
			return new Double(0);
		} else {
			// how likely the sentiment class occurs
			// count(clazz)/total_count
			return textList.size()/new Double(trainingDataSize);
		}
	}
}
