package bearmaps.hw4;


import edu.princeton.cs.algs4.Stopwatch;
import bearmaps.proj2ab.DoubleMapPQ;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Float.MAX_VALUE;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private DoubleMapPQ<Vertex> myPQ;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, WeightedEdge> edgeTo;
    private int numStatesExplored;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
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
            Vertex p = myPQ.removeSmallest();
            numStatesExplored++;

            /*ArrayList<WeightedEdge<Vertex>> hold = (ArrayList) input.neighbors(p);
            distTo.add(hold.get(0).from());
            distTo.add(input.neighbors(p))*/
            for (WeightedEdge e : input.neighbors(p)) {
               // System.out.print("A");
                if (!distTo.containsKey(e.to())) {
                    distTo.put((Vertex) e.to(), (double) MAX_VALUE);
                }
                Vertex from = (Vertex) e.from();
                Vertex to = (Vertex) e.to();
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
                ArrayList<Vertex> mySolution = new ArrayList<>();
                mySolution.add(end);
                WeightedEdge goal = edgeTo.get(end);
                while (goal != null) {
                    mySolution.add((Vertex) goal.from());
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
    public List<Vertex> solution() {
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
