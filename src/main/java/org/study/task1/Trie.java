package org.study.task1;

/**
 * Interface of trie symbol table
 * 
 * @author Andrii_Lehuta
 *
 */
public interface Trie {

	/**
	 * Adds specified tuple to trie;
	 * 
	 * @param tuple
	 *            - specified tuple to add to trie
	 */
	public void add(Tuple tuple);

	/**
	 * Checks if specified word is in trie
	 * 
	 * @param word
	 *            - specified word
	 * @return true if specified word is in trie
	 */
	public boolean contains(String word);

	/**
	 * Removes specified word from trie if present
	 * 
	 * @param word
	 *            - specified word
	 * @return true if word is found and removed
	 */
	public boolean delete(String word);

	/**
	 * Returns all words of trie
	 * 
	 * @return all words of trie
	 */
	public Iterable<String> words();

	public Iterable<String> wordsWithPrefix(String pref);

	/**
	 * Returns the size of trie
	 * 
	 * @return integer value that represents the size of trie
	 */
	public int size();
}
