package org.study.task1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
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

	private static final String AUX_STRING = "aaaaaaaaaaaaaaaaaa";
	private PrefixMatches dictionary;

	@Mock
	private Trie mockTrie;

	@InjectMocks
	private PrefixMatches mockPrefixMatches;

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
	public void prefixMatchesAddMehtodCallsTrieAddThreeTimes() {
		mockPrefixMatches.add("one", "two", "three");
		verify(mockTrie, times(3)).add((Tuple) argThat(new ArgumentMatcher<Tuple>() {
			public boolean matches(Object list) {
				return list.getClass().equals(Tuple.class);
			}
		}));
	}

	/**
	 * Checks add method of PrefixMatches by filling it with prepared words
	 */
	@Test
	public void addMethodTest() {

		int expectedSize = 7;
		dictionary.add("associated", "drill", "drink", "spelling", "respond", "seriousness", "singers");

		assertEquals(dictionary.size(), expectedSize);
	}

	/**
	 * Checks delete method of PrefixMatches by filling it with prepared words
	 * and tries to delete existing words
	 */
	@Test
	public void deleteMethodOnExistingWordsTest() {

		dictionary.add("associated", "drill", "drink", "spelling", "respond", "seriousness", "singers");

		Set<String> testWords = new TreeSet<String>() {
			{
				add("associated");
				add("drill");
				add("drink");
				add("spelling");
				add("respond");
				add("seriousness");
				add("singers");
			}
		};

		for (String word : testWords) {
			assertTrue(dictionary.delete(word));
		}

		assertEquals(dictionary.size(), 0);
		
	}
	
	/**
	 * Checks delete method of PrefixMatches by filling it with prepared words
	 * and tries to delete non existing words
	 */
	@Test
	public void deleteMethodOnNonExistingWordsTest() {

		int expectedSize = 7;
		dictionary.add("associated", "drill", "drink", "spelling", "respond", "seriousness", "singers");
		
		
		Set<String> testWords = new TreeSet<String>() {
			{
				add("associated");
				add("drill");
				add("drink");
				add("spelling");
				add("respond");
				add("seriousness");
				add("singers");
			}
		};

		for (String word : testWords) {
			assertFalse(dictionary.delete(word + AUX_STRING));
		}
		
		assertEquals(dictionary.size(), expectedSize);
		
	}

	/**
	 * Checks contains method of PrefixMatches by filling it with prepared words
	 * and testing if word exist in table
	 */
	@Test
	public void containsMethodOnExistingWordsTest() {

		dictionary.add("associated", "drill", "drink", "spelling", "respond", "seriousness", "singers");

		Set<String> testWords = new TreeSet<String>() {
			{
				add("associated");
				add("drill");
				add("drink");
				add("spelling");
				add("respond");
				add("seriousness");
				add("singers");
			}
		};

		for (String word : testWords) {
			assertTrue(dictionary.contains(word));
		}

	}

	/**
	 * Checks contains method of PrefixMatches by filling it with prepared words
	 * and testing if existing and non existing word exist
	 */
	@Test
	public void containsMethodOnNonExistingWordsTest() {

		dictionary.add("associated", "drill", "drink", "spelling", "respond", "seriousness", "singers");

		Set<String> testWords = new TreeSet<String>() {
			{
				add("associated");
				add("drill");
				add("drink");
				add("spelling");
				add("respond");
				add("seriousness");
				add("singers");
			}
		};

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
	public void wordWithPrefixMethodGroupSizeTest() {

		int counter = 0;
		int expectedNumberOfWords = 7;

		String prefix = "en";
		dictionary.add("en", "end", "ends", "envy", "ended", "entry", "envoy", "enable", "ending", "enabled", "endless",
				"endowed", "encamped", "enclosed", "encoding");

		for (String word : dictionary.wordsWithPrefix(prefix, 4)) {
			counter++;
		}

		assertTrue(counter == expectedNumberOfWords);
	}

	/**
	 * Checks wordWithPrefix method of PrefixMatches by filling it with prepared
	 * words and testing if the returned words are correct
	 */
	@Test
	public void wordWithPrefixMethodWordsCheckTest() {

		String prefix = "en";
		String[] expectedWords = { "en", "end", "ends", "envy", "ended", "entry", "envoy" };

		dictionary.add("en", "end", "ends", "envy", "ended", "entry", "envoy", "enable", "ending", "enabled", "endless",
				"endowed", "encamped", "enclosed", "encoding");

		Iterator<String> iter = dictionary.wordsWithPrefix(prefix, 4).iterator();
		assertEquals(iter.next(), expectedWords[0]);
		assertEquals(iter.next(), expectedWords[1]);
		assertEquals(iter.next(), expectedWords[2]);
		assertEquals(iter.next(), expectedWords[3]);
		assertEquals(iter.next(), expectedWords[4]);
		assertEquals(iter.next(), expectedWords[5]);
		assertEquals(iter.next(), expectedWords[6]);

	}

	/**
	 * Check if dictionary throws exception if it is empty
	 */
	@Test(expected = NoSuchElementException.class)
	public void wordWithPrefixMethodExceptionTest() {

		Iterator<String> iter = dictionary.wordsWithPrefix("").iterator();

		iter.next();

	}

}
