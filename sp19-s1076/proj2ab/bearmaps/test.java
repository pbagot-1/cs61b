package bearmaps;

public class test {
    public static void main(String[] args) {
        recursionTest(1000);
        System.out.println("done");
    }

    public static void recursionTest(int amount) {
        if (amount == 0) {
            return;
        }
        for (int i = amount; i >= 0; i--) {
            System.out.println(i);

        }

        recursionTest(amount-1);

    }
}
