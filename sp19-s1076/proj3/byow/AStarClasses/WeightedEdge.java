package byow.AStarClasses;

/**
 * Utility class that represents a weighted edge.
 * Created by hug.
 */
public class WeightedEdge<Tile> {
    private Tile v;
    private Tile w;
    private double weight;

    public WeightedEdge(Tile v, Tile w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    public Tile from() {
        return v;
    }
    public Tile to() {
        return w;
    }
    public double weight() {
        return weight;
    }
}
