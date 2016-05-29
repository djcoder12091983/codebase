import java.util.*;

// suffix tree
public class SuffixTree {

    int idCounter = 0;
    Map<Integer, String> dictionary = new HashMap<Integer, String>();
    
    class SuffixNode {
        char ch;
        // multiple words may end with same suffix
        List<Integer> wordList = new LinkedList<Integer>();
        
        SuffixNode parent = null;
        Map<Character, SuffixNode> mapping = new HashMap<Character, SuffixNode>(4);
        
        SuffixNode(char ch) {
            this.ch = ch;
        }
        
        void addWordId(int id) {
            // assumed duplicate 
            wordList.add(id);
        }
    }
    
    SuffixNode root = new SuffixNode('R');
    
    // adds word into suffix
    void add(String word) {
    
        boolean exists = findByWord(word);
        if(exists) {
            // no op
            return;
        }
        
        // System.out.println("word: " + word + ", idCounter: " + idCounter);
        
        dictionary.put(idCounter, word);
        
        int l = word.length();
        for(int i = l - 1; i >= 0; i--) {
            String suffix = word.substring(i, l);
            
            // System.out.println("Suffix word: " + suffix);
            
            int l1 = suffix.length();
            
            // adds suffix
            SuffixNode nextNode = root;
            SuffixNode currentNode = root;
            
            int c = 0;
            boolean suffixExists = false;
            while(nextNode != null) {
            
                currentNode = nextNode;
            
                if(c == l1) {
                    // full suffix word exists in tree
                    // mark suffix end with word identifier
                    suffixExists = true;
                    // System.out.println("Suffix word full exists: " + suffix);
                    currentNode.addWordId(idCounter);
                    break;
                }
            
                char ch = suffix.charAt(c++);
                nextNode = currentNode.mapping.get(ch);
            }
            
            if(!suffixExists) {
                c--;
                // add details into suffix tree
                nextNode = currentNode;
                while(c < l1) {
                    
                    char ch = suffix.charAt(c);
                    SuffixNode node = new SuffixNode(ch);
                    node.parent = nextNode;
                    
                    // suffix word ends
                    if(c == l1 - 1) {
                        // word identifier
                        // System.out.println("Word identifier: " + suffix);
                        node.addWordId(idCounter);
                    }
                    
                    nextNode.mapping.put(ch, node);
                    nextNode = node;
                    
                    c++;
                }
            }
        }
        
        idCounter++;
    }
    
    //  finds possible words by suffix id
    List<String> findPossibleWordsBySuffix(String suffix) {
        
        SuffixNode suffixNode = findBySuffix(suffix);
        
        if(suffixNode != null) {
            
            List<String> possibleWords = new LinkedList<String>();
            
            // possible word exists
            // gets all possible word from sub-tree
            List<SuffixNode> nodes = new LinkedList<SuffixNode>();
            nodes.add(suffixNode);
            
            // BFS search to get possibilities
            while(!nodes.isEmpty()) {
                
                List<SuffixNode> nextNodes = new LinkedList<SuffixNode>();
                for(SuffixNode node : nodes) {
                
                    // get possible by word_id
                    List<Integer> wordList = node.wordList;
                    if(!wordList.isEmpty()) {
                        // possible words
                        for(int id : wordList) {
                            String possibleWord = dictionary.get(id);
                            if(possibleWord != null) {
                                possibleWords.add(possibleWord);
                            }
                        }
                    }
                    
                    // mapping
                    nextNodes.addAll(node.mapping.values());
                }
                
                // update next node list
                nodes = nextNodes;
            }
            
            return possibleWords;
            
        } else {
            // blank list
            return new LinkedList<String>();
        }
    }
    
    // finds existence of suffix
    SuffixNode findBySuffix(String suffix) {
        
        SuffixNode nextNode = root;
        
        int l = suffix.length();
        int c = 0;
        boolean found = false;
        while(nextNode != null) {
        
            if(c == l) {
                // full suffix word exists
                found = true;
                break;
            }
        
            char ch = suffix.charAt(c++);
            nextNode = nextNode.mapping.get(ch);
        }
        
        if(found) {
            return nextNode;
        } else {
            return null;
        }
    }
    
    // finds by exact word
    boolean findByWord(String word) {
    
        SuffixNode node = findBySuffix(word);
        
        // System.out.println("word : " + word + ", node: " + node);
        
        if(node != null) {
            // full word exists
            // cross check with word identifier
            List<Integer> wordList = node.wordList;
            // System.out.println("word: " + dictionary.get(id));
            // expected one word
            return wordList.size() == 1 && dictionary.get(wordList.get(0)) != null;
        } else {
            return false;
        }
    }
    
    public static void main(String a[]) {
    
        String words[] = {
            "apple", "application", "apply", "debasis", "asish", "devashri", "tanushree",
            "boy", "ball", "bag", "paromita", "suchismita", "anumita", "anindita", "nandita",
            "failure", "fail", "google", "go", "dog", "doggy", "pass", "passpass", "find", "wifi",
            "mohit", "amit", "sumit", "Debanjan", "Subhanjan", "Nilanjan", "Anjan"
        };
        
        SuffixTree st = new SuffixTree();
        
        // add words
        for(String word : words) {
            st.add(word);
        }
        
        // find words test-cases
        String exactWords[] = {"apple", "fail", "buy", "sell", "fear", "passpa", "find", "wifi"};
        String suffixWords[] = {"fi", "ppl", "ba", "fail", "go", "mit", "mita", "sh", "jan"};
        
        // exact find
        System.out.println("\n------------Exact word find test-cases-------------\n");
        for(String word : exactWords) {
            System.out.println(word + " found exactly: " + st.findByWord(word));
        }
        
        // suffix find
        System.out.println("\n\n------------Suffix word find test-cases-------------\n");
        for(String word : suffixWords) {
        
            List<String> possibleWords = st.findPossibleWordsBySuffix(word);
            
            System.out.print(word + " suffix possible words: [");
            for(String possibleWord : possibleWords) {
                System.out.print(possibleWord + " ");
            }
            System.out.println("]");
        }
    }
}