package com.skitskurr.profanityfilter.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TreeRun {
	
	private final char[] text;
	private final Knot root;
	
	private final Set<Integer> indices = new HashSet<Integer>();
	private final List<TreeWalker> walkers = new LinkedList<TreeWalker>();
	private final List<TreeWalker> newWalkers = new ArrayList<TreeWalker>();
	private final List<TreeWalker> deadWalkers = new ArrayList<TreeWalker>();
	
	public TreeRun(final String text, final Knot root) {
		this.text = text.toCharArray();
		this.root = root;
	}
	
	void addIndices(final Collection<Integer> indices) {
		this.indices.addAll(indices);
	}
	
	void addWalker(final TreeWalker walker) {
		this.newWalkers.add(walker);
	}
	
	private void step(final int index) {
		this.walkers.addAll(this.newWalkers);
		this.newWalkers.clear();
		this.walkers.add(new TreeWalker(root, this));
		
		final char symbol = this.text[index];
		
		for(final TreeWalker walker: this.walkers) {
			if(walker.step(index, symbol)){
				deadWalkers.add(walker);
			}
		}
		
		for(final TreeWalker walker: this.deadWalkers) {
			this.walkers.remove(walker);
		}
	}
	
	public String run() {
		for(int i = 0; i < this.text.length; i++) {
			step(i);
		}
		
		for(final int index: this.indices) {
			text[index] = '*';
		}
		
		return new String(text);
	}

}
