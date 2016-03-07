/*  Assignment 4: Word Ladder
 *  Name: Rikin Tanna, Stefan Bordovsky
 *  UTEID: rrt575, sb39782
 */

package assignment4;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// do not change class name or interface it implements
public class WordLadderSolver implements Assignment4Interface
{
    // declare class members here.
	// Dictionary lets us search for valid 5-letter words in constant time.
	public HashMap<String, String> dictionary;
	
	// Maintaining a solutions HashMap lets us check for existence of solution
	// in constant time rather than O(n^2) time.
	public HashMap<String, String> solutions;

    // add a constructor for this object. HINT: it would be a good idea to set up the dictionary there
	public WordLadderSolver(){
		dictionary = new HashMap<String, String>();
		solutions = new HashMap<String, String>();
	}

    // do not change signature of the method implemented from the interface
    @Override
    public List<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException 
    {
        List<String> solutions = new ArrayList<String>();
        solutions.add(startWord);
        addSolution(startWord);
    	List <String> result = makeLadder(startWord, endWord, 0, solutions);
    	clearSolutions();
    	return result;
        //throw new UnsupportedOperationException("Not implemented yet!");
    }
    
    @Override
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) 
    {	
    	// return false if word ladder is empty, start word is not the first word, or end word is not the last word
    	if((wordLadder == null) ||wordLadder.isEmpty()
    			|| !startWord.equals(wordLadder.get(0)) 
    			|| !endWord.equals(wordLadder.get(wordLadder.size() - 1))){
    		return false;
    	}
    	// check to see if each word is one letter different than next
    	if(wordLadder.size() > 1){
    		for(int x = 0; x < wordLadder.size() - 1; x++){
    			if(numDifferentChars(wordLadder.get(x), wordLadder.get(x+1)) > 1){
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }


    // add additional methods here
    public List<String> makeLadder(String startWord, String endWord,
    		int changeIndex, List<String> solutions){
    	List<String> result = new ArrayList<String>();
    	if(startWord.equals(endWord)){		// start word = end word
    		solutions.add(endWord);
    		return solutions;
    	}
        if(numDifferentChars(startWord, endWord) == 1){
        	solutions.add(endWord);
        	return solutions;
        }
        
        String letters = "abcdefghijklmnopqrstuvwxyz";
        List<String> possibles = new ArrayList<String>();
        // Initial for loop used to change single letters of old word to make new
        // words for recursive branching.
        for(int wordIndex = 0; wordIndex < 5; wordIndex++){
        	if(wordIndex == changeIndex){
        		// If wordIndex = changeIndex, that letter was just changed. Shouldn't
        		// be changed this time.
        		wordIndex++;
        		if(wordIndex == 5){ // Don't iterate beyond 5 letter limit.
        			break;
        		}
        	}
        	char check = startWord.charAt(wordIndex);
        	for(int alphaIndex = 0; alphaIndex < letters.length(); alphaIndex++){
        		if(check != letters.charAt(alphaIndex)){ // Ensures we make new word.
        			int buildIndex = 0; // Index of new word to be made via concatenation.
        			String test = "";
        			while(buildIndex != wordIndex){ // WordIndex is position of char to change.
        				test += startWord.charAt(buildIndex); // Add letters of old word to test.
        				buildIndex++;
        			}
        			test += letters.charAt(alphaIndex); // Add new letter to test.
        			buildIndex++;
        			while(buildIndex < 5){ // Add rest of letters fr/ old word.
        				test += startWord.charAt(buildIndex);
        				buildIndex++;
        			}
        			if(isInDictionary(test)){
        				// Append number of differences between test and end word as well as
        				// the most recently-changed letter index of test to beginning of test.
        				test = Integer.toString(numDifferentChars(test, endWord))
        						+ Integer.toString(wordIndex) + test;
        				int index = binarySearch(possibles, test);
        				possibles.add(index, test); // Add test to list of potential words for branching.
        			}
        		}
        	}
        }
        Iterator<String> it = possibles.iterator();
        String temp;
        // Run through all valid words and branch for words not appearing in Dictionary.
        while(it.hasNext()){
        	temp = it.next();
        	// Pull index of change from second position of String fr/ possibles.
        	int change = Integer.parseInt(temp.substring(1, 2));
        	// Take String without its appended digits.
        	temp = temp.substring(2);
        	if(!isSolution(temp)){ // If temp doesn't appear in solutions list, try it.
        		solutions.add(temp); // Add to solutions ArrayList.
        		addSolution(temp); // Add to solutions HashMap.
        		// Recursive call with changed word.
            	result = makeLadder(temp, endWord, change, solutions);
            	if(result != null){
            		// Null will cascade down branch.
            		return result;
            	}
        	}

        }
        // If a branch doesn't reach a solution, returns null.
        return null;
    }
    
    
    public void setDictionary(ArrayList<String> words){
    	Iterator<String> i = words.iterator();
    	while(i.hasNext()){
    		String temp = i.next();
    		dictionary.put(temp, temp);
    	}
    }
    
    public void clearSolutions(){
    	solutions.clear();
    }
    
    public void addSolution(String word){
    	solutions.put(word, word);
    }
    
    public void removeSolution(String word){
    	solutions.remove(word);
    }
    
    public boolean isSolution(String word){
    	String temp = solutions.get(word);
    	if(word.equalsIgnoreCase(temp)){
    		return true;
    	} else{
    		return false;
    	}
    }
    
    public boolean isInDictionary(String word){
    	String temp = dictionary.get(word);
    	if(word.equalsIgnoreCase(temp)){
    		return true;
    	} else{
    		return false;
    	}
    }
    
    public void printWordLadder(List<String> ladder){
			Iterator<String> it = ladder.iterator();
			while(it.hasNext()){
				String temp = it.next();
				System.out.println(temp);
			}
    }
    
    public int numDifferentChars(String word, String end_word){
    	int count = 0;
    	for(int i = 0; i < word.length(); i++){
    		if(!(word.substring(i, i+1)).equalsIgnoreCase(end_word.substring(i, i+1))){
    			count++;
    		}
    	}
    	return count;
    }
   
   // Override binarySearch to return the index at which a new item should be inserted to
    // maintain alpha order.
 	public static int binarySearch(List<String> possibles, String word){
		int low = 0;
	    int high = possibles.size() - 1;
 	    while (low <= high) {
 	    	int mid = (low + high) / 2;
 	    	String midWord = possibles.get(mid);
 		    int cmp = midWord.compareTo(word);
 		    if (cmp < 0)
 		        low = mid + 1;
 		    else if (cmp > 0)
 		        high = mid - 1;
 		    else
 		        return mid;
 		}
 		return low;
 	}
}
