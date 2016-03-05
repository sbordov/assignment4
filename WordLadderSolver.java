/*
    ADD YOUR HEADER HERE
 */

package assignment4;

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
    public ArrayList<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException 
    {
        // implement this method
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean validateResult(String startWord, String endWord, ArrayList<String> wordLadder) 
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    // add additional methods here
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
    
    public void printWordLadder(ArrayList<String> ladder){
			Iterator<String> it = ladder.iterator();
			while(it.hasNext()){
				String temp = it.next();
				System.out.println(temp);
			}
    }
}
