/*
    ADD YOUR HEADER HERE
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
	public HashMap<String, String> dictionary;

    // add a constructor for this object. HINT: it would be a good idea to set up the dictionary there
	public WordLadderSolver(){
		dictionary = new HashMap<String, String>();
	}

    // do not change signature of the method implemented from the interface
    @Override
    public List<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException 
    {
    	List <String> result = makeLadder(startWord, endWord, 0);
    	return result;
        //throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) 
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    // add additional methods here
    public List<String> makeLadder(String startWord, String endWord,
    		int changeIndex){
    	List<String> result = new ArrayList<String>();
    	if(startWord.equals(endWord)){
    		result.add(startWord);
    		result.add(endWord);
    		return result;
    	}
        if(numDifferentChars(startWord, endWord) == 1){
        	result.add(startWord);
        	result.add(endWord);
        	return result;
        }
    }
    
    public void setDictionary(ArrayList<String> words){
    	Iterator<String> i = words.iterator();
    	while(i.hasNext()){
    		String temp = i.next();
    		dictionary.put(temp, temp);
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
}
