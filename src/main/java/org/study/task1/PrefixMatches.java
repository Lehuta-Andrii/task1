package org.study.task1;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
public class PrefixMatches implements Iterable<String>{

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
		
		return new Iterable<String>(){

			public Iterator<String> iterator() {
				return new PrefixMatchesIterator(pref,k);
			}
			
		};
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

	private class PrefixMatchesIterator implements Iterator<String>{

		private Iterator<String> RWayIterator;
		private String currentWord = "";
		private String nextWord = "";
		private int k;
		
		public PrefixMatchesIterator(String pref, int k){
			RWayIterator = trie.wordsWithPrefix(pref).iterator();
			this.k = k;
			
			if(RWayIterator.hasNext()){
				currentWord = RWayIterator.next();
			}else{
				this.k = 0;
			}
		}

		@Override
		public boolean hasNext() {
			return k > 0;
		}

		@Override
		public String next() {
			
			if(k <= 0){
				throw new NoSuchElementException();
			}
			
			if(RWayIterator.hasNext()){
				nextWord = RWayIterator.next();
				
				if(currentWord.length() < nextWord.length()){
					k--;
				}
				
			}else{
				k = 0;
			}
			
			String result = currentWord;
			currentWord = nextWord;

			return result;
		}
		
	}
	
	@Override
	public Iterator<String> iterator() {
		return new PrefixMatchesIterator("", 3);
	}

}
