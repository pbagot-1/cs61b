import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();


    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void isPalindromeTest() {

        assertFalse(palindrome.isPalindrome("cat"));


        assertFalse(palindrome.isPalindrome("Aaa"));


        assertTrue(palindrome.isPalindrome("dad"));


        assertTrue(palindrome.isPalindrome(""));

        assertTrue(palindrome.isPalindrome("a"));

        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("D"));

        assertFalse(palindrome.isPalindrome("aba", offByOne));

        assertFalse(palindrome.isPalindrome("Flake", offByOne));

        assertTrue(palindrome.isPalindrome("detrude", offByOne));

        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));  // true
        assertTrue(offBy5.equalChars('f', 'a'));  // true
        assertFalse(offBy5.equalChars('f', 'h'));  // false

    }

}
