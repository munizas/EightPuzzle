package com.eightpuzzle.heuristic;

import com.eightpuzzle.solve.PuzzleNode;

public enum ComputeH implements Heuristic {
	MANHATTAN_DISTANCE() {
		public int compute(PuzzleNode node) {
			return 1;
		}
	},
	OTHER() {
		public int compute(PuzzleNode node) {
			return -1;
		}
	};
}
