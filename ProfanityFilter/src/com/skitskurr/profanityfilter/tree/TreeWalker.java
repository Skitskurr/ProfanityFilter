package com.skitskurr.profanityfilter.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeWalker {
	
	private Knot current;
	private final List<Integer> indices;
	private final TreeRun run;
	private char buffer;
	private int buffing = -1;
	private boolean dead = false;
	
	public TreeWalker(final Knot root, final TreeRun run) {
		this.current = root;
		this.run = run;
		this.indices = new ArrayList<>();
	}
	
	private TreeWalker(final Knot current, final TreeRun run, final List<Integer> indices, final char buffer, final int index) {
		this.current = current;
		this.run = run;
		this.indices = indices;
		this.buffer = buffer;
		this.buffing = index;
	}
	
	public void step(final int index, final char symbol) {
		if(this.buffing == -1) {
			this.run.addWalker(new TreeWalker(this.current, this.run, new ArrayList<>(this.indices), symbol, index));
		}
		
		if(current.hasChild(symbol)){
			if(!Character.isLetter(symbol)) {
				this.run.addWalker(new TreeWalker(this.current, this.run, new ArrayList<>(this.indices), this.buffer, this.buffing));
			}
			
			this.current = this.current.getChild(symbol);
			this.indices.add(index);
			
			if(this.buffing != -1) {
				if(this.current.hasChild(buffer)) {
					this.current = this.current.getChild(buffer);
					this.indices.add(this.buffing);
				}
				this.buffing = -1;
			}
			
			if(this.current.isProfane()) {
				this.run.addIndices(this.indices);
				this.indices.clear();
			}
		} else {
			if(Character.isLetter(symbol)) {
				this.dead = true;
			}
		}
	}
	
	public boolean isDead() {
		return this.dead;
	}

}
