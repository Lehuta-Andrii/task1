package org.study.task1;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import org.study.task1.trie.RWayTrie;
import org.study.task1.trie.alphabet.EnglishAlphabet;
import org.study.task1.trie.tuple.Tuple;

/**
 * Class for testing of RWayTrie trie
 * 
 * @author Andrii_Lehuta
 *
 */
public class RWayTrieTest {

	private static final String AUX_STRING = "aaaaaaaaaaaaaaaaaa";
	private RWayTrie trie;

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
		int expectedSize = 7;

		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

		assertEquals(trie.size(), expectedSize);
	}

	/**
	 * Checks add method of RWayTrie with empty word
	 */
	@Test
	public void addMethodEmptyStringAddTest() {

		int oldSize = trie.size();

		trie.add(new Tuple("", 1));

		assertEquals(trie.size(), oldSize);
	}
	
	/**
	 * Checks add method of RWayTrie with same word
	 */
	@Test
	public void addMethodSameStringAddTest() {

		Tuple sameTuple = new Tuple("abc",1);
		
		trie.add(sameTuple);
		
		int oldSize = trie.size();

		trie.add(sameTuple);

		assertEquals(trie.size(), oldSize);
	}

	/**
	 * Checks delete method of RWayTrie by filling it with prepared words and
	 * tries to delete existing and non existing words
	 */
	@Test
	public void deleteMethodTest() {

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

		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

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

		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

		for (String word : trie.words()) {
			assertTrue(testWords.contains(word));
		}
	}

	/**
	 * Checks contains method of RWayTrie by filling it with prepared words and
	 * testing if existing  word exist
	 */
	@Test
	public void containsMethodOnExistingWordsTest() {
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

		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

		for (String word : testWords) {
			assertTrue(trie.contains(word));
		}

	}

	/**
	 * Checks contains method of RWayTrie by filling it with prepared words and
	 * testing if existing words exist
	 */
	@Test
	public void containsMethodOnNonExistingWordsTest() {
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

		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));


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

		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

		for (String word : testWords) {
			for (String prefixWords : trie.wordsWithPrefix(word.substring(0, 2))) {
				assertTrue(testWords.contains(prefixWords));
			}
		}
	}

	/**
	 * Checks if empty RWayTrie iterator throws the exception 
	 */
	@Test(expected = NoSuchElementException.class)
	public void wordsMethodExceptionThrowTest() {

		Iterator<String> iter = trie.words().iterator();
		iter.next();

	}
	
	/**
	 * Checks if RWayTrie iterator throws the exception on successful deletion 
	 */
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorConcurrentModificationOnDeletionTest() {
		
		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

		Iterator<String> iter = trie.words().iterator();
		iter.next();
		assertTrue(trie.delete("drill"));
		iter.next();

	}
	
	
	/**
	 * Checks if RWayTrie iterator throws the exception on unsuccessful deletion 
	 */
	@Test
	public void iteratorConcurrentModificationOnUnsuccessfulDeletionTest() {
		
		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

		Iterator<String> iter = trie.words().iterator();
		iter.next();
		assertFalse(trie.delete(AUX_STRING));
		iter.next();

	}
	
	/**
	 * Checks if RWayTrie iterator throws the exception on add operation
	 */
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorConcurrentModificationOnAddTest() {
		
		trie.add(new Tuple("associated", 1));
		trie.add(new Tuple("drill", 1));
		trie.add(new Tuple("drink", 1));
		trie.add(new Tuple("spelling", 1));
		trie.add(new Tuple("respond", 1));
		trie.add(new Tuple("seriousness", 1));
		trie.add(new Tuple("singers", 1));

		Iterator<String> iter = trie.words().iterator();
		iter.next();
		trie.add(new Tuple("drills",1));
		iter.next();

	}
	
	
	
	
	
}
