import java.util.ArrayList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private static final int R = 128; // ASCII
    private Node root;    // root of trie

    private class DataIndexedCharMap<V> {
        private V[] items;
        private ArrayList<Character> keys;

        DataIndexedCharMap(int R) {
            items = (V[]) new Object[R];
            keys = new ArrayList<>();
        }

        public void put(char c, V val) {
            items[c] = val;
            keys.add(c);
        }

        public V get(char c) {
            return items[c];
        }

        public List<Character> getKeys() {
            return keys;
        }
    }

    public MyTrieSet() {
        root = new Node(false, R);
        root.isKey = false;
        root.next = new DataIndexedCharMap(R);
    }

    private class Node {
        private char ch;
        private boolean isKey;
        private DataIndexedCharMap<Node> next;


        private Node(char c, boolean b, int R) {
            ch = c;
            isKey = b;
            next = new DataIndexedCharMap<Node>(R);

        }

        private Node(boolean b, int R) {
            isKey = b;
            next = new DataIndexedCharMap<Node>(R);

        }


    }

    /**
     * Clears all items out of Trie
     */
    @Override
    public void clear() {
        root = new Node(false, R);
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise
     */
    @Override
    public boolean contains(String key) {
        Node temp = root;
        char a;

        for (int i = 0; i < key.length(); i++) {
            if (temp == null) {
                return false;
            }
            if (temp.next.get(key.charAt(i)) == null) {
                return false;
            }
            a = key.charAt(i);
            temp = temp.next.get(a);
        }

        if (temp.isKey) {
            return true;
        }
        return false;
    }


    /**
     * Inserts string KEY into Trie
     */
    @Override
    public void add(String key) {
        boolean newKey = false;
        char tempChar = key.charAt(0);
        int hold = 1;
        Node temp = root;
        int checkForDuplicate = 1;
        try {
            while (temp.next.get(tempChar) != null && hold < key.length()) {

                temp = temp.next.get(tempChar);
                tempChar = key.charAt(hold);
                hold++;
                checkForDuplicate++;
            }
        } catch (NullPointerException e) {
            System.out.println();
        }


        while (hold < key.length()) {
            newKey = true;
            temp.next.put(tempChar, new Node(false, R));
            temp = temp.next.get(tempChar);
            tempChar = key.charAt(hold);
            hold++;
        }
        // if (temp.next.getKeys().isEmpty()) {
        if (temp.next.get(tempChar) != null) {
            Node n = temp.next.get(tempChar);
            n.isKey = true;
            temp.next.put(tempChar, /*new Node(true, R)*/ n);
        } else {
            temp.next.put(tempChar, new Node(true, R));
        }


    }

    /**
     * Returns a list of all words that start with PREFIX
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {

        char tempChar = prefix.charAt(0);
        int hold = 1;
        Node temp = root;

        try {
            while (temp.next.get(tempChar) != null && hold < prefix.length()) {

                temp = temp.next.get(tempChar);
                tempChar = prefix.charAt(hold);
                hold++;
            }
        } catch (NullPointerException e) {
            System.out.println();
        }
        ArrayList<String> results = new ArrayList<>();
        results = (ArrayList<String>) collect(temp);
        ArrayList<String> returnThis = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            returnThis.add(prefix + results.get(i));
        }
        return returnThis;
    }

    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    /*public static void main(String[] args) {
        MyTrieSet test = new MyTrieSet();
        test.add("cool");
        test.add("cod");
        System.out.println(test.contains("cool"));
        System.out.println(test.contains("col"));
        System.out.println(test.contains("cod"));

        ArrayList<String> wordz = new ArrayList<>();
        wordz = (ArrayList<String>) test.keysWithPrefix("co");

        for (String testin : wordz) {
            System.out.println(testin);
        }

        String[] saStrings = new String[]{"same", "sam"};

        MyTrieSet t = new MyTrieSet();
        for (String s : saStrings) {
            t.add(s);
        }
    } */


    private List collect(Node n) {
        ArrayList<String> results = new ArrayList<>();
        for (char c : n.next.getKeys()) {
            colHelp("", results, n.next.get(c));
            //Character.toString(c)
        }
        return results;
    }

    private void colHelp(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }

        for (char c : n.next.getKeys()) {
            colHelp(s + c, x, n.next.get(c));
        }
    }

}
