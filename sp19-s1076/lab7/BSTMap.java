import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private K myKey;
    private V myVal;
    private BSTMap<K, V> myLeftNode;
    private BSTMap<K, V> myRightNode;

    private int size = 0;

    public BSTMap() {
        myKey = null;
        myVal = null;
        myLeftNode = null;
        myRightNode = null;
    }

    public BSTMap(K key, V val) {
        myKey = key;
        myVal = val;
        myLeftNode = null;
        myRightNode = null;
        size = 1;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        myLeftNode = null;
        myRightNode = null;
        myKey = null;
        myVal = null;
        size = 0;
    }

    public void printInOrder() {

        if (size == 0) {
            return;
        }
        if (myLeftNode != null) {
            myLeftNode.printInOrder();
        }
        System.out.println(myVal);

        if (myRightNode != null) {
            myRightNode.printInOrder();
        }


    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (size == 0) {
            return false;
        }

        if (myKey.equals(key)) {
            return true;
        }

        if (myKey.compareTo(key) < 0) {
            return myLeftNode.containsKey(key);
        }

        return myRightNode.containsKey(key);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }

        if (myKey.equals(key)) {
            return myVal;
        }

        if (myKey.compareTo(key) > 0) {
            return myLeftNode.get(key);
        }

        return myRightNode.get(key);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (size == 0) {
            myKey = key;
            myVal = value;
            size = 1;
        }

        if (myKey.equals(key)) {
            myVal = value;
        } else {

            if (myKey.compareTo(key) > 0) {
                if (myLeftNode == null) {
                    myLeftNode = new BSTMap<>(key, value);
                    myLeftNode.size = 1;
                    size++;
                } else {
                    myLeftNode.put(key, value);
                }

            } else {
                    if (myRightNode == null) {
                        myRightNode = new BSTMap<>(key, value);
                        myRightNode.size = 1;
                        size++;
                    } else {
                        myRightNode.put(key, value);
                    }
                }
            }
        }



        /* Returns a Set view of the keys contained in this map. */
        @Override
        public Set<K> keySet () {
            throw new UnsupportedOperationException();
        }

        /* Removes the mapping for the specified key from this map if present.
         * Not required for Lab 8. If you don't implement this, throw an
         * UnsupportedOperationException. */
        @Override
        public V remove (K key){
            throw new UnsupportedOperationException();
        }

        /* Removes the entry for the specified key only if it is currently mapped to
         * the specified value. Not required for Lab 8. If you don't implement this,
         * throw an UnsupportedOperationException.*/
        @Override
        public V remove (K key, V value){
            throw new UnsupportedOperationException();
        }
    }
