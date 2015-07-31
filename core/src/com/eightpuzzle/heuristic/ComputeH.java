package com.eightpuzzle.heuristic;

import java.util.List;

import com.eightpuzzle.solve.PuzzleNode;

public enum ComputeH implements Heuristic {
	MANHATTAN_DISTANCE() {
		public int compute(PuzzleNode node) {
			int h = 0;
			List<Integer> state = node.getState();

			// model the state in game board form, so use 3*3 array
			Integer[][] simBoard = new Integer[3][3];
			for(int j = 0; j < 3; j++)
				simBoard[0][j] = state.get(j);
			for(int j = 0; j < 3; j++)
				simBoard[1][j] = state.get(j+3);
			for(int j = 0; j < 3; j++)
				simBoard[2][j] = state.get(j+6);

			//for loop finds current position and desired position of the row and column of a tile
			int needRow = 0, needCol = 0;
			int atRow = 0, atCol = 0;
			for(int i = 0; i < 8; i++) {

				for(int a = 0; a < 3; a++) {
					for(int b = 0; b < 3; b++) {
						if(simBoard[a][b] == i+1) {
							atRow = a;
							atCol = b;
						}
						if(PuzzleNode.simGoal[a][b] == i+1) {
							needRow = a;
							needCol = b;
						}
					}
				}
				//add distance from current position to desired position
				//this is found by adding the difference of the rows and columns
				h += Math.abs(atRow-needRow) + Math.abs(atCol-needCol);
			}

			return h;
		}
	},
	OTHER() {
		public int compute(PuzzleNode node) {
			return -1;
		}
	};
}
