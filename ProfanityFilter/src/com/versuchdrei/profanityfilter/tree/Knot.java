package com.versuchdrei.profanityfilter.tree;

import java.util.HashMap;

/**
 * a knot in the profanity tree
 * @author VersuchDrei
 * @version 1.0
 */
public class Knot {

	private final HashMap<Character, Knot> children = new HashMap<>();

	private boolean profane = false;
	
	public Knot() {
	}
	
	public Knot addChild(final char[] letters) {
		return addChild(letters, new Knot());
	}
	
	public Knot addChild(final char[] letters, final Knot child) {
		for(final char letter: letters) {
			// it should be enough to only do one check outside of the for loop
			// but that could mess up if a character is in more than one charGroup
			// also this is only done once on server start, so a loss of a few milliseconds doesn't matter
			if(!this.children.containsKey(letter)) {
				this.children.put(letter, child);
				// every knot gets itself as child
				// this way you can't just type a letter twice to bypass the filter
				child.children.put(letter, child);
			}
		}
		
		return children.get(letters[0]);
	}
	
	public Knot getChild(final char symbol) {
		return children.get(symbol);
	}
	
	public boolean hasChild(final char symbol) {
		return children.containsKey(symbol);
	}
	
	public void setProfane() {
		this.profane = true;
	}
	
	public boolean isProfane() {
		return this.profane;
	}
	
}
