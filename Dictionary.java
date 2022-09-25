import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
/**
 * @author Virinchi Rallabhandi
 *
 */
public class Dictionary {
	
	/**
	 * The first entry in each ArrayList<String> is a word in the 
	 * dictionary and all subsequent words are words that appear in the definition
	 * of that word. The first words are arranged alphabetically to comprise the
	 * full dictionary
	 */
	private ArrayList<ArrayList<String>> definitions;
	private HashSet<String> words;
	
	/**
	 * Initialises the definitions object using the provided file
	 * @param filename The path to the file where I saved the dictionary I
	 * downloaded from online
	 */
	public Dictionary(String filename) {
		definitions = new ArrayList<ArrayList<String>>();
		words = new HashSet<String>();
		//The dictionary file will already have the words in alphabetical order,
		//so no sorting should be necessary.
		try {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line  = reader.readLine();
		//The first word in the line should be the entry in the dictionary.
		//However, I need to remove spurious words. Verb conjugations are also
		//an issue. To resolve this issue, I'll use the following method. If a 
		//word in a definition does not appear as a word listed in the dictionary,
		//then I ignore it. This should cover conjugations, explanations such as n.
		//or adj. for nouns or adjectives respectively and other such "words" in
		//a definition. It may be too aggressive though and requires the dictionary
		//to be scanned twice.
		while (line != null) {
			String[] lineWords = line.split(" ");
			if (!line.equals("") && lineWords.length > 0) {
				StringBuilder word = new StringBuilder(
					lineWords[0].toLowerCase());
				for (int i = 0; i < word.length(); i = i + 1) {
					//Removing content in brackets, punctuation and other
					//distractions
					if (word.charAt(i) == '(') {
						while (word.charAt(i) != ')') {
							word.deleteCharAt(i);
						}
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '[') {
						while (word.charAt(i) != ']') {
							word.deleteCharAt(i);
						}
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == ',') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '.') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '-') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '\'') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '0') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '1') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '2') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '3') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '4') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '5') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '6') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '7') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '8') {
						word.deleteCharAt(i);
						i = i - 1;
					} else if (word.charAt(i) == '9') {
						word.deleteCharAt(i);
						i = i - 1;
					}
				}
				if (!words.contains(word.substring(0)) && 
					!word.substring(0).equals("usage") &&
					!word.substring(0).equals("c")) {
					//Unfortunately usage appears on a different line in the 
					//dictionary document I have, messing up the one definition
					//per line assumption.
					words.add(word.substring(0));
				}
			}
			line = reader.readLine();
		}
		reader.close();
		HashSet<String> definitionWords = new HashSet<String>();
		//This hashset is needed to keep track of the words added to the definitions
		//because sometimes two word phrases (e.g. virtual reality) are listed in
		//the dictionary even though they should not.
		reader = new BufferedReader(new FileReader(filename));
		line  = reader.readLine();
		int currentWordIndex = -1;
		while (line != null) {
			if (!line.equals("")) {
				StringBuilder lineEdit = new StringBuilder(line);
				for (int i = 0; i < lineEdit.length(); i = i + 1) {
					//Removing content in brackets, punctuation and other
					//distractions
					if (lineEdit.charAt(i) == '(') {
						while (i < lineEdit.length() && lineEdit.charAt(i) != ')') {
							lineEdit.deleteCharAt(i);
						}
						if (i < lineEdit.length() && lineEdit.charAt(i) == ')') {
							lineEdit.deleteCharAt(i);
						}
						i = i - 1;
					} else if (lineEdit.charAt(i) == '[') {
						while (i < lineEdit.length() && lineEdit.charAt(i) != ']') {
							lineEdit.deleteCharAt(i);
						}
						if (i < lineEdit.length() && lineEdit.charAt(i) == ']') {
							lineEdit.deleteCharAt(i);
						}
						i = i - 1;
					} else if (lineEdit.charAt(i) == ',') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '.') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '-') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '\'') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '0') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '1') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '2') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '3') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '4') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '5') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '6') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '7') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '8') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					} else if (lineEdit.charAt(i) == '9') {
						lineEdit.deleteCharAt(i);
						i = i - 1;
					}
				}
				String[] lineWords = lineEdit.substring(0).split(" ");
				if (lineWords.length > 0 && (!definitionWords.contains(
						lineWords[0].toLowerCase()) ||
						lineWords[0].toLowerCase().equals(definitions.get(
						currentWordIndex).get(0)))) {
					if (((currentWordIndex == -1) ||
						(!lineWords[0].toLowerCase().equals(definitions.get(
						currentWordIndex).get(0)))) 
						&& !lineWords[0].toLowerCase().equals("usage")
						&& !lineWords[0].toLowerCase().equals("c")) {
						//This relies on the first word in the dictionary not
						//having multiple meanings.
						definitions.add(new ArrayList<String>());
						currentWordIndex = currentWordIndex + 1;
						for (int i = 0; i < lineWords.length; i = i + 1) {
							if (i == 0 || (contains(lineWords[i].toLowerCase())
								&& !lineWords[i].toLowerCase().equals(
								lineWords[0].toLowerCase()))) {
								//This unwieldy clause ensures that a word cannot
								//be included in its own definition.
								definitions.get(currentWordIndex).add(
									lineWords[i].toLowerCase());
								if (i == 0) {
									definitionWords.add(
										lineWords[i].toLowerCase());
								}
							}
						}
					} else if (!lineWords[0].toLowerCase().equals("usage") &&
						!lineWords[0].toLowerCase().equals("c")) {
						//The first word in the lineWords list will be the entry
						//in the dictionary this time.
						for (int i = 1; i < lineWords.length; i = i + 1) {
							if (contains(lineWords[i].toLowerCase()) &&
								!lineWords[i].toLowerCase().equals(
								lineWords[0].toLowerCase())) {
								definitions.get(currentWordIndex).add(
									lineWords[i].toLowerCase());
							}
						}
					}
				}
			}
			line = reader.readLine();
		}
		reader.close();
		for (int i = 0; i < definitions.size(); i = i + 1) {
			if (definitions.get(i).size() > 1) {
				Collections.sort(definitions.get(i).subList(
					1, definitions.get(i).size()));
			}
		}
		//Make sure words appearing multiple times in a definition appear only once
		//in the list.
		for (int i = 0; i < definitions.size(); i = i + 1) {
			if (definitions.get(i).size() > 1) {
				for (int j = 1; j < definitions.get(i).size(); j = j + 1) {
					if (definitions.get(i).get(j).equals(
						definitions.get(i).get(j - 1))) {
						definitions.get(i).remove(j);
						j = j - 1;
						//Not hitting the -1st index relies on a word being defined
						//not appearing in its definition (as should be the case in
						//any sensible dictionary).
					}
				}
			}
		}
		//It turns out my dictionary document is not properly sorted, so I need
		//to sort the definitions field myself. However, my dictionary is almost
		//sorted, so insertion sort is probably the best approach
		insertionSort(definitions);
		} catch (Exception e) {System.out.println(e);}
	}
	
	/**
	 * @param word
	 * @return Whether or not the dictionary contains word
	 */
	public boolean contains(String word) {
		if (words.contains(word)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param word
	 * @return The list of the words in the definition of the parameter, word. The
	 * first item in the list is word itself. If word isn't in the dictionary,
	 * then the method returns null.
	 */
	public ArrayList<String> getDefinition (String word) {
		if (!contains(word)) {
			return null;
		}
		int lower = 0;
		int upper = definitions.size() - 1;
		//Binary search for word
		int i = (lower + upper)/2;
		while (upper > lower + 1) {
			i = (lower + upper)/2;
			String leadWord = definitions.get(i).get(0);
			if (leadWord.equals(word)) {
				return definitions.get(i);
			}
			if (leadWord.compareTo(word) > 0) {
				upper = i;
			}
			if (leadWord.compareTo(word) < 0) {
				lower = i;
			}
		}
		if (definitions.get(lower).get(0).equals(word)) {
			return definitions.get(lower);
		}
		if (definitions.get(upper).get(0).equals(word)) {
			return definitions.get(upper);
		}
		return null;
	}
	
	/**
	 * 
	 * @return The field, words
	 */
	public HashSet<String> getWordList() {
		return words;
	}
	
	private void insertionSort(ArrayList<ArrayList<String>> a) {
		for (int j = 1; j < a.size(); j = j + 1) {
			ArrayList<String> key = new ArrayList<String>();
			for (int k = 0; k < a.get(j).size(); k = k + 1) {
				key.add(a.get(j).get(k));
			}
			int i = j - 1;
			boolean foundPosition = false;
			while (i >=0 && !foundPosition) {
				if (a.get(i).get(0).compareTo(key.get(0)) > 0) {
					a.remove(i + 1);
					a.add(i + 1, new ArrayList<String>());
					for (int k = 0; k < a.get(i).size(); k = k + 1) {
						a.get(i + 1).add(a.get(i).get(k));
					}
					i = i - 1;
				} else {
					foundPosition = true;
				}
			}
			if (i != j - 1) {
				a.remove(i + 1);
				a.add(i + 1, new ArrayList<String>());
				for (int k = 0; k < key.size(); k = k + 1) {
					a.get(i + 1).add(key.get(k));
				}
			}
		}
	}
	
}