package com.versuchdrei.profanityfilter.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * a util class to build a profanity tree of Knots based on profane words
 * @author VersuchDrei
 * @version 1.0
 */
public class ProfanityTree {
	
	private static final String CONFIG_KEY_CHAR_GROUPS = "charGroups";
	private static final String CONFIG_KEY_SEQUENCE_GROUPS = "sequenceGroups";
	private static final String CONFIG_KEY_PROFANITY = "profanity";
	private static final String SEQUENCE_GROUP_DELIMITER = ";";
	
	public static Knot setupTree(final FileConfiguration config) {
		
		// read char groups
		final Map<Character, char[]> charGroups = new HashMap<>();
		final List<String> charGroupStrings = config.getStringList(ProfanityTree.CONFIG_KEY_CHAR_GROUPS);
		
		for(final String charGroupString: charGroupStrings) {
			final char[] charGroupArray = charGroupString.toCharArray();
			for(final char charGroupChar: charGroupArray) {
				charGroups.put(charGroupChar, charGroupArray);
			}
		}
		
		// read profane terms
		
		final List<String> profanity = config.getStringList(ProfanityTree.CONFIG_KEY_PROFANITY);
		final Set<Knot> knots = new HashSet<>();
		
		final Knot root = new Knot();
		knots.add(root);
		
		for(final String term: profanity) {
			final char[] letters = term.toCharArray();
			
			Knot knot = root;
			for(final char letter: letters) {
				knot = knot.addChild(getCharGroup(charGroups, letter));
				knots.add(knot);
			}
			knot.setProfane();
		}
		
		// apply sequence groups
		
		final List<List<char[]>> sequenceGroups = config.getStringList(ProfanityTree.CONFIG_KEY_SEQUENCE_GROUPS).stream()
				.map(group -> group.split(ProfanityTree.SEQUENCE_GROUP_DELIMITER))
				.map(group -> Arrays.stream(group).map(sequence -> sequence.toCharArray()).collect(Collectors.toList()))
				.collect(Collectors.toList());
		
		
		for(final Knot knot: knots) {
			for(final List<char[]> sequenceGroup: sequenceGroups) {
				Knot goal = null;
				for(final char[] sequence: sequenceGroup) {
					Knot child = knot;
					for(int i = 0; i < sequence.length && child != null; i++) {
						child = child.getChild(sequence[i]);
					}
					if(child != null) {
						goal = child;
						break;
					}
				}
				
				// for some reason goal == knot messes up the tree, so until I got that figured out we skip those
				// also they're not that important anyways
				if(goal != null && goal != knot) {
					for(final char[] sequence: sequenceGroup) {
						Knot child = knot;
						for(int i = 0; i < sequence.length; i++) {
							if(i == sequence.length - 1) {
								child = child.addChild(getCharGroup(charGroups, sequence[i]), goal);
							} else {
								child = child.addChild(getCharGroup(charGroups, sequence[i]));
							}
						}
					}
				}
			}
		}
		
		
		return root;
	}
	
	private static char[] getCharGroup(final Map<Character, char[]> charGroups, final char letter) {
		if(charGroups.containsKey(letter)) {
			return charGroups.get(letter);
		}
		return new char[] {letter};
	}

}
