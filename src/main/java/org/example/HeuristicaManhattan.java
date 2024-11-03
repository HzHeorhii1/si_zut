package org.example;
import sac.State;
import sac.StateFunction;
import java.util.stream.IntStream;

public class HeuristicaManhattan extends StateFunction {
    public int getManhattanValue(SlidePullzle puzzle) {
        int n = SlidePullzle.n;
        return IntStream.range(0, n * n)
                .map(count -> {
                    int row = count / n;
                    int col = count % n;
                    int value = puzzle.board[row][col];
                    if (value == 0) return 0;
                    int targetRow = value / n;
                    int targetCol = value % n;
                    int diffBetweenRowAndTargetRow = row - targetRow;
                    int diffBetweenColAndTargetCol = col - targetCol;
                    return Math.abs(diffBetweenRowAndTargetRow) + Math.abs(diffBetweenColAndTargetCol);
                })
                .sum();
    }

    @Override
    public double calculate(State state) {
        return (state instanceof SlidePullzle) ? getManhattanValue((SlidePullzle) state) : Double.NaN;
    }
}