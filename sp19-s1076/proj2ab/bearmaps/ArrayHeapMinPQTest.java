package bearmaps;

import org.junit.Test;
import org.junit.Assert;
import java.util.ArrayList;
public class ArrayHeapMinPQTest {


    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        System.out.println("First tree: ");

        System.out.println("The value is initially the same as the priority value for each in this tree:");
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 5);
        printSimpleHeapDrawing(test.heap);
        System.out.println("Calling remove smallest twice:");
        test.removeSmallest();
        test.removeSmallest();
        printSimpleHeapDrawing(test.heap);
        System.out.println("Changing priority of 5 to less than 4");
        test.changePriority(5, 3);
        printSimpleHeapDrawing(test.heap);
        System.out.println("Changing priority of 4 to less than 5");
        test.changePriority(4, 2);
        printSimpleHeapDrawing(test.heap);

        ArrayHeapMinPQ<Integer> test2 = new ArrayHeapMinPQ<>();

        System.out.println("Second tree: ");
        System.out.println("The value is initially the same as the priority value for each in this tree:");

        test2.add(5, 5);
        test2.add(4, 4);
        test2.add(3, 3);
        test2.add(2, 2);
        printSimpleHeapDrawing(test2.heap);
        System.out.println("Calling remove smallest twice:");
        test2.removeSmallest();
        test2.removeSmallest();
        printSimpleHeapDrawing(test2.heap);
        System.out.println("Changing priority of 5 to less than 4");
        test2.changePriority(5, 3);
        printSimpleHeapDrawing(test2.heap);
        System.out.println("Changing priority of 4 to less than 5");
        test2.changePriority(4, 2);
        printSimpleHeapDrawing(test2.heap);

    }

    public static void printSimpleHeapDrawing(ArrayList<ArrayHeapMinPQ.Node> heap) {
        int depth = ((int) (Math.log(heap.size()) / Math.log(2)));
        int level = 0;
        int itemsUntilNext = (int) Math.pow(2, level);
        for (int j = 0; j < depth; j++) {
            System.out.print(" ");
        }

        for (int i = 1; i < heap.size(); i++) {
            System.out.printf("%d ", heap.get(i).val);
            if (i == itemsUntilNext) {
                System.out.println();
                level++;
                itemsUntilNext += Math.pow(2, level);
                depth--;
                for (int j = 0; j < depth; j++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

    @Test
    public void assertThing(ArrayHeapMinPQ<Integer> test) {
        Assert.assertTrue(test.contains(2));
        Assert.assertTrue(test.contains(3));
        Assert.assertTrue(test.contains(4));
        Assert.assertTrue(test.contains(5));

    }

}
