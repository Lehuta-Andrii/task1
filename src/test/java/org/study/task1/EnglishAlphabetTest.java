package org.study.task1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.study.task1.trie.RWayTrie;
import org.study.task1.trie.alphabet.EnglishAlphabet;

public class EnglishAlphabetTest {

    private EnglishAlphabet alphabet;

    @Before
    public void trieInit() {
	alphabet = new EnglishAlphabet();
    }

    @Test
    public void positionWithSymbolInAlphabetTest() {
	char inAlphabetChar = 'a';
	assertTrue(alphabet.position(inAlphabetChar) < 26 && alphabet.position(inAlphabetChar) >= 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void positionWithSymbolNotInAlphabetTest() {
	char inAlphabetChar = 'A';
	alphabet.position(inAlphabetChar);
    }

    
    @Test
    public void characterWithPositionInAlphabetTest() {
	int inAlphabetPosition = 0;
	assertTrue(alphabet.character(inAlphabetPosition) == 'a');
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void characterWithPositionNotInAlphabetTest() {
	int inAlphabetPosition = 190;
	alphabet.character(inAlphabetPosition);
    }
}
