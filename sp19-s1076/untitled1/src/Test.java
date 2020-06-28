class Test {
    public void Gulgate(Test arg) {System.out.println("hi nigga");}
    public void Gulgate(TestExtender arg) {System.out.println("hi");}
}

class TestExtender extends Test
{
    static int[] a;
    public void Gulgate(Test arg) {System.out.println("hi a");}

}

class Main {
    public static void main(String[] args) {
        System.out.println(TestExtender.a[0]);
        Test a = new TestExtender();
        TestExtender b = (TestExtender) a;
        b.Gulgate( b);

    }

}
