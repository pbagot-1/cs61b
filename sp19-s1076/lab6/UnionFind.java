import java.util.*;

public class UnionFind {

    int[] disjointSet;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        disjointSet = new int[n];
        for (int i = 0; i < n; i++) {
            disjointSet[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= disjointSet.length) {
            throw new IllegalArgumentException("Not a valid index.");
        }

    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        if (disjointSet[v1] > 0)
            return sizeOf(disjointSet[v1]);

        return disjointSet[v1] * -1;

    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        if (disjointSet[v1] < 0)
            return disjointSet[v1];

        return disjointSet[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);

        if (root1 == root2) {
            return true;
        }

        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {

        int sizev1 = sizeOf(v1);
        int sizev2 = sizeOf(v2);

        if (!connected(v1, v2)) {
            if (sizeOf(v1) < sizeOf(v2)) {
                int v1Root = find(v1);
                disjointSet[v1Root] = find(v2);
                disjointSet[find(v2)] = -(sizev1 + sizev2);


            } else if (sizeOf(v1) > sizeOf(v2)) {
                int v2Root = find(v2);
                disjointSet[v2Root] = find(v1);

                disjointSet[find(v1)] = -(sizev1 + sizev2);

            } else {
                int v1Root = find(v1);
                disjointSet[v1Root] = find(v2);

                disjointSet[find(v2)] = -(sizev1 + sizev2);

            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        if (disjointSet[vertex] > 0)
            return find(disjointSet[vertex]);

        return vertex;
    }

//    public static void main(String[] args) {
//        UnionFind myThing = new UnionFind(10);
//        myThing.union(1,2);
//        System.out.println(myThing.connected(1,2));
//        myThing.union(3 ,2);
//        System.out.println(myThing.connected(1,3));
//        System.out.println(myThing.connected(1,4));
//
//        System.out.println(myThing.sizeOf(3));
//
//
//    }

}
