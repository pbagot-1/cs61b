package byow.AStarClasses;

import java.util.List;

/**
 * Represents a graph of vertices.
 * Created by hug.
 */
public interface AStarGraph<Tile> {
    List<WeightedEdge<Tile>> neighbors(Tile v);
    double estimatedDistanceToGoal(Tile s, Tile goal);
}
