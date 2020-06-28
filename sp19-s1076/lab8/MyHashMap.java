import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;


public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int SIZE_INITIALIZER = 16;
    private static final double LOADFACTOR_INITIALIZER = 0.75;


    private myPair<K, V>[] buckets;
    private int size;
    private double loadFactor;
    private HashSet<K> keySet;

    private static class myPair<K, V> {
        private K key;
        private V val;
        myPair<K, V> nextInBucket;
        public myPair(K key, V val) {
            this.key = key;
            this.val = val;

        }

        public myPair() {

        }
    }

    private int loadFactor() {
        return size / buckets.length;
    }

    @Override
    public boolean containsKey(K key) {
        if (get(key) == null) {
            return false;
        }
        return true;
    }

    public MyHashMap() {
        buckets = new myPair[SIZE_INITIALIZER];
    }

    public MyHashMap(int initialSize) {
        buckets = new myPair[initialSize];
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new myPair[initialSize];
        this.loadFactor = loadFactor;
    }

    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i++) {
            this.buckets[i] = new myPair<>();
        }
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        return Math.floorMod(key.hashCode(), buckets.length);
    }

    @Override
    public V get(K key) {
        myPair<K, V> temp = buckets[hash(key)];
        while (temp != null) {
            if (temp.key == key) {
                return temp.val;
            }
            temp = temp.nextInBucket;
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        myPair<K, V> temp = buckets[hash(key)];
        while (temp != null) {
            if (temp.key == key) {
                temp.val = value;
                return;
            }
        }
        temp = new myPair<>(key, value);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        return this.keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();

    }
}