package org.study.task1;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class for testing of RWayTrie trie
 * 
 * @author Andrii_Lehuta
 *
 */
public class RWayTrieTest {

	private static final String WORDS_FILE = "src/test/resources/wiktionary.txt";
	private static final String AUX_STRING = "aaaaaaaaaaaaaaaaaa";
	private static final String WORD_PATTERN = "[a-z]+";
	private static final String SPACES_PATTERN = "[ \t]";
	private static List<String> testWords;
	private RWayTrie trie;

	/**
	 * Initializes list of prepared words for testing
	 */
	@BeforeClass
	public static void startUp() {

		testWords = new ArrayList<String>();

		try (BufferedReader fin = new BufferedReader(new FileReader(WORDS_FILE));) {

			String line;

			while ((line = fin.readLine()) != null) {
				String[] words = line.trim().split(SPACES_PATTERN);
				for (String word : words) {
					if (word.length() >= 2 && word.matches(WORD_PATTERN)) {
						testWords.add(word);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fill trie for every test
	 */
	@Before
	public void trieInit() {
		trie = new RWayTrie(new EnglishAlphabet());
	}

	/**
	 * Checks add method of RWayTrie by filling it with prepared words
	 */
	@Test
	public void addMethodTest() {
		fillTree();
		assertEquals(trie.size(), testWords.size());
	}

	/**
	 * Checks delete method of RWayTrie by filling it with prepared words and
	 * tries to delete existing and non existing words
	 */
	@Test
	public void deleteMethodTest() {
		fillTree();

		for (String word : testWords) {
			assertFalse(trie.delete(word + AUX_STRING));
		}

		for (String word : testWords) {
			assertTrue(trie.delete(word));
		}

		assertEquals(trie.size(), 0);

	}

	/**
	 * Checks words method of RWayTrie by filling it with prepared words and
	 * checking if all words are saved correctly
	 */
	@Test
	public void wordsMethodTest() {
		fillTree();
		for (String word : trie.words()) {
			assertTrue(testWords.contains(word));
		}
	}

	/**
	 * Checks contains method of RWayTrie by filling it with prepared words and
	 * testing if existing and non existing word exist
	 */
	@Test
	public void containsMethodTest() {
		fillTree();
		for (String word : testWords) {
			assertTrue(trie.contains(word));
		}

		for (String word : testWords) {
			assertFalse(trie.contains(word + AUX_STRING));
		}
	}

	/**
	 * Checks wordWithPrefix method of RWayTrie by filling it with prepared
	 * words and testing if all words with specified prefix are discovered
	 */
	@Test
	public void wordWithPrefixMethodTest() {
		fillTree();

		String[] wordsArray = testWords.toArray(new String[0]);
		Arrays.sort(wordsArray);

		for (int i = 0; i < testWords.size(); i++) {
			for (String word : trie.wordsWithPrefix(testWords.get(i).substring(0, 2))) {
				assertTrue(Arrays.binarySearch(wordsArray, word) >= 0);
			}
		}
	}

	private void fillTree() {
		for (String word : testWords) {
			trie.add(new Tuple(word.toLowerCase(), word.length()));
		}
	}
}
