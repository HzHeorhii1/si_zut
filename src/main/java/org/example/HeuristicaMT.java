package org.example;

import sac.State;
import sac.StateFunction;

public class HeuristicaMT extends StateFunction {
    public int getMTValue(SlidePullzle puzzle) {
        int n = SlidePullzle.n;
        int misplacedTiles = 0;
        int count = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                misplacedTiles += (puzzle.board[row][col] != count++ && puzzle.board[row][col] != 0) ? 1 : 0;
            }
        }
        return misplacedTiles;
    }

    @Override
    public double calculate(State state) {
        return (state instanceof SlidePullzle) ? getMTValue((SlidePullzle) state) : Double.NaN;
    }
}
