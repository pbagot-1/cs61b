package bearmaps;

import java.util.List;
//import java.util.ArrayList;

public class KDTree implements PointSet {
    private Node root;

    private class Node {
       // private static final boolean HORIZONTAL = false;
        private Point p;
        private boolean isHorizontal;

        private Node leftChild;
        private Node rightChild;

        private Node(Point point, boolean horizontal) {
            p = point;
            isHorizontal = horizontal;
        }

    }

    //has at least size 1
    public KDTree(List<Point> points) {

        for (Point p : points) {
            root = insert(p, root, true);
        }

    }

    private Node insert(Point p, Node node, boolean orientation) {
        if (node == null) {
            return new Node(p, orientation);
        }

        if (p.getX() == node.p.getX() && p.getY() == node.p.getY()) {
            return node;
        }

        int compare = comparePoints(p, node.p, orientation);

        if (compare < 0) {
            node.leftChild = insert(p, node.leftChild, !orientation);
        } else if (compare > 0) {
            node.rightChild = insert(p, node.rightChild, !orientation);
        } else {
            node.rightChild = insert(p, node.rightChild, !orientation);
        }

        return node;
    }

    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation) {
            return Double.compare(a.getX(), b.getX());
        }

        return Double.compare(a.getY(), b.getY());
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearestHelper(root, goal, root).p;
    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }

        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }

        Node goodSide;
        Node badSide;
        if (comparePoints(goal, n.p, n.isHorizontal) < 0) {
            goodSide = n.leftChild;
            badSide = n.rightChild;

        } else {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        }


        best = nearestHelper(goodSide, goal, best);
        Point bestBadSidePoint;
        if (n.isHorizontal) {
            bestBadSidePoint = new Point(n.p.getX(), goal.getY());
        } else {
            bestBadSidePoint = new Point(goal.getX(), n.p.getY());
        }
        if (Point.distance(best.p, goal) > Point.distance(bestBadSidePoint, goal)) {
            best = nearestHelper(badSide, goal, best);
        }
        return best;
    }

    /*public static void main(String[] args) {
        Point a = new Point(2, 3);
        Point b = new Point(4, 2);
        Point c = new Point(4, 5);
        Point d = new Point(3, 3);
        Point e = new Point(1, 5);
        Point f = new Point(4, 4);
        ArrayList<Point> pointList = new ArrayList<>();
        pointList.add(a);
        pointList.add(b);
        pointList.add(c);
        pointList.add(d);
        pointList.add(e);
        pointList.add(f);

        KDTree myTree = new KDTree(pointList);

    }*/
}
