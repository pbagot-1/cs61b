public class Hw11a{
	public static void main(String[] args)
{
int col = 0;
int row = 0;
int SIZE = 5;
while (row < SIZE) {

while (col < row) {
System.out.print('*');
col = col + 1;
}
row = row + 1;
col = 0;
System.out.println('*');

}
}
}