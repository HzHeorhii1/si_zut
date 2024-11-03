package org.example;
import sac.graph.*;
import java.util.Map;
import java.util.function.Supplier;

public class Main {
//    public static void main(String[] args) {
//        for (int experiment = 0; experiment < 10; experiment++) {
//            System.out.println("Experiment " + experiment);
//
//            SlidePullzle slidingPuzzle = new SlidePullzle(3);
//            HeuristicaManhattan heuristicaManhattan = new HeuristicaManhattan();
//            HeuristicaMT heuristicaMT = new HeuristicaMT();
//            slidingPuzzle.shuffler(100);
//            GraphSearchAlgorithm algorithm = new AStar(slidingPuzzle);
////            slidingPuzzle.setHFunction(heuristicaManhattan);
////            algorithm.execute();
//            slidingPuzzle.setHFunction(heuristicaMT);
//            algorithm.execute();
//        }
//    }

//    public static void main(String[] args) {
//        System.out.println("It started");
//        long t1 = System.currentTimeMillis();
//        SlidePullzle puzzle = new SlidePullzle(3);
//        AStar aStar = new AStar(puzzle);
//        BestFirstSearch bfs = new BestFirstSearch(puzzle);
//        for (int i = 0; i < 100; i++) {
//            puzzle.shuffler(1000);
//            aStar.execute();
//            bfs.execute();
//        }
//        long t2 = System.currentTimeMillis();
//        System.out.println("Experiment total time [s]: " + (0.001 * (t2 - t1)));
//        System.out.println("It ended");
//    }

    public static void main(String[] args) {
        for (int experiment = 1; experiment <= 2; experiment++) {
            System.out.println("proba " + experiment + ":");

            SlidePullzle slidingPuzzle = new SlidePullzle(3);
            HeuristicaManhattan heuristicaManhattan = new HeuristicaManhattan();
            HeuristicaMT heuristicaMT = new HeuristicaMT();
            slidingPuzzle.setHFunction(heuristicaManhattan);
            slidingPuzzle.shuffler(1000);
            GraphSearchAlgorithm algorithm = new AStar(slidingPuzzle);
            algorithm.execute();

            if (algorithm.getSolutions().isEmpty()) { System.out.println("no solution found"); }
            else {
                SlidePullzle solution = (SlidePullzle) algorithm.getSolutions().get(0);
                printSolutionDetails(solution, algorithm);
            }
            System.out.println();
        }
    }


    private static void printSolutionDetails(GraphState solution, GraphSearchAlgorithm algorithm) {
        Map<String, Supplier<String>> messages = Map.of(
                "solution", () -> "solution:\n" + solution,
                "path len", () -> "path len " + solution.getPath().size(),
                "mov along path", () -> "mov along path: " + solution.getMovesAlongPath(),
                "closed state", () -> "closed state: " + algorithm.getClosedStatesCount(),
                "open state", () -> "open state: " + algorithm.getOpenSet().size(),
                "time", () -> "time: " + algorithm.getDurationTime() + " ms"
        );
        messages.forEach((key, messageSupplier) -> System.out.println(messageSupplier.get()));
    }

}
