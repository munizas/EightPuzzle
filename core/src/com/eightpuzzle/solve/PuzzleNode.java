package com.eightpuzzle.solve;

import java.util.List;
import java.util.ArrayList;

import com.eightpuzzle.heuristic.ComputeH;
import com.eightpuzzle.heuristic.Heuristic;

public class PuzzleNode {
	private int g, h;
	private List<Integer> state;
	private PuzzleNode parent;
	private static Heuristic heuristic;

	static {
		heuristic = ComputeH.MANHATTAN_DISTANCE;
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

	public int getF() {
		return g + h;
	}

	public List<PuzzleNode> getNextNodes() {
		List<PuzzleNode> nextNodes = new ArrayList<PuzzleNode>();
		
		int holeIndex = state.lastIndexOf(0);
		if (holeIndex-3 >= 0) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex-3));
			newState.set(holeIndex-3, 0);
			nextNodes.add(new PuzzleNode(this, newState));
		}
		
		if (holeIndex+3 < state.size()) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex+3));
			newState.set(holeIndex+3, 0);
			nextNodes.add(new PuzzleNode(this, newState));
		}
		
		if (holeIndex != 2 && holeIndex != 5 && holeIndex != 8) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex+1));
			newState.set(holeIndex+1, 0);
			nextNodes.add(new PuzzleNode(this, newState));
		}
		
		if (holeIndex != 3 && holeIndex != 6 && holeIndex != 0) {
			List<Integer> newState = new ArrayList<Integer>(state);
			newState.set(holeIndex, newState.get(holeIndex-1));
			newState.set(holeIndex-1, 0);
			nextNodes.add(new PuzzleNode(this, newState));
		}
		
		return nextNodes;
	}
	
	public String toString() {
		return "g=" + g + ", h=" + h + ", state: " + state.toString();
	}
}
