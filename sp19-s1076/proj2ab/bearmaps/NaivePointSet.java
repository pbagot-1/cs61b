package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> myPointList;

    @Override
    public Point nearest(double x, double y) {
        Point testPoint = new Point(x, y);
        Point holdPoint = myPointList.get(0);

        double lowestDistance = Double.MAX_VALUE;
        for (Point a : myPointList) {
            if (Point.distance(a, testPoint) < lowestDistance) {
                holdPoint = a;
                lowestDistance = Point.distance(a, testPoint);
            }
        }
        return holdPoint;
    }

    public NaivePointSet(List<Point> points) {
        myPointList = points;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }
}
