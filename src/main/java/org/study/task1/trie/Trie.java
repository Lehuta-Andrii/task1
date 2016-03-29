package org.study.task1.trie;

import org.study.task1.trie.tuple.Tuple;

/**
 * Interface of trie symbol table
 * 
 * @author Andrii_Lehuta
 *
 */
public interface Trie {
    /**
     * Adds specified tuple to trie
     * 
     * @param tuple
     *            - specified tuple to add to trie
     */
    void add(Tuple tuple);

    /**
     * Checks if specified word is in trie
     * 
     * @param word
     *            - specified word
     * @return true if specified word is in trie
     */
    boolean contains(String word);

    /**
     * Removes specified word from trie if present
     * 
     * @param word
     *            - specified word
     * @return true if word is found and removed
     */
    boolean delete(String word);

    /**
     * Returns all words of trie
     * 
     * @return all words of trie
     */
    Iterable<String> words();

    Iterable<String> wordsWithPrefix(String pref);

    /**
     * Returns the size of trie
     * 
     * @return integer value that represents the size of trie
     */
    int size();
}
