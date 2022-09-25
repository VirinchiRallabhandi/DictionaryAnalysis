import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Random;
import java.io.IOException;
/**
 * This project aims to find the minimum number of words one needs to understand
 * so that the rest of the English language can be understood with the aid of
 * a dictionary. I can't think of a rigorous solution, so I'll apply a genetic
 * algorithm to estimate the solution instead.
 * 
 * @author Virinchi Rallabhandi
 *
 */
public class DictionaryAnalyser {

	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		Dictionary dictionary = new Dictionary(
			"/Users/virinchirallabhandi/Documents/Miscellaneous/"
			+ "WordAnalysisProject/Dictionary.txt");
		ArrayList<ArrayList<String>> solutions = new ArrayList<ArrayList<String>>();
		//An array would be better, but arrays can't hold parameterised types,
		//so I had to use an ArrayList.
		int max = 1000; //The number of trial solutions I'll use.
		ArrayList<String> wordList = new ArrayList<String>();
		Iterator<String> it = dictionary.getWordList().iterator();
		while (it.hasNext()) {
			wordList.add(it.next());
		}
		Collections.sort(wordList);
		for (int i = 0; i < max; i = i + 1) {
			solutions.add(new ArrayList<String>());
			for (int j = 0; j < wordList.size(); j = j + 1) {
				solutions.get(i).add(wordList.get(j));
			}
		}
		Random r = new Random();
		int generations = 60; //The number of generations in my genetic algorithm
		int mutationRate = 130; //This parameter is explained when it is used below.
		ArrayList<String> bestSolution = new ArrayList<String>();
		for (int g = 0; g < generations; g = g + 1) {
			System.out.println("Generation "+g+" out of "+generations+":");
			for (int i = 0; i < max; i = i + 1) {
				int tries = 3*solutions.get(i).size()/2;
				//This is the number of times I randomly choose a word in each
				//solution and check if all words in its definition are already in
				//the solution.
				for (int j = 0; j < tries; j = j + 1) {
					int wordIndex = r.nextInt(solutions.get(i).size());
					String word = solutions.get(i).get(wordIndex);
					ArrayList<String> definition = dictionary.getDefinition(word);
					boolean containsAll = true;
					boolean finished = false;
					if (definition.size() > 1) {
						for (int k = 1; k < definition.size(); k = k + 1) {
							String definitionWord = definition.get(k);
							//Binary search for definitionWord in the solution
							int lower = 0;
							int upper = solutions.get(i).size() - 1;
							int middle = (lower + upper)/2;
							while (upper > lower + 1 && !finished) {
								middle = (lower + upper)/2;
								if (solutions.get(i).get(middle).equals(
									definitionWord)) {
									finished = true;
								} else if (solutions.get(i).get(middle).compareTo(
									definitionWord) > 0) {
									upper = middle;
								} else if (solutions.get(i).get(middle).compareTo(
									definitionWord) < 0) {
									lower = middle;
								}
							}
							if (!finished && 
								!solutions.get(i).get(upper).equals(
								definitionWord) && !solutions.get(i).get(
								lower).equals(definitionWord)) {
								containsAll = false;
							}
						}
					} else {
						containsAll = false;
					}
					if (containsAll) {
						solutions.get(i).remove(wordIndex);
					}
				}
			}
			//Now I sort the solutions from best to worst.
			quickSort(solutions, 0, solutions.size() - 1);
			System.out.println("The best solution had "+solutions.get(0).size()+
				" words in it.");
			if (g == 0 || solutions.get(0).size() < bestSolution.size()) {
				bestSolution = new ArrayList<String>();
				for (int i = 0; i < solutions.get(0).size(); i = i + 1) {
					bestSolution.add(solutions.get(0).get(i));
				}
			}
			//Cull the poorest performing half of the solutions and replace them
			//with new solutions that are offspring of randomly chosen solutions
			//from the better performing half.
			for (int i = max/2; i < max; i = i + 1) {
				solutions.remove(max/2);
			}
			for (int i = max/2; i < max; i = i + 1) {
				int parent1 = r.nextInt(max/2);
				int parent2 = parent1;
				while (parent2 == parent1) {
					parent2 = r.nextInt(max/2);
				}
				solutions.add(new ArrayList<String>());
				int j1 = 0;
				int j2 = 0;
				while (j1 < solutions.get(parent1).size() ||
					j2 < solutions.get(parent2).size()) {
					if (j1 >= solutions.get(parent1).size()) {
						solutions.get(i).add(solutions.get(parent2).get(j2));
						j2 = j2 + 1;
					} else if (j2 >= solutions.get(parent2).size()) {
						solutions.get(i).add(solutions.get(parent1).get(j1));
						j1 = j1 + 1;
					} else if (solutions.get(parent1).get(j1).compareTo(
						solutions.get(parent2).get(j2)) < 0) {
						solutions.get(i).add(solutions.get(parent1).get(j1));
						j1 = j1 + 1;
					} else if (solutions.get(parent1).get(j1).compareTo(
						solutions.get(parent2).get(j2)) > 0) {
						solutions.get(i).add(solutions.get(parent2).get(j2));
						j2 = j2 + 1;
					} else {
						solutions.get(i).add(solutions.get(parent1).get(j1));
						j1 = j1 + 1;
						j2 = j2 + 1;
					}
				}
			}
			//I'll add mutations to all solutions. That is, I'll randomly
			//choose some number, mutationRate, of words from the dictionary
			//randomly and add them and the words in their definitions to
			//the solution.
			for (int i = 0; i < max; i = i + 1) {
				for (int m = 0; m < mutationRate; m = m + 1) {
					String newWord = wordList.get(r.nextInt(wordList.size()));
					ArrayList<String> definition = dictionary.getDefinition(newWord);
					for (String w : definition) {
						boolean finished = false;
						int j = 0;
						while (!finished && j < solutions.get(i).size()) {
							if (solutions.get(i).get(j).compareTo(w) < 0) {
								j = j + 1;
							} else if (solutions.get(i).get(j).compareTo(w) == 0) {
								finished = true;
							} else {
								finished = true;
								solutions.get(i).add(j, w);
							}
						}
					}
				}
			}
		}
		writeToFile(
			"/Users/virinchirallabhandi/Documents/Miscellaneous/minimalWords.txt",
			bestSolution);
	}
	
	private static void quickSort(ArrayList<ArrayList<String>> a,
		int lower, int upper) {
		if (lower < upper) {
			//upper is used as the pivot
			int pivotFinal = partition(a, lower, upper);
			quickSort(a, lower, pivotFinal - 1);
			quickSort(a, pivotFinal + 1, upper);
		}
	}
	
	private static int partition(ArrayList<ArrayList<String>> a,
		int lower, int upper) {
		int highestBelowPivot = lower - 1;
		for (int i = lower; i < upper; i = i + 1) {
			if (a.get(i).size() <= a.get(upper).size()) {
				highestBelowPivot = highestBelowPivot + 1;
				swap(a, i, highestBelowPivot);
			}
		}
		highestBelowPivot = highestBelowPivot + 1;
		swap(a, highestBelowPivot, upper);
		return highestBelowPivot;
	}
	
	private static void swap(ArrayList<ArrayList<String>> a, int i, int j) {
		ArrayList<String> rememberI = new ArrayList<String>();
		ArrayList<String> rememberJ = new ArrayList<String>();
		for (int k = 0; k < a.get(i).size(); k = k + 1) {
			rememberI.add(a.get(i).get(k));
		}
		for (int k = 0; k < a.get(j).size(); k = k + 1) {
			rememberJ.add(a.get(j).get(k));
		}
		a.remove(i);
		a.add(i, rememberJ);
		a.remove(j);
		a.add(j, rememberI);
	}
	
	private static void writeToFile(String path, ArrayList<String> bestSolution) {
		StringBuilder text = new StringBuilder("");
		for (int i = 0; i < bestSolution.size(); i = i + 1) {
			text.append(bestSolution.get(i)).append("\n");
		}
		try {
            FileWriter writer = new FileWriter(path, false);
            PrintWriter printer = new PrintWriter(writer);
            printer.printf("%s", text.toString());
            printer.close();
		} catch (IOException e) {
			System.out.println("File writing disaster!");
		}
	}
	
}