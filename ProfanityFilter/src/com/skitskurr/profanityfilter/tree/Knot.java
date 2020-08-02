package com.skitskurr.profanityfilter.tree;

import java.util.HashMap;

public class Knot {

	private final HashMap<Character, Knot> children = new HashMap<>();

	private boolean profane = false;
	
	public Knot() {
	}
	
	public Knot addChild(final char[] letters) {
		return addChild(letters, new Knot());
	}
	
	public Knot addChild(final char[] letters, final Knot child) {
		if(!children.containsKey(letters[0])) {
			for(final char letter: letters) {
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
