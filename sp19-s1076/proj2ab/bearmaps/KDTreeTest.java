package bearmaps;

//import org.junit.Test;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
//import java.util.Set;
//import java.util.HashSet;
import java.util.Random;

public class KDTreeTest {

    public static void main(String[] args) {
        ArrayList<Point> treeSet = new ArrayList<Point>();
        ArrayList<Point> pointSet = new ArrayList<Point>();
        Random random = new Random();
        random.setSeed(1333323);

        while (pointSet.size() < 10000) {
            //System.out.println(random.nextDouble());
            pointSet.add(new Point((int) (random.nextDouble() * 10000),
                    (int) (random.nextDouble() * 10000)));
        }

        while (treeSet.size() < 100000) {
            //System.out.println(random.nextDouble());
            treeSet.add(new Point((int) (random.nextDouble() * 10000),
                    (int) (random.nextDouble() * 10000)));
        }


        // for (Point p : pointSet) {
        //    System.out.println(p.getX());
        // }

        KDTree myTree = new KDTree(pointSet);
        NaivePointSet myNaive = new NaivePointSet(pointSet);


        for (Point p : pointSet) {
            assertEquals(myTree.nearest(p.getX(), p.getY()), myNaive.nearest(p.getX(), p.getY()));
        }

        long startTime = System.currentTimeMillis();

        for (Point p : pointSet) {
            Point a = myTree.nearest(p.getX(), p.getY());
        }

        long elapsed = ((System.currentTimeMillis() - startTime));

        long startTime2 = System.currentTimeMillis();

        for (Point p : pointSet) {
            Point a = myNaive.nearest(p.getX(), p.getY());
        }

        long elapsed2 = ((System.currentTimeMillis() - startTime2));
        System.out.println(elapsed);
        System.out.println(elapsed2);
       // assertTrue(elapsed2 / elapsed < .1);

    }
}
