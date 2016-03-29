package org.study.task1.trie.alphabet;

import org.study.task1.trie.RWayTrie;

/**
 * EngrlishAlphabet based on RWayTrie.Alphabet public interface and used as the
 * helper class to represent alphabet of English language
 * 
 * @author Andrii_Lehuta
 *
 */
public class EnglishAlphabet implements RWayTrie.Alphabet {
    private static final int SIZE = 26;
    private static final int SHIFT = 0x61;

    /**
     * Returns the size of alphabet
     * 
     * @return the size of alphabet
     */
    @Override
    public int size() {
	return SIZE;
    }

    /**
     * Return the position of specified symbol in alphabet
     * 
     * @param character
     *            - specified symbol
     * @return position in alphabet
     */
    @Override
    public int position(char character) {
	int pos = character - SHIFT;

	if (pos < 0 || pos >= SIZE) {
	    throw new IndexOutOfBoundsException();
	}

	return pos;
    }

    /**
     * Return the character from specified position in alphabet
     * 
     * @param character
     *            - specified symbol
     * @return position in alphabet
     */
    @Override
    public char character(int position) {
	if (position < 0 || position >= SIZE) {
	    throw new IndexOutOfBoundsException();
	}
	return (char) (position + SHIFT);
    }

}
