package byow.AStarClasses;


import edu.princeton.cs.algs4.Stopwatch;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Float.MAX_VALUE;

public class AStarSolver<Tile> implements ShortestPathsSolver<Tile> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Tile> solution;
    private double timeSpent;
    private DoubleMapPQ<Tile> myPQ;
    private HashMap<Tile, Double> distTo;
    private HashMap<Tile, WeightedEdge> edgeTo;
    private int numStatesExplored;

    public AStarSolver(AStarGraph<Tile> input, Tile start, Tile end, double timeout) {
        myPQ = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        Stopwatch sw = new Stopwatch();
        myPQ.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        //System.out.print("A");
        while (myPQ.size() != 0  && !myPQ.getSmallest().equals(end)) {
            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution = List.of();
                break;
            }
            Tile p = myPQ.removeSmallest();
            numStatesExplored++;

            /*ArrayList<WeightedEdge<Tile>> hold = (ArrayList) input.neighbors(p);
            distTo.add(hold.get(0).from());
            distTo.add(input.neighbors(p))*/
            for (WeightedEdge e : input.neighbors(p)) {
               // System.out.print("A");
                if (!distTo.containsKey(e.to())) {
                    distTo.put((Tile) e.to(), (double) MAX_VALUE);
                }
                Tile from = (Tile) e.from();
                Tile to = (Tile) e.to();
                double edgeWeight = e.weight();
                double distToThisEdge = edgeWeight + distTo.get(from);
                if (distToThisEdge < distTo.get(to)) {
                 //   System.out.print("A");
                    distTo.put(to, distToThisEdge);
                    edgeTo.put(to, e);
                    if (myPQ.contains(to)) {
                        myPQ.changePriority(to, distToThisEdge
                                + input.estimatedDistanceToGoal(to, end));
                    } else {
                        myPQ.add(to, distToThisEdge + input.estimatedDistanceToGoal(to, end));
                    }
                }
            }
        }
        if (myPQ.size() != 0) {
            if (myPQ.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                ArrayList<Tile> mySolution = new ArrayList<>();
                mySolution.add(end);
                WeightedEdge goal = edgeTo.get(end);
                while (goal != null) {
                    mySolution.add((Tile) goal.from());
                    solutionWeight += goal.weight();
                    goal = edgeTo.get(goal.from());

                }


                Collections.reverse(mySolution);

                solution = mySolution;

            }
        } else {
            if (sw.elapsedTime() < timeout) {
                outcome = SolverOutcome.UNSOLVABLE;
                solution = List.of();
            }
        }

        timeSpent = sw.elapsedTime();

    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Tile> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
