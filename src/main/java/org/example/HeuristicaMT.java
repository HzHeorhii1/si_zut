package org.example;

import sac.State;
import sac.StateFunction;

public class HeuristicaMT extends StateFunction {
    public int getMTValue(SlidePullzle puzzle) {
        int n = SlidePullzle.n;
        int misplacedTiles = 0;
        for (int count = 0; count < n * n; count++) {
            int row = count / n;
            int col = count % n;
            misplacedTiles += (puzzle.board[row][col] != count && puzzle.board[row][col] != 0) ? 1 : 0;
        }
        return misplacedTiles;
    }

    @Override
    public double calculate(State state) {
        return (state instanceof SlidePullzle) ? getMTValue((SlidePullzle) state) : Double.NaN;
    }
}
