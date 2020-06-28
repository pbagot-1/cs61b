import edu.princeton.cs.algs4.Queue;

//import edu.princeton.cs.algs4.Quick;
import org.junit.Test;
import org.junit.Assert;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> myQueue = new Queue();
        myQueue.enqueue("gang");
        myQueue.enqueue("Gang");
        myQueue.enqueue("ang");
        myQueue.enqueue("bng");
        myQueue = MergeSort.mergeSort(myQueue);
        Assert.assertTrue(isSorted(myQueue));
    }

    @Test
    public void testMergeSort() {
        Queue<String> myQueue = new Queue();
        myQueue.enqueue("gang");
        myQueue.enqueue("Gang");
        myQueue.enqueue("ang");
        myQueue.enqueue("bng");
        myQueue = QuickSort.quickSort(myQueue);
        Assert.assertTrue(isSorted(myQueue));

    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
