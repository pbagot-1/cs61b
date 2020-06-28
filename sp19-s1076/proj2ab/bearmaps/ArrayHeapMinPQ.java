package bearmaps;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    public ArrayList<Node> heap;
    private HashSet<T> itemsPresent;
    public HashMap<T, Integer> indexMap;

    public static class Node<T> {
        private double priorityVal;
        public T val;

        Node(T item, double priority) {
            priorityVal = priority;
            val = item;
        }
    }

    public ArrayHeapMinPQ() {
        heap = new ArrayList<Node>();
        heap.add(null);
        itemsPresent = new HashSet<T>();
        indexMap = new HashMap<T, Integer>();
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (itemsPresent.contains(item)) {
            throw new IllegalArgumentException();
        }

        itemsPresent.add(item);

        Node holdNode = new Node(item, priority);
        heap.add(holdNode);
        indexMap.put(item, heap.size() - 1);
        int hold = (heap.size() - 1);
        //int mult = 1;
        //swimming up

        while (hold / (2) >= 1) {

            if (heap.get(hold / (2)).priorityVal > heap.get(hold).priorityVal) {
                Node temp = heap.get(hold / (2));
                heap.set(hold / (2), holdNode);
                //System.out.println("->" + hold / 2 + "<-");

                indexMap.replace((T)holdNode.val, hold / 2 + ((hold % 2 == 0) ? 0 : 1));

                heap.set(hold, temp);

                indexMap.replace((T)temp.val,hold);

                hold = hold / (2);
            }
            else {
                break;
            }
        }

    }

    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        if (itemsPresent.contains(item)) {
            return true;
        }

        return false;
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (heap.size() == 1) {
            throw new NoSuchElementException();
        }
        return (T) heap.get(1).val;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (heap.size() == 1) {
            throw new NoSuchElementException();
        }
        T smallest = (T) heap.get(1).val;

        indexMap.remove(heap.get(1).val);
        heap.set(1, heap.get(heap.size() - 1));
        indexMap.replace((T)heap.get(1).val,1);
       // System.out.println("-> replacing " + (T)heap.get(1).val + "index as " + 1 + "<-");

        heap.remove(heap.size() - 1);


        int k = 1;
        int level = 0;

        while (level <= Math.log(heap.size() - 1) / Math.log(2)) {

            if (k * 2 + 1 == heap.size() - 1) {

                if (heap.get(k * 2 + 1).priorityVal < heap.get(k).priorityVal && heap.get(k * 2).priorityVal < heap.get(k).priorityVal) {
                    if (heap.get(k * 2 + 1).priorityVal <= heap.get(k * 2).priorityVal) {
                        Node temp = heap.get(k * 2 + 1);
                        heap.set(k * 2 + 1, heap.get(k));

                        indexMap.replace((T)heap.get(k).val,k*2 +1);
                       // System.out.println("-> replacing " + (T)heap.get(k).val + "index as " + k*2 +1 + "<-");

                        heap.set(k, temp);

                        indexMap.replace((T)temp.val,k);
                      //  System.out.println("-> replacing " + (T)temp.val + "index as " + k + "<-");

                        k = k * 2 + 1;
                    } else {
                        Node temp = heap.get(k * 2);
                        heap.set(k * 2, heap.get(k));

                        indexMap.replace((T)heap.get(k).val,k*2);
                      //  System.out.println("-> replacing " + (T)heap.get(k).val + "index as " + k*2 + "<-");
                        heap.set(k, temp);

                        indexMap.replace((T)temp.val,k);
                       // System.out.println("-> replacing " + (T)temp.val + "index as " + k + "<-");

                        k = k * 2;
                    }
                } else if (heap.get(k * 2 + 1).priorityVal < heap.get(k).priorityVal) {
                    Node temp = heap.get(k * 2 + 1);
                    heap.set(k * 2 + 1, heap.get(k));

                    indexMap.replace((T)heap.get(k).val,k*2 +1);
                   // System.out.println("-> replacing " + (T)heap.get(k).val + "index as " + k*2 + 1 + "<-");

                    heap.set(k, temp);

                    indexMap.replace((T)temp.val,k);
                    //System.out.println("-> replacing " + (T)temp.val + "index as " + k + "<-");


                    k = k * 2 + 1;
                } else if (heap.get(k * 2).priorityVal < heap.get(k).priorityVal) {
                    Node temp = heap.get(k * 2);
                    heap.set(k * 2, heap.get(k));

                    indexMap.replace((T)heap.get(k).val,k*2);

                    heap.set(k, temp);

                   indexMap.replace((T)temp.val,k);


                    k = k * 2;
                }
            } else if (/*heap.get(k * 2) != null*/ k * 2 == heap.size() - 1) {
                if (heap.get(k * 2).priorityVal < heap.get(k).priorityVal) {
                    Node temp = heap.get(k * 2);
                    heap.set(k * 2, heap.get(k));

                    indexMap.replace((T)heap.get(k).val,k*2);

                    heap.set(k, temp);

                    indexMap.replace((T)temp.val,k);

                    k = k * 2;
                }
            } else {
                break;
            }
            level++;
        }


        return smallest;
    }

    /* Returns the number of items in the PQ. */
    public int size() {
        return heap.size() - 1;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        if (!itemsPresent.contains(item)) {
            throw new NoSuchElementException();
        }
        //System.out.println(indexMap.get(item));
        int index = indexMap.get(item);
        heap.get(indexMap.get(item)).priorityVal = priority;
        if (indexMap.get(item) != 1 && heap.get(index).priorityVal < heap.get(index / 2).priorityVal ) {
            changePriorityHelper("up", index);
        }
        else {
            changePriorityHelper("down", index);
        }





    }

    private void changePriorityHelper(String direction, int hold) {
        if (direction.equals("up")) {
            Node holdNode = heap.get(hold);
            while (hold / (2) >= 1) {

                if (heap.get(hold / (2)).priorityVal > heap.get(hold).priorityVal) {
                    Node temp = heap.get(hold / (2));
                    heap.set(hold / (2), holdNode);
                    //System.out.println("->" + hold / 2 + "<-");

                    indexMap.replace((T)holdNode.val, hold / 2 + ((hold % 2 == 0) ? 0 : 1));

                    heap.set(hold, temp);

                    indexMap.replace((T)temp.val,hold);

                    hold = hold / (2);
                }
                else {
                    break;
                }
            }
        }

        if (direction.equals("down")) {
            int k = hold;
            int level = (int) (Math.log(hold) / Math.log(2));

            while (level <= Math.log(heap.size() - 1) / Math.log(2)) {

                if (k * 2 + 1 == heap.size() - 1) {

                    if (heap.get(k * 2 + 1).priorityVal < heap.get(k).priorityVal && heap.get(k * 2).priorityVal < heap.get(k).priorityVal) {
                        if (heap.get(k * 2 + 1).priorityVal <= heap.get(k * 2).priorityVal) {
                            Node temp = heap.get(k * 2 + 1);
                            heap.set(k * 2 + 1, heap.get(k));

                            indexMap.replace((T)heap.get(k).val,k*2 +1);
                          //  System.out.println("-> replacing " + (T)heap.get(k).val + "index as " + k*2 +1 + "<-");

                            heap.set(k, temp);

                            indexMap.replace((T)temp.val,k);
                          //  System.out.println("-> replacing " + (T)temp.val + "index as " + k + "<-");

                            k = k * 2 + 1;
                        } else {
                            Node temp = heap.get(k * 2);
                            heap.set(k * 2, heap.get(k));

                            indexMap.replace((T)heap.get(k).val,k*2);
                          //  System.out.println("-> replacing " + (T)heap.get(k).val + "index as " + k*2 + "<-");
                            heap.set(k, temp);

                            indexMap.replace((T)temp.val,k);
                           // System.out.println("-> replacing " + (T)temp.val + "index as " + k + "<-");

                            k = k * 2;
                        }
                    } else if (heap.get(k * 2 + 1).priorityVal < heap.get(k).priorityVal) {
                        Node temp = heap.get(k * 2 + 1);
                        heap.set(k * 2 + 1, heap.get(k));

                        indexMap.replace((T)heap.get(k).val,k*2 +1);
                        //System.out.println("-> replacing " + (T)heap.get(k).val + "index as " + k*2 + 1 + "<-");

                        heap.set(k, temp);

                        indexMap.replace((T)temp.val,k);
                       // System.out.println("-> replacing " + (T)temp.val + "index as " + k + "<-");


                        k = k * 2 + 1;
                    } else if (heap.get(k * 2).priorityVal < heap.get(k).priorityVal) {
                        Node temp = heap.get(k * 2);
                        heap.set(k * 2, heap.get(k));

                        indexMap.replace((T)heap.get(k).val,k*2);

                        heap.set(k, temp);

                        indexMap.replace((T)temp.val,k);


                        k = k * 2;
                    }
                } else if (/*heap.get(k * 2) != null*/ k * 2 == heap.size() - 1) {
                    if (heap.get(k * 2).priorityVal < heap.get(k).priorityVal) {
                        Node temp = heap.get(k * 2);
                        heap.set(k * 2, heap.get(k));

                        indexMap.replace((T)heap.get(k).val,k*2);

                        heap.set(k, temp);

                        indexMap.replace((T)temp.val,k);

                        k = k * 2;
                    }
                } else {
                    break;
                }
                level++;
            }
        }
    }
    /*public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(2, 5);
        test.add(3, 6);
        test.add(4, 7);
        test.add(5, 5);
        for (int i = 1; i < test.heap.size(); i++) {
            System.out.println(test.heap.get(i).val);
        }
        System.out.println();

        for (Object name: test.indexMap.keySet()) {

            String key = name.toString();
            String value = test.indexMap.get(name).toString();
            System.out.println(key + " " + value);


        }

        System.out.println();
        test.removeSmallest();
        for (int i = 1; i < test.heap.size(); i++) {
            System.out.println(test.heap.get(i).val);
        }
        System.out.println();
        test.removeSmallest();
        for (int i = 1; i < test.heap.size(); i++) {
            System.out.println(test.heap.get(i).val);
        }
        System.out.println();
    }*/
}
