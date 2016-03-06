package assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Assign4Driver
{
    public static void main(String[] args)
    {
        // Create a word ladder solver object
        Assignment4Interface wordLadderSolver = new WordLadderSolver();
        
		if (args.length != 2) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}

        try 
        {
    		// Populate dictionary. Assumes args[0] is filename of dictionary.dat
    		ArrayList<String> dictionary = processLinesInFile (args[0],
    				"Create Dictionary");
    		wordLadderSolver.setDictionary(dictionary);
    		
    		// Find pairs of start and ending words for word ladder computation.
    		// Will vet each pair to see if the entries are valid dictionary words b4
    		// attempting to compute word ladder.
    		ArrayList<String> pairs = processLinesInFile (args[1], "Find Pairs");
    		if(pairs.size() == 0){
    			System.err.println("No pairs of words were given for making word ladders.");
    		}
    		
    		Iterator<String> i = pairs.iterator(); // Iterate through all pairs.
    		while(i.hasNext()){
    			String temp = i.next();
    			String[] s = temp.split("\\s+"); // Split pairs by multi-space delim.
    			if(s.length == 2){ // If there are two arguments, proceed.
    				if(wordLadderSolver.isInDictionary(s[0]) &&
    						wordLadderSolver.isInDictionary(s[1])){
    					// If both words in a pair appear in the dictionary, compute
    					// word ladder for pair.
    					List<String> result = wordLadderSolver.computeLadder(s[0],
    							s[1]);
    					boolean correct = true;
    					if(result == null){
    						correct = false;
    					}
    					//boolean correct = wordLadderSolver.validateResult(s[0],
    					//		s[1], result);
						System.out.println("**********");
    					if(correct){
    						System.out.println("For the input words " + s[0] + " and "
    								+ s[1] + " the following word ladder was found");
    						wordLadderSolver.printWordLadder(result);
    					} else{
    						System.out.println("There is no word ladder between " + s[0]
    								+ " and " + s[1] + "!");
    					}
						System.out.println("**********");
    				} else{
    					System.err.println("At least one of the words " + s[0]
    							+ " and " + s[1] +" are not legitimate 5-letter"
    									+ " words from the dictionary.");
    				}
    			}
    		}
        } 
        catch (NoSuchLadderException e) 
        {
            e.printStackTrace();
        }
    }
    
	/******************************************************************************
	* Method Name: processLinesInFile                                             *
	* Purpose:  
	*            
	*                                                 
	* Returns: None                                                               *
	******************************************************************************/
	public static ArrayList<String> processLinesInFile (String filename, String op) 
	{ 
		try 
		{
			FileReader freader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(freader);
			ArrayList<String> dictionaryWords = new ArrayList<String>();
			ArrayList<String> pairs = new ArrayList<String>();
			
			// For creating dictionary.
			if(op.equals("Create Dictionary")){
				for (String s = reader.readLine(); s != null; s = reader.readLine()) 
				{
					if(s.length() >= 5){
						String word = s.substring(0, 5);
						if(isValidWord(word)){
							dictionaryWords.add(word);
						}
					}
				}
				return dictionaryWords;
			// For finding word ladder pairs.
			} else{
				for (String s = reader.readLine(); s != null; s = reader.readLine()) 
				{
					pairs.add(s);
				}
				return pairs;
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) 
		{
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}    
    
	/* isValidChar()
	 * Checks that the given word 1) doesn't start with * 2) is 5 letters 
	 * 3) has only alphabetical characters.
	 */
	public static boolean isValidWord(String word){
		if(word.substring(0,1).equals("*")){
			return false;
		} else if(word.length() != 5){
			return false;
		} else{
			int i = 0;
			while(i < 5){
				if(!isValidChar(word.substring(i, i + 1))){
					return false;
				}
				i++;
			}
			return true;
		}
	}
	
	/* isValidChar()
	 * Checks if the given character is valid according to the list indicated in Assignment1.
	 */
	public static boolean isValidChar (String character){
		if("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(character)
				>= 0){
			return true;
		} else {
			return false;
		}
	}
	
}
