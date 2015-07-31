package com.eightpuzzle.solve;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.eightpuzzle.heuristic.ComputeH;
import com.eightpuzzle.heuristic.Heuristic;

public class PuzzleNode implements Comparable<PuzzleNode> {
	private int g, h;
	private List<Integer> state;
	private PuzzleNode parent;
	private static Heuristic heuristic;
	
	public static List<Integer> GOAL;
	
	// used to help compute heuristic estimated cost
	public static int[][] simGoal = {
			{1, 2, 3},
			{4, 5, 6},
			{7, 8, 0}
	};

	static {
		heuristic = ComputeH.MANHATTAN_DISTANCE;
		GOAL = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0));
	}

	public PuzzleNode(PuzzleNode parent, List<Integer> state) {
		this.parent = parent;
		this.state = new ArrayList<Integer>(state);

		if (parent != null)
			g = parent.getG() + 1;

		h = heuristic.compute(this);
	}

	public int getG() {
		return g;
	}
	
	public int getH() {
		return h;
	}

	public int getF() {
		return g + h;
	}
	
	public List<Integer> getState() {
		return state;
	}
	
	public PuzzleNode getParent() {
		return parent;
	}

	public List<PuzzleNode> getNextNodes() {
		List<PuzzleNode> nextNodes = new ArrayList<PuzzleNode>();
		
		int holeIndex = state.lastIndexOf(0);
		if (holeIndex-3 >= 0) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex-3));
			newState.set(holeIndex-3, 0);
			if (parent==null || !parent.getState().equals(newState))
				nextNodes.add(new PuzzleNode(this, newState));
		}
		
		if (holeIndex+3 < state.size()) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex+3));
			newState.set(holeIndex+3, 0);
			if (parent==null || !parent.getState().equals(newState))
				nextNodes.add(new PuzzleNode(this, newState));
		}
		
		if (holeIndex != 2 && holeIndex != 5 && holeIndex != 8) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex+1));
			newState.set(holeIndex+1, 0);
			if (parent==null || !parent.getState().equals(newState))
				nextNodes.add(new PuzzleNode(this, newState));
		}
		
		if (holeIndex != 3 && holeIndex != 6 && holeIndex != 0) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex-1));
			newState.set(holeIndex-1, 0);
			if (parent==null || !parent.getState().equals(newState))
				nextNodes.add(new PuzzleNode(this, newState));
		}
		
		return nextNodes;
	}
	
	public String toString() {
		return "g=" + g + ", h=" + h + ", state: " + state.toString();
	}

	@Override
	public int compareTo(PuzzleNode o) {
		return getF() - o.getF();
	}
}
