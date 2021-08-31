import java.util.Arrays;

/**
 * Class to keep track of all the words involved in the program
 */
public class WordDictionary {
	int size;
	String[] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary
	
	/**
	 * Constructor to initialise the dictionary and populate the dictionary
	 * @param tmp
	 */
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
	}
	
	/**
	 * Default Constructor setting the size of the dictionary
	 */
	WordDictionary() {
		size=theDict.length;	
	}

	/**
	 * Method to get new random word from the dictionary
	 * @return randomWord
	 */
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		// return theDict[wdPos];
		String word = theDict[wdPos];
		System.out.println(Arrays.asList(theDict));
		if (word.equals("")){
			return getNewWord();
		}
		return word;	
	}

	public synchronized String caughtWord(String caught) {
		for (int i = 0; i < theDict.length; i++){
			if (theDict[i].equals(caught)){
				theDict[i] = "";
			}
		}
		return getNewWord();
	}


}
