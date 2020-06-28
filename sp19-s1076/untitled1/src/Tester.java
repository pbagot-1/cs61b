public class Tester {

        public static void main(String [] args){
            Student alice = new CSStudent();
            CSStudent bob = new CSStudent();

            bob.code(alice);
            bob.code(bob);
            alice.code(alice);
            alice.code(bob);

            mult(5);
        }

        private static int mult(int a) {
            return a*5;
        }
    }

