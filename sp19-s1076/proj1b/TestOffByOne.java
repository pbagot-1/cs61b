import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();


    @Test
    public void testOffByOne() {

        assertFalse(offByOne.equalChars('a', 'e'));  // false
        assertFalse(offByOne.equalChars('z', 'a'));  // false
        assertFalse(offByOne.equalChars('a', 'a'));  // false
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('&', '%'));

    }
}
