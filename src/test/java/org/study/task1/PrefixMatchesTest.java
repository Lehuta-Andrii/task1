package org.study.task1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Class for testing of PrefixMatches table
 * 
 * @author Andrii_Lehuta
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {

	private static final String WORDS_FILE = "src/test/resources/wiktionary.txt";
	private static final String CHECKED_WORDS_FILE = "src/test/resources/word2.txt";
	private static final String AUX_STRING = "aaaaaaaaaaaaaaaaaa";
	private static final String WORD_PATTERN = "[a-z]+";
	private static final String SPACES_PATTERN = "[ \t]";
	private static Set<String> testWords;
	private static Set<String> wordsWithKnownGroups;

	private PrefixMatches dictionary;

	@Mock
	private Trie mockTrie;
	
	@InjectMocks
	private PrefixMatches mockPrefixMatches;
	
	/**
	 * Initializes list of prepared words for testing
	 */
	@BeforeClass
	public static void startUp() {

		testWords = new TreeSet<String>();

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

		wordsWithKnownGroups = new TreeSet<String>();

		try (BufferedReader fin = new BufferedReader(new FileReader(CHECKED_WORDS_FILE));) {

			String line;

			while ((line = fin.readLine()) != null) {
				String[] words = line.trim().split(SPACES_PATTERN);
				for (String word : words) {
					if (word.length() >= 2 && word.matches(WORD_PATTERN)) {
						wordsWithKnownGroups.add(word);
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
	 * Fill table for every test
	 */
	@Before
	public void trieInit() {
		dictionary = new PrefixMatches(new RWayTrie(new EnglishAlphabet()));
	}

	/**
	 * Test if PrefixMatches calls trie add method fixed number of times
	 */
	@Test
	public void prefixMatchesAddMehtodCallsTrieAddThreeTimes(){
		mockPrefixMatches.add("one", "two", "three");
		verify(mockTrie, times(3)).add((Tuple)argThat(new ArgumentMatcher<Tuple>(){
		      public boolean matches(Object list) {
		          return list.getClass().equals(Tuple.class);
		      }
		}));
	}
	
	/**
	 * Test if PrefixMatches wordsWithPrefix calls trie wordsWithPrefix
	 */
	@Test
	public void wordsWithPrefixMethodCallsTrieWordWithPrefix(){
		String testString = "any";
		Trie spy = spy(new RWayTrie(new EnglishAlphabet()));
		PrefixMatches localMockPrefixMatches = new PrefixMatches(spy);
		
		localMockPrefixMatches.wordsWithPrefix(testString);
		
		verify(spy).wordsWithPrefix(testString);
	}
	
	/**
	 * Checks add method of PrefixMatches by filling it with prepared words
	 */
	@Test
	public void addMethodTest() {
		fillPrefixMatches();
		assertEquals(dictionary.size(), testWords.size());
	}

	/**
	 * Checks delete method of PrefixMatches by filling it with prepared words
	 * and tries to delete existing and non existing words
	 */
	@Test
	public void deleteMethodTest() {
		fillPrefixMatches();

		for (String word : testWords) {
			assertFalse(dictionary.delete(word + AUX_STRING));
		}

		for (String word : testWords) {
			assertTrue(dictionary.delete(word));
		}

		assertEquals(dictionary.size(), 0);

	}

	/**
	 * Checks contains method of PrefixMatches by filling it with prepared words
	 * and testing if existing and non existing word exist
	 */
	@Test
	public void containsMethodTest() {
		fillPrefixMatches();
		for (String word : testWords) {
			assertTrue(dictionary.contains(word));
		}

		for (String word : testWords) {
			assertFalse(dictionary.contains(word + AUX_STRING));
		}
	}

	/**
	 * Checks wordWithPrefix method of PrefixMatches by filling it with prepared
	 * words and testing if the size of batch of words with specified prefix is
	 * correct
	 */
	@Test
	public void wordWithPrefixMethodTest() {
		int counter = 0;

		dictionary.add(wordsWithKnownGroups.toArray(new String[0]));

		for (String word : dictionary.wordsWithPrefix("en", 2)) {
			counter++;
		}
		assertTrue(counter == 2);

		counter = 0;
		for (String word : dictionary.wordsWithPrefix("ca", 3)) {
			counter++;
		}
		assertTrue(counter == 10);

		counter = 0;
		for (String word : dictionary.wordsWithPrefix("de", 4)) {
			counter++;
		}
		assertTrue(counter == 22);

	}

	private void fillPrefixMatches() {
		dictionary.add(testWords.toArray(new String[0]));

	}

}
