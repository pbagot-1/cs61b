//*******************************************************************
// Welcome to CompileJava!
// If you experience any issues, please contact us ('More Info')  -->
// Also, sorry that the "Paste" feature no longer works! GitHub broke
// this (so we'll switch to a new provider): https://blog.github.com\
// /2018-02-18-deprecation-notice-removing-anonymous-gist-creation/
//*******************************************************************

import java.lang.Math; // headers MUST be above the first class

// one class needs to have a main() method
public class Cool
{
    // arguments are passed using the text field below this editor
    public static void main(String[] args)
    {
        Integer[] cool = new Integer[1000];


        }
        double startTime = System.nanoTime();

        OtherClass.sortMethod(cool);
        double endTime   = System.nanoTime();
        double totalTime = (double) (endTime - startTime) / 1000000;
        System.out.println(totalTime + "seconds elapsed before end of sort. (Input size " + cool.length + ")");
        for (int a : cool)
        {
            System.out.println(a);
        }
    }
}

 class OtherClass
{
    private String message;
    private boolean answer = false;
    public OtherClass(String input)
    {
        message = "Why, " + input + " Isn't this something?";
    }
    public String toString()
    {
        return message;
    }

    public static Integer[] sortMethod(Integer[] args) {
        /* epic selection sort  O(n^2) :D */
        int currentMin = 10000;
        int hold = 0;

        for (int i = 0; i < args.length; i++) {
            currentMin = 10000;
            for (int j = i; j < args.length; j++) {
                if (args[j] < currentMin) {
                    currentMin = args[j];
                    hold = j;
                }
            }
                int temp = args[i];
                args[i] = currentMin;
                args[hold] = temp;

        }

        return args;
    }
}