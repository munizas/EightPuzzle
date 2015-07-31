package com.eightpuzzle.heuristic;

import com.eightpuzzle.solve.PuzzleNode;

public interface Heuristic {
	int compute(PuzzleNode node);
}
