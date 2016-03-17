package org.study.task1;

/**
 * EngrlishAlphabet based on RWayTrie.Alphabet public interface and used as the
 * helper class to represent alphabet of English language
 * 
 * @author Andrii_Lehuta
 *
 */
public class EnglishAlphabet implements RWayTrie.Alphabet {

	private final int SIZE = 26;
	
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
		return character - 0x61;
	}

	/**
	 * Return the character from specified position in alphabet
	 * 
	 * @param character
	 *            - specified symbol
	 * @return position in alphabet
	 */
	@Override
	public char charcter(int position) {
		return (char) (position + 0x61);
	}

}
