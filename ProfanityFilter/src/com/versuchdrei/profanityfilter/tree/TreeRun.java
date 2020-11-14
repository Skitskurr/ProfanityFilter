package com.versuchdrei.profanityfilter.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * an instance of a run walking down the text, 
 * remembers all indices of letters of profane words and replaces them with stars
 * @author VersuchDrei
 * @version 1.0
 */
public class TreeRun {
	
	private final char[] text;
	private final Knot root;
	
	private final Set<Integer> indices = new HashSet<Integer>();
	private List<TreeWalker> walkers = new ArrayList<TreeWalker>();
	private final List<TreeWalker> newWalkers = new ArrayList<TreeWalker>();
	
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
			walker.step(index, symbol);
		}
		
		this.walkers = this.walkers.stream().filter(walker -> !walker.isDead()).collect(Collectors.toList());
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
