package org.example;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SlidePullzle extends GraphStateImpl {
    protected int row, col;
    protected static int n;
    protected int voidField;
    protected byte[][] board;

    public SlidePullzle(int n) {
        SlidePullzle.n = (byte) n;
        board = new byte[n][n];
        IntStream.range(0, n * n - 1).forEach(count -> {
            int i = count / n;
            int j = count % n;
            board[i][j] = (byte) (count + 1);
        });
        board[n-1][n-1] = 0;
        voidField = n * n - 1;
        row = voidField / n;
        col = voidField % n;
    }

    public SlidePullzle(SlidePullzle sp) {
        board = Arrays.stream(sp.board)
                .map(byte[]::clone)
                .toArray(byte[][]::new);
        this.voidField = sp.voidField;
        this.row = sp.row;
        this.col = sp.col;
    }

    private GraphStateImpl move(int rowOffset, int colOffset, String simvol) {
        int newRow = row + rowOffset;
        int newCol = col + colOffset;
        boolean isOutOfBoard = newRow < 0 || newRow >= n || newCol < 0 || newCol >= n;
        if (isOutOfBoard) { return this; }
        SlidePullzle thisCopy = new SlidePullzle(this);
        thisCopy.board[row][col] = thisCopy.board[newRow][newCol];
        thisCopy.board[newRow][newCol] = 0;
        thisCopy.row = newRow;
        thisCopy.col = newCol;
        thisCopy.voidField += rowOffset * n + colOffset;
        thisCopy.setMoveName(simvol);
        return thisCopy;
    }

    public GraphStateImpl moveLeft() {
        return move(0, -1, "←");
    }

    public GraphStateImpl moveRight() {
        return move(0, 1, "→");
    }

    public GraphStateImpl moveDown() {
        return move(1, 0, "↓");
    }

    public GraphStateImpl moveUp() {
        return move(-1, 0, "↑");
    }

    public static void expand(int d, GraphState s) {
        System.out.println(d <= 0 ? s : "");
        s.generateChildren().stream()
                .filter(t -> s != t)
                .forEach(t -> expand(d - 1, t));
    }

    public void shuffler(int movesCount) {
        Random random = new Random();
        for (int i = 0; i < movesCount; i++) {
            List<GraphState> moves = generateChildren();
            int position = random.nextInt(moves.size());
            SlidePullzle nextMove = (SlidePullzle) moves.get(position);
            this.board = nextMove.board;
            this.row = nextMove.row;
            this.col = nextMove.col;
            this.voidField = nextMove.voidField;
        }
    }

    @Override
    public String toString() {
        final int cellSize = 5;
        final String borderLine = "@".repeat(n * cellSize + 1) + "\n";
        String boardString = IntStream.range(0, n)
                .mapToObj(i -> IntStream.range(0, n)
                        .mapToObj(j -> String.format("@%1$3d ", board[i][j]))
                        .collect(Collectors.joining("")) + "@\n")
                .collect(Collectors.joining(borderLine, borderLine, borderLine));
        return boardString;
    }

    @Override
    public List<GraphState> generateChildren() {
        List<GraphState> children = new ArrayList<>();
        addIfNotNull(children, moveDown());
        addIfNotNull(children, moveUp());
        addIfNotNull(children, moveRight());
        addIfNotNull(children, moveLeft());
        return children;
    }

    private void addIfNotNull(List<GraphState> list, GraphState state) {
        if (state != null) list.add(state);
    }

    @Override
    public boolean isSolution() {
        return java.util.stream.IntStream.range(0, n * n)
                .allMatch(count -> {
                    int row = count / n;
                    int col = count % n;
                    boolean isCorrectValue = board[row][col] == count;
                    return isCorrectValue;
                });
    }


}