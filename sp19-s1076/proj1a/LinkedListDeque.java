public class LinkedListDeque<T> {
    private class TNode {

        private T item;
        private TNode prev;
        private TNode next;

        TNode(TNode prev, TNode next, T item) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private TNode sentinel;
    private TNode front;
    private int size;

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        front = sentinel;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    public T getRecursive(int index) {
        if (this.isEmpty()) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, TNode ref) {

        if (index != 0) {
            return getRecursiveHelper(index - 1, ref.next);
        }

        return ref.item;
    }

   /* public LinkedListDeque(T item) {
        sentinel = new TNode(null, null, item);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        sentinel.next = new TNode(sentinel, sentinel, item);
        size = 1;
    } */

    /** Adds an item to the front of the list. */
    public void addFirst(T item) {
        TNode newNode = new TNode(sentinel, sentinel.next, item);
        sentinel.next = newNode;
        newNode.next.prev = newNode;
        size += 1;
    }

    /** Adds an item to the BACK! of the list. */
    public void addLast(T item) {
        TNode newNode = new TNode(sentinel.prev, sentinel, item);
        sentinel.prev = newNode;
        newNode.prev.next = newNode;
        size += 1;
    }

    public void printDeque() {
        TNode ref = sentinel.next;
        while (ref != sentinel) {
            System.out.print(ref.item + " ");
            ref = ref.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size > 0) {
            T itemToReturn = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return itemToReturn;
        }
        return null;
    }

    public T removeLast() {
        if (size > 0) {
            T itemToReturn = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return itemToReturn;
        }
        return null;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    public T get(int index) {
        if (this.isEmpty()) {
            return null;
        }
        TNode ref = sentinel.next;
        int counter = 0;
        while (counter < index) {
            ref = ref.next;
            counter++;
        }

        return ref.item;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }
}
