package com.skitskurr.profanityfilter.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeWalker {
	
	private Knot current;
	private final List<Integer> indices;
	private final TreeRun run;
	
	public TreeWalker(final Knot root, final TreeRun run) {
		this.current = root;
		this.run = run;
		this.indices = new ArrayList<>();
	}
	
	private TreeWalker(final Knot current, final TreeRun run, final List<Integer> indices) {
		this.current = current;
		this.run = run;
		this.indices = indices;
	}
	
	public boolean step(final int index, final char symbol) {
		if(current.hasChild(symbol)){
			if(!Character.isLetter(symbol)) {
				this.run.addWalker(new TreeWalker(this.current, this.run, this.indices));
			}
			this.current = this.current.getChild(symbol);
			this.indices.add(index);
			
			if(this.current.isProfane()) {
				this.run.addIndices(this.indices);
				this.indices.clear();
			}
		} else {
			if(Character.isLetter(symbol)) {
				return true;
			}
		}
		return false;
	}

}
