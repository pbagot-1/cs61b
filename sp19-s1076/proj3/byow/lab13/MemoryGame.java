package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame(seed);
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        Random random = new Random(seed);
    }

    public String generateRandomString(int n, Random gen) {
        String randomString = "";
        while (randomString.length() < n) {
            randomString += CHARACTERS[Math.abs(gen.nextInt()) % 26];
        }
        return randomString;
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.setFont(new Font("msyh", Font.BOLD, 30));
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.text(width / 2, height - 10, "Round: " + round);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        String letterHold = new String(letters);
        while (letterHold.length() > 0) {
            StdDraw.setFont(new Font("msyh", Font.BOLD, 30));
            StdDraw.text(width / 2, height / 2, Character.toString(letterHold.charAt(0)));
            StdDraw.setPenColor(StdDraw.BLACK);
            drawFrame(Character.toString(letterHold.charAt(0)));
            StdDraw.pause(1000);
            // StdDraw.clear(Color.WHITE);
            drawFrame("");
            //StdDraw.show();


            StdDraw.pause(500);

            letterHold = letterHold.substring(1);
        }
    }

    public String solicitNCharsInput(int n) {
        int numLettersRead = 0;
        String userInput = new String();
        while (numLettersRead < n) {
            if (StdDraw.hasNextKeyTyped()) {
                userInput += StdDraw.nextKeyTyped();
                numLettersRead++;
            }

        }
        return userInput;
    }

    public void startGame(int seed) {
        round = 1;
        boolean gameOver = false;
        Random random = new Random(seed);
        String firstSeq = generateRandomString(round, random);
        flashSequence(firstSeq);
        while (!gameOver) {
            String current = solicitNCharsInput(round);
            System.out.println("current: " + current);

            if (!current.equals(firstSeq)) {
                gameOver = true;
                break;
            }
            round++;
            firstSeq = generateRandomString(round, random);
            System.out.println(firstSeq);
            flashSequence(firstSeq);


        }

        System.out.println("Game over! You made it to round: " + round);
    }

}
