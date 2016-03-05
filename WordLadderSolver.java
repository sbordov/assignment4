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
    	List <String> result = makeLadder(startWord, endWord, 0);
    	clearSolutions();
    	return result;
        //throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) 
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    // add additional methods here
    public List<String> makeLadder(String startWord, String endWord, int changeIndex){
    	List<String> result = new ArrayList<String>();
    	if(startWord.equals(endWord)){		// start word = end word
    		result.add(startWord);
    		result.add(endWord);
    		return result;
    	}
        if(numDifferentChars(startWord, endWord) == 1){
        	result.add(startWord);
        	result.add(endWord);
        	return result;
        }
        
        String letters = "abcdefghijklmnopqrstuvwxyz";
        List<String> possibles = new ArrayList<String>();
        List<String> solutions = new ArrayList<String>();
        for(int x = 0; x < 5; x++){
        	if(x == changeIndex){
        		x++;
        	}
        	char check = startWord.charAt(x);
        	for(int y = 0; y < letters.length(); y++){
        		if(check != letters.charAt(y)){
        			int loop = 0;
        			String test = "";
        			while(loop != x){
        				test += startWord.charAt(loop);
        				loop++;
        			}
        			test += letters.charAt(y);
        			loop++;
        			while(loop < 5){
        				test += startWord.charAt(loop);
        				loop++;
        			}
        			if(isInDictionary(test)){
        				test = Integer.toString(numDifferentChars(test, endWord)) + Integer.toString(x) + test;
        				int index = binarySearch(possibles, test);
        				possibles.add(index, test);
        			}
        		}
        	}
        }
        Iterator<String> it = possibles.iterator();
        boolean route = false;
        while(it.hasNext()){
        	String temp = it.next();
        	int change = Integer.parseInt(temp.substring(1, 2));
        	temp = temp.substring(2);
        	solutions.add(temp);
        	addSolution(temp);
        	result = makeLadder(temp, endWord, change, solutions);
        	if(result != null){
        		route = true;
        		result.add(0, startWord);
        		return result;
        	}
        }
        return null;
    }
    
    public List<String> makeLadder(String startWord, String endWord, int changeIndex, List<String> solutions){
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
        for(int x = 0; x < 5; x++){
        	if(x == changeIndex){
        		x++;
        		if(x == 5){
        			break;
        		}
        	}
        	char check = startWord.charAt(x);
        	for(int y = 0; y < letters.length(); y++){
        		if(check != letters.charAt(y)){
        			int loop = 0;
        			String test = "";
        			while(loop != x){
        				test += startWord.charAt(loop);
        				loop++;
        			}
        			test += letters.charAt(y);
        			loop++;
        			while(loop < 5){
        				test += startWord.charAt(loop);
        				loop++;
        			}
        			if(isInDictionary(test)){
        				test = Integer.toString(numDifferentChars(test, endWord)) + Integer.toString(x) + test;
        				int index = binarySearch(possibles, test);
        				possibles.add(index, test);
        			}
        		}
        	}
        }
        Iterator<String> it = possibles.iterator();
        boolean route = false;
        String temp;
        while(it.hasNext()){
        	temp = it.next();
        	int change = Integer.parseInt(temp.substring(1, 2));
        	temp = temp.substring(2);
        	if(!isSolution(temp)){
        		solutions.add(temp);
        		addSolution(temp);
            	result = makeLadder(temp, endWord, change, solutions);
            	if(result != null){
            		route = true;
            		return result;
            	}
        	}

        }
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
