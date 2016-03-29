package org.study.task1.trie;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.study.task1.trie.tuple.Tuple;

/**
 * The RWayTrie class represents a table of words based on a symbol table of
 * key-value pairs with string keys and integer values. It supports add,
 * contains, delete and size methods. It also provides character-based method
 * for finding all strings in the table that start with a given prefix, and
 * method that return all words of table. The add, contains, delete, operations
 * take time proportional to the length of the key (in the worst case).
 * Construction takes constant time. The size operation takes constant time.
 * 
 * @author Andrii_Lehuta
 */
public class RWayTrie implements Trie, Iterable<String> {
    /**
     * Private class that is used for representation of node trie structure.
     * Contains integer value as key of the nod and array of children nodes
     * 
     * @author Andrii_Lehuta
     *
     */
    private static class Node {
	private int value;
	private Node[] next;

	public Node(int alphabetSize) {
	    next = new Node[alphabetSize];
	}

    }

    private final int alphabetSize; // Alphabet size
    private int trieSize = 0; // Size of the trie
    private Node root;
    private Alphabet alphabet;
    private long version;

    /**
     * Public interface uset for representation of alphabet used in trie
     * structure. Consists of size, position, character methods.
     * 
     * @author Andrii_Lehuta
     *
     */
    public interface Alphabet {

	/**
	 * Returns the size of alphabet
	 * 
	 * @return the size of alphabet
	 */
	int size();

	/**
	 * Return the position of specified symbol in alphabet
	 * 
	 * @param character
	 *            - specified symbol
	 * @return position in alphabet
	 */
	int position(char character);

	/**
	 * Return the character from specified position in alphabet
	 * 
	 * @param character
	 *            - specified symbol
	 * @return position in alphabet
	 */
	char character(int position);

    }

    /**
     * Constructs trie with specified alphabet
     * 
     * @param alphabet
     *            - specified alphabet
     */
    public RWayTrie(Alphabet alphabet) {
	this.alphabet = alphabet;
	alphabetSize = alphabet.size();
	root = new Node(alphabetSize);
    }

    /**
     * Adds a specified word to this trie
     * 
     * @param word
     *            - word to be added to trie
     */
    @Override
    public void add(Tuple tuple) {
	Node tmp = root;
	String word = tuple.getWord();

	if (word.equals("")) {
	    return;
	}

	for (int i = 0; i < word.length(); i++) {
	    if (tmp.next[alphabet.position(word.charAt(i))] != null) {
		tmp = tmp.next[alphabet.position(word.charAt(i))];
	    } else {
		tmp.next[alphabet.position(word.charAt(i))] = new Node(alphabetSize);
		tmp = tmp.next[alphabet.position(word.charAt(i))];
	    }
	}

	if (tmp.value == 0) {
	    trieSize++;
	}

	tmp.value = tuple.getWeight();
	version++;
    }

    /**
     * Returns true if trie contains the specified word.
     * 
     * @param word
     *            - word whose presence in this trie to be tested
     * @return true if this trie contains the specified word
     */
    @Override
    public boolean contains(String word) {
	Node result = get(root, word);
	return result != null && result != root;
    }

    /**
     * Private helper method that returns the last node that points to last
     * character of specified word or null if word is not present
     * 
     * @param x
     *            - specified Node to start search
     * @param word
     *            - specified word
     * @return null or node that points to last character of specified word
     */
    private Node get(Node x, String word) {
	if (word.length() == 0) {
	    return x;
	}

	Node tmp = x;

	for (int i = 0; i < word.length() - 1; i++) {
	    if (tmp.next[alphabet.position(word.charAt(i))] == null) {
		return null;
	    } else {
		tmp = tmp.next[alphabet.position(word.charAt(i))];
	    }
	}

	return tmp.next[alphabet.position(word.charAt(word.length() - 1))];

    }

    /**
     * Removes the specified word in this trie.
     * 
     * @param word
     *            - the specified word
     * @return true if the specified word is deleted
     */
    @Override
    public boolean delete(String word) {
	int oldSize = trieSize;

	root = delete(root, word, 0);
	if (root == null) {
	    root = new Node(alphabetSize);
	}

	if (trieSize - oldSize != 0) {
	    version++;
	    return true;
	}

	return false;
    }

    /**
     * Helper function that removes specified word, if discovered, from trie
     * recursively. Through recursion it reaches the last node of the word and
     * sets it to nil if branch of the word contains more words otherwise
     * removes nodes
     * 
     * @param node
     *            - initial node for search of specified word
     * @param word
     *            - specified word
     * @param d
     *            - next character of word
     * @return returns root of trie or null if last word was removed
     */
    private Node delete(Node node, String word, int d) {

	if (node == null) {
	    return null;
	}

	if (word.length() == d) {
	    if (node.value != 0) {
		trieSize--;
	    }

	    node.value = 0;

	} else {
	    node.next[alphabet.position(word.charAt(d))] = delete(node.next[alphabet.position(word.charAt(d))], word,
		    d + 1);
	}

	if (node.value != 0) {
	    return node;
	}

	for (int i = 0; i < node.next.length; i++) {
	    if (node.next[i] != null) {
		return node;
	    }
	}

	return null;
    }

    /**
     * Returns all words of this trie.
     * 
     * @return the iterable object that contains all words of this trie
     */
    @Override
    public Iterable<String> words() {
	return this;
    }

    /**
     * Returns all words that begin with specified prefix
     * 
     * @param pref
     *            - the specified prefix
     * @return the iterable object that contains words with specified prefix
     */
    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
	return new Iterable<String>() {

	    @Override
	    public Iterator<String> iterator() {
		return new RWayTrieIterator(get(root, pref), pref, version);
	    }

	};
    }

    /**
     * Returns the number of words in this trie
     * 
     * @return the number of words in this trie
     */
    @Override
    public int size() {
	return trieSize;
    }

    /**
     * Iterator for Trie table. Walks the tree breadthways
     * 
     * @author Andrii_Lehuta
     *
     */
    private class RWayTrieIterator implements Iterator<String> {

	private Queue<String> words = new LinkedList<String>();
	private Queue<Node> nodes = new LinkedList<Node>();
	private long version;

	public RWayTrieIterator(Node root, String pref, long version) {
	    if (root != null && RWayTrie.this.trieSize != 0) {
		nodes.add(root);
		words.add(pref);
		this.version = version;
	    }
	}

	@Override
	public boolean hasNext() {
	    return !nodes.isEmpty();
	}

	@Override
	public String next() {

	    if (version != RWayTrie.this.version) {
		throw new ConcurrentModificationException();
	    }

	    if (nodes.isEmpty()) {
		throw new NoSuchElementException();
	    }

	    String result = null;

	    while (!nodes.isEmpty() && result == null) {
		Node node = nodes.poll();
		String tmpWord = words.poll();

		if (node.value != 0) {
		    result = tmpWord;
		}

		for (int i = 0; i < node.next.length; i++) {
		    if (node.next[i] != null) {
			nodes.add(node.next[i]);
			words.add(tmpWord + alphabet.character(i));
		    }
		}
	    }

	    return result;
	}

    }

    /**
     * Returns iterator over all words of Trie
     * 
     * @return the iterator over all words of Trie
     */
    @Override
    public Iterator<String> iterator() {
	return new RWayTrieIterator(root, "", version);
    }

}
