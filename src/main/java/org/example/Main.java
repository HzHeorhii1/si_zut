package org.example;
import sac.graph.*;
import java.util.Map;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        long durationManhattan = 0;
        long totalOpenManhattan = 0;
        long totalOpenMT = 0;
        long totalVlosedManhattan = 0;
        long totalVlosedMT = 0;
        long durationMT = 0;
        for (int experiment = 1; experiment <= 100; experiment++) {
            System.out.println("Experiment " + experiment);
            SlidePullzle slidingPuzzle = new SlidePullzle(3);
            HeuristicaManhattan heuristicaManhattan = new HeuristicaManhattan();
            HeuristicaMT heuristicaMT = new HeuristicaMT();
            slidingPuzzle.shuffler(100);
            // HeuristicaManhattan
            slidingPuzzle.setHFunction(heuristicaManhattan);
            GraphSearchAlgorithm algorithmManhattan = new AStar(slidingPuzzle);
            long startManhattan = System.nanoTime();
            algorithmManhattan.execute();
            long endManhattan = System.nanoTime();
            long diff1 = endManhattan - startManhattan;
            durationManhattan += diff1 / 1_000_000;
            System.out.println("HeuristicaManhattan time: " + diff1 / 1_000_000 + " ms");
            totalOpenManhattan += algorithmManhattan.getClosedStatesCount();
            totalVlosedManhattan += algorithmManhattan.getOpenSet().size();
            System.out.println("closed states: " + algorithmManhattan.getClosedStatesCount());
            System.out.println("open states: " + algorithmManhattan.getOpenSet().size());
            // HeuristicaMT
            slidingPuzzle.setHFunction(heuristicaMT);
            GraphSearchAlgorithm algorithmMT = new AStar(slidingPuzzle);
            long startMT = System.nanoTime();
            algorithmMT.execute();
            long endMT = System.nanoTime();
            long diff2 = endMT - startMT;
            durationMT += diff2 / 1_000_000;
            System.out.println("HeuristicaMT time: " + diff2 / 1_000_000 + " ms");
            totalOpenMT += algorithmMT.getClosedStatesCount();
            totalVlosedMT += algorithmMT.getOpenSet().size();
            System.out.println("closed states: " + algorithmMT.getClosedStatesCount());
            System.out.println("open states: " + algorithmMT.getOpenSet().size());
        }
        System.out.println("Total open for HeuristicaManhattan: " + totalOpenManhattan);
        System.out.println("Total open for HeuristicaMT: " + totalOpenMT);
        System.out.println("Total closed for HeuristicaManhattan: " + totalVlosedManhattan);
        System.out.println("Total closed for HeuristicaMT: " + totalVlosedMT);
        System.out.println("Total time for HeuristicaManhattan: " + durationManhattan + " ms");
        System.out.println("Total time for HeuristicaMT: " + durationMT + " ms");
    }
}