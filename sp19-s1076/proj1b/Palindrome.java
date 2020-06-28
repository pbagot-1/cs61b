public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> myDeck = new LinkedListDeque();
        for (int i = 0; i < word.length(); i++) {
            myDeck.addLast(word.charAt(i));
        }

        return myDeck;
    }


    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != (char) d.removeLast()) {
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);

        boolean odd;
        if (word.length() % 2 == 1) {
            odd = true;
        } else {
            odd = false;
        }

        for (int i = 0; i < word.length(); i++) {
            if (!(odd && i == ((word.length() + 1) / 2) - 1)) {
                if (!cc.equalChars(word.charAt(i), (char) d.removeLast())) {
                    return false;
                }
            } else {
                d.removeLast();
            }
        }

        return true;
    }



}
