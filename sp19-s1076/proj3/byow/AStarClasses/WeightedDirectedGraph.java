package byow.AStarClasses;


import java.util.ArrayList;
import java.util.List;

/**
 * A very simple (and literal) example of an AStarGraph.
 * Created by hug.
 */
public class WeightedDirectedGraph implements AStarGraph<Tile> {
    /* Represents the list of edges from a single vertex. */
    public class EdgeList {
        public List<WeightedEdge<Tile>> list;
        public EdgeList() {
            list = new ArrayList<>();
        }
    }

    public EdgeList[] adj;

    public WeightedDirectedGraph(int V) {
        adj = new EdgeList[V];
        for (int i = 0; i < V; i += 1) {
            adj[i] = new EdgeList();
        }
    }

    @Override
    public List<WeightedEdge<Tile>> neighbors(Tile v) {
        return adj[v.getOrder()].list;
    }

    /* Very crude heuristic that just returns the weight
       of the smallest edge out of vertex s.
     */
    @Override
    public double estimatedDistanceToGoal(Tile s, Tile goal) {
        double dx = s.getXPos()- goal.getXPos();
        double dy = s.getYPos()- goal.getYPos();

        double result = (Math.sqrt((dx*dx)+(dy*dy)));
        return result;
    }

    public void addEdge(Tile p, Tile q, double w) {
        WeightedEdge<Tile> e = new WeightedEdge<>(p, q, w);
      //  System.out.println("Added edge starting at X: " + e.from().getXPos() +"and Y: "+ e.from().getYPos());
       // System.out.println("to place starting at X: " + e.to().getXPos() +"and Y: "+ e.to().getYPos());

        adj[p.getOrder()].list.add(e);
    }

}
