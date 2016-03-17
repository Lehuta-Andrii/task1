package org.study.task1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The PrefixMatches class provides the function of auto completion for words of
 * English language. It is based on the use of Trie word table and provides add,
 * contains, delete, size methods. It also provides wordWithPrefix methods that
 * find the groups of words with specified prefix. The add, contains, delete,
 * operations take time proportional to the length of the key (in the worst
 * case). Construction takes constant time. The size operation takes constant
 * time.
 * 
 * @author Andrii_Lehuta
 *
 */
public class PrefixMatches {

	private Trie trie;

	/**
	 * Consrtucts table with the help of specified trie
	 * 
	 * @param trie
	 *            - specified trie
	 */
	public PrefixMatches(Trie trie) {
		this.trie = trie;
	}

	/**
	 * Adds the specified array of words to this table
	 * 
	 * @param strings
	 *            - the specified array of words
	 * @return the number of added words
	 */
	public int add(String... strings) {
		int oldSize = trie.size();

		for (String string : strings) {
			String words[] = string.split(" ");

			for (String word : words) {
				if (word.length() >= 2) {
					trie.add(new Tuple(word, word.length()));
				}
			}
		}

		return trie.size() - oldSize;
	}

	/**
	 * Returns true if table contains the specified word.
	 * 
	 * @param word
	 *            - word whose presence in this table to be tested
	 * @return true if this table contains the specified word
	 */
	public boolean contains(String word) {
		return trie.contains(word);
	}

	/**
	 * Removes the specified word in this table.
	 * 
	 * @param word
	 *            - the specified word
	 * @return true if the specified word is deleted
	 */
	public boolean delete(String word) {
		return trie.delete(word);
	}

	/**
	 * Returns the size of table
	 * 
	 * @return the size of table
	 */
	public int size() {
		return trie.size();
	}

	/**
	 * Returns group of words that contains the specified prefix
	 * 
	 * @param pref
	 *            - the specified prefix
	 * @param k
	 *            - number of groups to return
	 * @return iterable object that contains k or less group of words
	 */
	public Iterable<String> wordsWithPrefix(String pref, int k) {

		if (k == 0) {
			return new LinkedList<String>();
		}

		List<String> result = new LinkedList<String>();
		Iterator<String> iterator = trie.wordsWithPrefix(pref).iterator();

		if (iterator.hasNext()) {
			result.add(iterator.next());
		} else {
			return new LinkedList<String>();
		}

		while (iterator.hasNext()) {

			String word = iterator.next();

			if (word.length() > result.get(result.size() - 1).length()) {
				k--;
			}

			if (k == 0) {
				break;
			}

			result.add(word);

		}

		return result;
	}

	/**
	 * Returns group of words that contains the specified prefix
	 * 
	 * @param pref
	 *            - the specified prefix
	 * @param k
	 *            - number of groups to return
	 * @return iterable object that contains k or less group of words
	 */
	public Iterable<String> wordsWithPrefix(String pref) {
		return wordsWithPrefix(pref, 3);
	}

}
