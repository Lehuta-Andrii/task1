package org.study.task1;

/**
 * Immutable tuple class for storage of word and its weight.
 * 
 * @author Andrii_Lehuta
 *
 */
public class Tuple {
	private String word;
	private int weight;

	public Tuple(String word, int weight) {
		this.word = word;
		this.weight = weight;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

}
