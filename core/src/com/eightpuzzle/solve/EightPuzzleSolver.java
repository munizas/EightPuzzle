package com.eightpuzzle.solve;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

public class EightPuzzleSolver {
	
	public static Deque<List<Integer>> solve(PuzzleNode node) {
		if (node == null)
			return new ArrayDeque<List<Integer>>();
		
		int count = 1;
		PriorityQueue<PuzzleNode> pQue = new PriorityQueue<PuzzleNode>();
		pQue.add(node);
		PuzzleNode pn = null;
		while (!pQue.isEmpty()) {
			pn = pQue.poll();
			if (isGoal(pn))
				break;
			pQue.addAll(pn.getNextNodes());
			count += pn.getNextNodes().size();
		}
		
		Deque<List<Integer>> solutionStack = new ArrayDeque<List<Integer>>();
		while (pn != null) {
			solutionStack.addFirst(pn.getState());
			pn = pn.getParent();
		}
		
		System.out.println("nodes generated = " + count);
		return solutionStack;
	}
	
	public static boolean isGoal(PuzzleNode node) {
		return node.getH() == 0;
	}

}
