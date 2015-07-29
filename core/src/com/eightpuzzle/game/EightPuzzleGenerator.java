package com.eightpuzzle.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class EightPuzzleGenerator {
	
	private static Random rand;
	
	static {
		rand = new Random();
	}
	
	public static ArrayList<Integer> newPuzzle() {
		ArrayList<Integer> newPuzzle = randSeq();
		while (!isSolvable(newPuzzle))
			newPuzzle = randSeq();
		insertRandomZero(newPuzzle);
		return newPuzzle;
	}
	
	public static ArrayList<Integer> randSeq() {
		Integer[] seq = {1, 2, 3, 4, 5, 6, 7, 8};
		Collections.shuffle(Arrays.asList(seq));
		ArrayList<Integer> newSeq = new ArrayList<Integer>();
		for (int i = 0; i < seq.length; i++)
			newSeq.add(seq[i]);
		return newSeq;
	}
	
	public static boolean isSolvable(ArrayList<Integer> seq) {
		int invCount = 0;
		for (int i = 0; i < seq.size(); i++) {
			for (int j = i+1; j < seq.size(); j++) {
				if (seq.get(i) > seq.get(j))
					invCount++;
			}
		}
		return invCount % 2 == 0;
	}
	
	public static void insertRandomZero(ArrayList<Integer> seq) {
		seq.add(rand.nextInt(9), 0);
	}
	
}
