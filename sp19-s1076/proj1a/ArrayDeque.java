public class ArrayDeque<T>  {
    private T[] arrayOfTs;
    private int size;
    private int nextFirst;
    private int nextLast;

    private static final int CAP = 8;

    public ArrayDeque() {
        arrayOfTs = (T[]) new Object[CAP];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    /*public static void main(String[] args) {
        ArrayDeque<String> a = new ArrayDeque();
        a.addFirst("item1");
        a.addFirst("item2");
        a.addFirst("item3");
        a.addFirst("item14");
        System.out.println(a.get(0));
    }*/


    public ArrayDeque(ArrayDeque other) {
        arrayOfTs = (T[]) new Object[other.capacity()];
        nextFirst = other.getNextFirst();
        nextLast = other.getNextLast();
        size = other.size();
        int holdBeginning = upOne(nextFirst);
        int holdEnd = downOne(nextLast);

        int t = 0;
        for (int i = holdBeginning; i != holdEnd; i = upOne(i), t += 1) {
            arrayOfTs[i] = (T) other.get(t);
        }

        arrayOfTs[holdEnd] = (T) other.get(t);

    }

    private int getNextFirst() {
        return nextFirst;
    }

    private int getNextLast() {
        return nextLast;
    }

    public void addFirst(T item) {
        if (atCapacity()) {
            extendCapacity();
        }

        arrayOfTs[nextFirst] = item;
        nextFirst = downOne(nextFirst);
        size++;
    }

    public void addLast(T item) {
        if (atCapacity()) {
            extendCapacity();
        }

        arrayOfTs[nextLast] = item;
        nextLast = upOne(nextLast);
        size++;
    }

    private int downOne(int current) {
        return (current - 1) - Math.floorDiv((current - 1), arrayOfTs.length) * arrayOfTs.length;
    }

    private int upOne(int current) {
        return (current + 1) - Math.floorDiv((current + 1), arrayOfTs.length) * arrayOfTs.length;
    }

    private void extendCapacity() {
        resize(2 * size());
    }

    /*Resizes array on both ends by making a new T array of twice the size and copying items one by
    one from old (temp)*/
    private void resize(int newSize) {
        T[] tempItems = arrayOfTs;
        int holdBeginning = upOne(nextFirst);
        int holdEnd = downOne(nextLast);

        arrayOfTs = (T[]) new Object[newSize];
        nextFirst = 4;
        nextLast = 5;

        for (int i = holdBeginning; i != holdEnd; i = upOne(i, tempItems.length)) {
            arrayOfTs[nextLast] = tempItems[i];
            nextLast = upOne(nextLast);
        }

        arrayOfTs[nextLast] = tempItems[holdEnd];
        nextLast = upOne(nextLast);
    }

    /* Overloaded this function because it was needed for copying with correct length */
    private int upOne(int current, int overloadLength) {
        return (current + 1) - Math.floorDiv((current + 1), overloadLength) * overloadLength;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return size;
    }

    private int capacity() {
        return arrayOfTs.length;
    }

    public T removeFirst() {
        if (size > 0) {
            if (isSmallEnoughToResize()) {
                reduceSizeOfArray();
            }
            nextFirst = upOne(nextFirst);
            T tToRemove = arrayOfTs[nextFirst];
            arrayOfTs[nextFirst] = null;
            size--;
            return tToRemove;
        }
        return null;
    }
    public T removeLast() {
        if (size > 0) {
            if (isSmallEnoughToResize()) {
                reduceSizeOfArray();
            }
            nextLast = downOne(nextLast);
            T tToRemove = arrayOfTs[nextLast];
            arrayOfTs[nextLast] = null;
            size--;
            return tToRemove;
        }
        return null;
    }

    private void reduceSizeOfArray() {
        resize(arrayOfTs.length / 2);
    }


    public T get(int index) {
        return arrayOfTs[findCorrectOffset(nextFirst + 1, index)];
    }

    private int findCorrectOffset(int myStart, int userIndex) {
        return (myStart + userIndex)
                - Math.floorDiv(myStart + userIndex, arrayOfTs.length) * arrayOfTs.length;
    }


    public void printDeque() {
        for (int i = upOne(nextFirst); i != nextLast; i = upOne(i)) {
            System.out.print(arrayOfTs[i] + " ");
        }

        System.out.println();
    }

    private boolean atCapacity() {
        if (size() == arrayOfTs.length) {
            return true;
        }

        return false;
    }

    /*Want to keep it at least 8 capacity*/
    private boolean isSmallEnoughToResize() {
        return (this.size < arrayOfTs.length / 4) && (arrayOfTs.length >= 16);
    }

}
