package assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Assignment1.Translator;

public class Assign4Driver
{
    public static void main(String[] args)
    {
        // Create a word ladder solver object
        Assignment4Interface wordLadderSolver = new WordLadderSolver();
        
		if (args.length != 1) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		// Populate dictionary. Assumes args[0] is filename of dictionary.dat
		ArrayList<String> dictionary = processLinesInFile (args[0],
				"Create Dictionary");
		wordLadderSolver.setDictionary(dictionary);

        try 
        {
            List<String> result = wordLadderSolver.computeLadder("money", "honey");
            boolean correct = wordLadderSolver.validateResult("money", "honey", result);
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
			
			// For creating dictionary.
			if(op.equals("Create Dictionary")){
				for (String s = reader.readLine(); s != null; s = reader.readLine()) 
				{
					String word = s;
					if(isValidWord(word)){
						dictionaryWords.add(word);
					}
				}
				return dictionaryWords;
			// For creating word ladders.
			} else()
			
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
