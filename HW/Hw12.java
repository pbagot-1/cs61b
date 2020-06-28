public class Hw12 {
    /** Returns the maximum value from m. */
    public static int max(int[] m) {
	int counter = 0;
	int max = 0;
	while (counter < m.length)
	{
	if (m[counter] > max){
	max = m[counter]; }
	counter = counter + 1;
	}

	
        return max;
    }
    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};   
	System.out.println(max(numbers));   
    }
}
