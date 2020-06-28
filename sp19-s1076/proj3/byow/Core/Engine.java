package byow.Core;

import byow.AStarClasses.*;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    //KeyboardListener my = new KeyboardListener();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    public static final long maxSeed = Long.MAX_VALUE;

    int round = 0;
    String loadedString = "";
    boolean worldGenerated = false;
    String lastAlphaNumericOrColonKeyTyped = "";
    String lastKeyTyped = "";
    String allKeyStrokes = "";
    CharacterClass myCharacter;
    EnemyClass myEnemy;
    TETile[][] worldFrame;
    Random generalUse = new Random(231);

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    //@Source https://stackoverflow.com/questions/4609068/is-there-a-way-to-get-keyboard-events-without-jframe
    public void interactWithKeyboard() {
        StdDraw.clear();

       // StdDraw.text(WIDTH - 10, HEIGHT - 10, "Round: " + round);

        AWTEventListener listener = new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                try {
                    KeyEvent evt = (KeyEvent) event;
                    StdDraw.clear();
                    System.out.println(StdDraw.mouseX());
                    System.out.println(StdDraw.mouseY());


                    if (evt.getID() == KeyEvent.KEY_PRESSED) {
                        lastKeyTyped = Character.toString(evt.getKeyChar());
                        if ((Character.isAlphabetic(evt.getKeyChar()) || Character.isDigit(evt.getKeyChar()) ||
                                evt.getKeyChar() == ':')) {
                            lastAlphaNumericOrColonKeyTyped = Character.toString(evt.getKeyChar());
                            allKeyStrokes += Character.toString(evt.getKeyChar());
                            System.out.println(lastAlphaNumericOrColonKeyTyped);
                            System.out.println(allKeyStrokes);

                            if (lastAlphaNumericOrColonKeyTyped.equals("Q") || lastAlphaNumericOrColonKeyTyped.equals("q")) {
                                if (allKeyStrokes.length() > 2) {
                                    if (allKeyStrokes.charAt(allKeyStrokes.length() - 2) == ':') {
                                        saveState(allKeyStrokes);
                                        System.exit(0);
                                    }

                                }
                            }

                            if (lastAlphaNumericOrColonKeyTyped.equals("L") || lastAlphaNumericOrColonKeyTyped.equals("l")) {
                                System.out.print('O');
                                ter.renderFrame(interactWithInputString(loadState()));
                            }

                            if (worldGenerated) {
                                if (lastAlphaNumericOrColonKeyTyped.equals("w") || lastAlphaNumericOrColonKeyTyped.equals("W")
                                        || lastAlphaNumericOrColonKeyTyped.equals("A") || lastAlphaNumericOrColonKeyTyped.equals("a")
                                        || lastAlphaNumericOrColonKeyTyped.equals("S") || lastAlphaNumericOrColonKeyTyped.equals("s")
                                        || lastAlphaNumericOrColonKeyTyped.equals("D") || lastAlphaNumericOrColonKeyTyped.equals("d")) {
                                    updateChar(worldFrame, Character.toLowerCase(lastAlphaNumericOrColonKeyTyped.charAt(0)), true);
                                }
                            }


                            //}
                        }
                    }
                    if (evt.getID() == KeyEvent.KEY_RELEASED) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);

        mainMenu();
        //ter.renderFrame(interactWithInputString("N123S"));

    }

    private static String loadState() {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileReader fileReader = new FileReader(f);
                StringBuffer stringBuffer = new StringBuffer();
                int numCharsRead;
                char[] charArray = new char[1024];
                while ((numCharsRead = fileReader.read(charArray)) > 0) {
                    stringBuffer.append(charArray, 0, numCharsRead);
                }
                fileReader.close();
                System.out.println("Contents of file:");
                System.out.println(stringBuffer.toString());
                return stringBuffer.toString();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            }
        }
        System.out.println("in load");

        /* In the case no Editor has been saved yet, we return a new one. */
        return new String();
    }

    private static void saveState(String saveString) {
        File f = new File("./save_data.txt");

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(saveString);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("File written to: " + f.getAbsolutePath());
    }

    public void mainMenu() {

        StdDraw.enableDoubleBuffering();

        Random random = new Random();
        // initial values
        double rx = WIDTH / 2, ry = HEIGHT / 2;     // position
        double vx = .25, vy = .25;     // velocity
        double radius = 2;              // radius
        StdDraw.setFont(new Font("SimSun", Font.BOLD, 16));
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 3, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Quit (Q)");
        StdDraw.text(WIDTH / 2, HEIGHT - 10, "Church Of Sods Guide to the Future");
        StdDraw.show();
        // main animation loop

        String firstChoice = "";

        while (!(firstChoice.equals("Q") || firstChoice.equals("q") ||
                firstChoice.equals("N") || firstChoice.equals("n") || firstChoice.equals("L") || firstChoice.equals("l"))) {
            firstChoice = solicitNCharsInput(1);
        }

        if (firstChoice.equals("Q") || firstChoice.equals("q")) {
            System.exit(0);
        } else if (firstChoice.equals("N") || firstChoice.equals("n")) {
            long seed = seedPrompt();
            TETile[][] world = generateWorld(seed);
            addCharToFrame(world);
            myEnemy = addEnemyToFrame(world);
            // getEnemyGraph(newEnemy);
            ter.renderFrame(world);

        } else {
            interactWithInputString(loadState());
        }
    }

    public ArrayList<Tile> getEnemyGraph(EnemyClass enemy) {
        int count = 0;
        Tile[][] tiles = new Tile[WIDTH][HEIGHT];
        int special = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (worldFrame[i][j] == Tileset.FLOOR || worldFrame[i][j] == Tileset.AVATAR) {
                    if (i == myCharacter.getXPos() && j == myCharacter.yPos) {
                        special = count;
                    }
                    tiles[i][j] = new Tile(i, j, count, worldFrame);
                    count++;
                }
            }
        }
        WeightedDirectedGraph map = new WeightedDirectedGraph(count);
        count = 0;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (worldFrame[i][j] == Tileset.FLOOR || worldFrame[i][j] == Tileset.AVATAR) {
                    for (Tile neighbor : tiles[i][j].neighborTiles(tiles)) {
                        map.addEdge(tiles[i][j], neighbor, 1);
                    }
                    count++;
                }
            }
        }

        System.out.println("THIS SPECIFIC TEST HAS NEIGHBORS.");
        ArrayList<WeightedEdge<Tile>> test = (ArrayList) map.adj[special].list;
        for (WeightedEdge a : test) {
            System.out.println("NEIGHBOR " + "1");
        }


        AStarSolver<Tile> solveGraph = new AStarSolver<>(map,
                tiles[enemy.getXPos()][enemy.getYPos()], tiles[myCharacter.getXPos()][myCharacter.getYPos()], 1000000);

        if (solveGraph.outcome() == SolverOutcome.SOLVED) {
            System.out.println("success");
        }
        int n = 0;
        for (Tile a : solveGraph.solution()) {
            if (n != 0 && n != solveGraph.solution().size() - 1) {
                System.out.println("Step " + n + " " + a.getXPos() + " " + a.getYPos());
                // worldFrame[a.getXPos()][a.getYPos()] = Tileset.WATER;
            }
            n++;
        }

        //for (Tile a : path) {
        //   System.out.println("Xpos: " + a.getXPos());
        //  System.out.println("Ypos: " + a.getYPos());
//
        //}
        ArrayList<Tile> e = new ArrayList<>(solveGraph.solution());
        return e;
    }

    public void updateChar(TETile[][] world, char direction, boolean show) {
        boolean gameOver = false;
            int xHold = myCharacter.xPos;
            int yHold = myCharacter.yPos;
        String hold =  worldFrame[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();

        if (direction == 'w' && (world[myCharacter.getXPos()][myCharacter.getYPos() + 1] == Tileset.LOCKED_DOOR)) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");
        }
        if (direction == 'a' && world[myCharacter.getXPos() - 1][myCharacter.getYPos()] == Tileset.LOCKED_DOOR) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");

        }
        if (direction == 's' && world[myCharacter.getXPos()][myCharacter.getYPos() - 1] == Tileset.LOCKED_DOOR) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");

        }
        if (direction == 'd' && world[myCharacter.getXPos() + 1][myCharacter.getYPos()] == Tileset.LOCKED_DOOR) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");
        }

        if (direction == 'w' && (world[myCharacter.getXPos()][myCharacter.getYPos() + 1] == Tileset.FLOOR)) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos()][myCharacter.getYPos() + 1] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos());
            myCharacter.setYPos(myCharacter.getYPos() + 1);
        }
        if (direction == 'a' && world[myCharacter.getXPos() - 1][myCharacter.getYPos()] == Tileset.FLOOR) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos() - 1][myCharacter.getYPos()] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos() - 1);
            myCharacter.setYPos(myCharacter.getYPos());
        }
        if (direction == 's' && world[myCharacter.getXPos()][myCharacter.getYPos() - 1] == Tileset.FLOOR) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos()][myCharacter.getYPos() - 1] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos());
            myCharacter.setYPos(myCharacter.getYPos() - 1);
        }
        if (direction == 'd' && world[myCharacter.getXPos() + 1][myCharacter.getYPos()] == Tileset.FLOOR) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos() + 1][myCharacter.getYPos()] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos() + 1);
            myCharacter.setYPos(myCharacter.getYPos());
        }
        ArrayList<Tile> enemyMoves = getEnemyGraph(myEnemy);
        if (enemyMoves.size() > 1 /*&& Math.abs(generalUse.nextInt()) % 2 == 0*/) {
            world[myEnemy.getXPos()][myEnemy.getYPos()] = Tileset.FLOOR;
            world[enemyMoves.get(1).getXPos()][enemyMoves.get(1).getYPos()] = Tileset.AVATAR;
            myEnemy.setXPos(enemyMoves.get(1).getXPos());
            myEnemy.setYPos(enemyMoves.get(1).getYPos());

        }
        if (myEnemy.getXPos() == myCharacter.getXPos() && myEnemy.getYPos() == myCharacter.getYPos()) {
            // gameOver = true;
            round = 0;
            StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");
            //gameOverScreen();
        }
        if (show && !gameOver) {
            ter.renderFrame(world);

            StdDraw.setPenColor(Color.RED);

            StdDraw.text(WIDTH - 10, HEIGHT - 1, "Round: " + round);
            StdDraw.text(WIDTH - 20, HEIGHT - 1, "Mouse at: " + hold);
            //  StdDraw.text(WIDTH / 2, HEIGHT - 10, "char X: " + myCharacter.getXPos());
            // StdDraw.text(10, 20, "char Y: " + myCharacter.getYPos());
            StdDraw.show();
        }
    }

    public void updateCharNoDraw(TETile[][] world, char direction, boolean show) {
        boolean gameOver = false;
        int xHold = myCharacter.xPos;
        int yHold = myCharacter.yPos;
        //String hold =  worldFrame[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();

        if (direction == 'w' && (world[myCharacter.getXPos()][myCharacter.getYPos() + 1] == Tileset.LOCKED_DOOR)) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
           // StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");
        }
        if (direction == 'a' && world[myCharacter.getXPos() - 1][myCharacter.getYPos()] == Tileset.LOCKED_DOOR) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
           // StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");

        }
        if (direction == 's' && world[myCharacter.getXPos()][myCharacter.getYPos() - 1] == Tileset.LOCKED_DOOR) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
           // StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");

        }
        if (direction == 'd' && world[myCharacter.getXPos() + 1][myCharacter.getYPos()] == Tileset.LOCKED_DOOR) {
            round++;
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            //StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");
        }

        if (direction == 'w' && (world[myCharacter.getXPos()][myCharacter.getYPos() + 1] == Tileset.FLOOR)) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos()][myCharacter.getYPos() + 1] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos());
            myCharacter.setYPos(myCharacter.getYPos() + 1);
        }
        if (direction == 'a' && world[myCharacter.getXPos() - 1][myCharacter.getYPos()] == Tileset.FLOOR) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos() - 1][myCharacter.getYPos()] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos() - 1);
            myCharacter.setYPos(myCharacter.getYPos());
        }
        if (direction == 's' && world[myCharacter.getXPos()][myCharacter.getYPos() - 1] == Tileset.FLOOR) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos()][myCharacter.getYPos() - 1] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos());
            myCharacter.setYPos(myCharacter.getYPos() - 1);
        }
        if (direction == 'd' && world[myCharacter.getXPos() + 1][myCharacter.getYPos()] == Tileset.FLOOR) {
            world[myCharacter.getXPos()][myCharacter.getYPos()] = Tileset.FLOOR;
            world[myCharacter.getXPos() + 1][myCharacter.getYPos()] = myCharacter.getTile();
            myCharacter.setXPos(myCharacter.getXPos() + 1);
            myCharacter.setYPos(myCharacter.getYPos());
        }
        ArrayList<Tile> enemyMoves = getEnemyGraph(myEnemy);
        if (enemyMoves.size() > 1 /*&& Math.abs(generalUse.nextInt()) % 2 == 0*/) {
            world[myEnemy.getXPos()][myEnemy.getYPos()] = Tileset.FLOOR;
            world[enemyMoves.get(1).getXPos()][enemyMoves.get(1).getYPos()] = Tileset.AVATAR;
            myEnemy.setXPos(enemyMoves.get(1).getXPos());
            myEnemy.setYPos(enemyMoves.get(1).getYPos());

        }
        if (myEnemy.getXPos() == myCharacter.getXPos() && myEnemy.getYPos() == myCharacter.getYPos()) {
            // gameOver = true;
            round = 0;
            //StdDraw.clear();
            interactWithInputString("n" + Math.abs(generalUse.nextLong()) % maxSeed
                    + "s");
            //gameOverScreen();
        }
        if (show && !gameOver) {
           // ter.renderFrame(world);

            //StdDraw.setPenColor(Color.RED);

           // StdDraw.text(WIDTH - 10, HEIGHT - 1, "Round: " + round);
           // StdDraw.text(WIDTH - 20, HEIGHT - 1, "Mouse at: " + hold);
            //  StdDraw.text(WIDTH / 2, HEIGHT - 10, "char X: " + myCharacter.getXPos());
            // StdDraw.text(10, 20, "char Y: " + myCharacter.getYPos());
          //  StdDraw.show();
        }
    }

    public void addSuitableDoor(TETile[][] world) {
        Random thisRandom = new Random(5);
        int x = Math.abs(thisRandom.nextInt()) % WIDTH;
        while (x == 0 || x == WIDTH - 1) {
            x = Math.abs(thisRandom.nextInt()) % WIDTH;
        }
        int y = Math.abs(thisRandom.nextInt()) % HEIGHT;
        while (y == 0 || y == HEIGHT - 1) {
            y = Math.abs(thisRandom.nextInt()) % HEIGHT;
        }

        while (!(world[x + 1][y] == Tileset.NOTHING || world[x - 1][y] == Tileset.NOTHING
                || world[x][y + 1] == Tileset.NOTHING || world[x][y - 1] == Tileset.NOTHING) || !(world[x + 1][y] == Tileset.FLOOR || world[x - 1][y] == Tileset.FLOOR
                || world[x][y + 1] == Tileset.FLOOR || world[x][y - 1] == Tileset.FLOOR)) {
            x = Math.abs(thisRandom.nextInt()) % WIDTH;
            while (x == 0 || x == WIDTH - 1) {
                x = Math.abs(thisRandom.nextInt()) % WIDTH;
            }
            y = Math.abs(thisRandom.nextInt()) % HEIGHT;
            while (y == 0 || y == HEIGHT - 1) {
                y = Math.abs(thisRandom.nextInt()) % HEIGHT;
            }
        }

        world[x][y] = Tileset.LOCKED_DOOR;

    }

    public void gameOverScreen() {
        StdDraw.clear();
        StdDraw.text(WIDTH / 2, HEIGHT - 10, "Game over! Click box to reload.");
        StdDraw.rectangle(WIDTH / 2, HEIGHT / 2, 10, 10);
        StdDraw.show();
        while (true) {
            // if (StdDraw.isMousePressed() && (StdDraw.mouseY() > HEIGHT / 2 - 10) && (StdDraw.mouseY() < HEIGHT / 2 + 10)
            // && (StdDraw.mouseX() >= WIDTH / 2 - 10) && (StdDraw.mouseX() <= WIDTH / 2 + 10)) {
            //  break;
            // }
        }
        // mainMenu();
    }

    public EnemyClass addEnemyToFrame(TETile[][] world) {
        Random placePick = new Random(5);
        int initialCharacterXPos = Math.abs(placePick.nextInt()) % WIDTH;
        int initialCharacterYPos = Math.abs(placePick.nextInt()) % HEIGHT;
        while (world[initialCharacterXPos][initialCharacterYPos] != Tileset.FLOOR) {
            initialCharacterXPos = Math.abs(placePick.nextInt()) % WIDTH;
            initialCharacterYPos = Math.abs(placePick.nextInt()) % HEIGHT;
        }
        myEnemy = new EnemyClass(initialCharacterXPos, initialCharacterYPos, "");
        world[initialCharacterXPos][initialCharacterYPos] = myEnemy.getTile();
        return myEnemy;
    }


    public void addCharToFrame(TETile[][] world) {
        Random placePick = new Random(5);
        int initialCharacterXPos = Math.abs(placePick.nextInt()) % WIDTH;
        int initialCharacterYPos = Math.abs(placePick.nextInt()) % HEIGHT;
        while (world[initialCharacterXPos][initialCharacterYPos] != Tileset.FLOOR) {
            initialCharacterXPos = Math.abs(placePick.nextInt()) % WIDTH;
            initialCharacterYPos = Math.abs(placePick.nextInt()) % HEIGHT;
        }
        myCharacter = new CharacterClass(initialCharacterXPos, initialCharacterYPos, "");
        world[initialCharacterXPos][initialCharacterYPos] = myCharacter.getTile();
    }

    public String solicitNCharsInput(int n) {

        int numLettersRead = 0;
        String userInput = "";
        while (numLettersRead < n) {
            if (StdDraw.hasNextKeyTyped()) {
                userInput += StdDraw.nextKeyTyped();
                numLettersRead++;

            }
        }
        return userInput;
    }

    public long seedPrompt() {
        StdDraw.clear();
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter a seed (press enter after done)");
        StdDraw.show();
        int chars = 0;
        String longHold = "";
        while (chars < 19) {
            longHold += solicitNCharsInput(1);
            if (lastKeyTyped.equals("\n")) {
                longHold = longHold.substring(0, longHold.length() - 1);
                allKeyStrokes += 'S';
                break;
            }
            if (lastKeyTyped.equals("s") || lastKeyTyped.equals("S")) {
                longHold = longHold.substring(0, longHold.length() - 1);
                allKeyStrokes += 'S';
                break;
            }
            StdDraw.clear();
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter a seed (press enter after done)");
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 5, longHold);
            StdDraw.show();
            chars++;
        }

        long prompt = Long.parseLong(longHold);

        return prompt;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        loadedString = input;
        worldFrame = new TETile[WIDTH + 1][HEIGHT + 1];
        //StdDraw.clear();
        long seed;
        String hold = new String();
        if (input.charAt(0) == 'n' || input.charAt(0) == 'N') {
            input = input.substring(1);
            while (input.charAt(0) != 'S' && input.charAt(0) != 's') {
                hold += input.charAt(0);
                // System.out.println(hold);
                input = input.substring(1);
            }
        }
        input = input.substring(1);

        seed = Long.parseLong(hold);
        worldFrame = generateWorld(seed);
        addCharToFrame(worldFrame);
        EnemyClass newEnemy = addEnemyToFrame(worldFrame);
        while (input.length() > 0) {
            updateCharNoDraw(worldFrame, input.charAt(0), false);
            input = input.substring(1);
        }
        allKeyStrokes = loadedString;
       // ter.renderFrame(worldFrame);
       return worldFrame;
    }

    public TETile[][] generateWorld(long seed) {
        int numOfRooms = ((int) seed + 10) % 15 + 10;
        int numOfHallways = ((int) seed + 15) % 10;
        worldFrame = new TETile[WIDTH + 1][HEIGHT + 1];


        Random placePick = new Random();
        placePick.setSeed(seed);

        //generate first room
        int firstWidth = Math.abs(placePick.nextInt()) % 10 + 3;
        int firstHeight = Math.abs(placePick.nextInt()) % 10 + 3;
        int firstXPos = 100;
        int firstYPos = 100;

        while (firstXPos + firstWidth > WIDTH) {
            firstXPos = Math.abs(placePick.nextInt()) % WIDTH;
        }
        while (firstYPos + firstHeight > HEIGHT) {
            firstYPos = Math.abs(placePick.nextInt()) % HEIGHT;
        }
        for (int i = firstXPos; i < firstXPos + firstWidth; i++) {
            for (int j = firstYPos; j < firstYPos + firstHeight; j++) {
                worldFrame[i][j] = Tileset.FLOOR;
            }
        }

        int numRoomsGenerated = 1;
        while (numRoomsGenerated < numOfRooms) {
            boolean validChoice = false;

            int[] getNewSpot = generateHallway(worldFrame, placePick, numOfHallways);
            int width = Math.abs(placePick.nextInt()) % 6 + 2;
            int height = Math.abs(placePick.nextInt()) % 6 + 2;
            int xPos = getNewSpot[0]; /*Math.abs(placePick.nextInt()) % WIDTH;*/
            int yPos = getNewSpot[1]; /*Math.abs(placePick.nextInt()) % HEIGHT;*/

            while (worldFrame[xPos][yPos] != Tileset.FLOOR) {
                xPos = Math.abs(placePick.nextInt()) % WIDTH;
                yPos = Math.abs(placePick.nextInt()) % HEIGHT;
            }

            if (xPos + width > WIDTH) {
                width = WIDTH - xPos;
            }

            if (yPos + height > HEIGHT) {
                height = HEIGHT - yPos;
            }

            validChoice = true;

            for (int i = xPos; i < xPos + width; i++) {
                for (int j = yPos; j < yPos + height; j++) {
                    worldFrame[i][j] = Tileset.FLOOR;
                }
            }
            numRoomsGenerated++;
        }


        addWalls(worldFrame);
        worldGenerated = true;
        addSuitableDoor(worldFrame);
        System.out.print('e');
        return worldFrame;
    }


    private void addWalls(TETile[][] world) {
        for (int i = 0; i < WIDTH + 1; i++) {
            for (int j = 0; j < HEIGHT + 1; j++) {
                if (world[i][j] != Tileset.FLOOR) {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }

        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                if (adjIsFloor(world, i, j)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }

        for (int i = 0; i < WIDTH; i++) {
            if (world[i][HEIGHT - 1] == Tileset.FLOOR
                    || world[i][HEIGHT - 2] == Tileset.FLOOR) {
                world[i][HEIGHT - 1] = Tileset.WALL;
            }

            if (world[i][0] == Tileset.FLOOR
                    || world[i][1] == Tileset.FLOOR) {
                world[i][0] = Tileset.WALL;
            }

        }

        for (int i = 0; i < HEIGHT; i++) {
            if (world[0][i] == Tileset.FLOOR
                    || world[1][i] == Tileset.FLOOR) {
                world[0][i] = Tileset.WALL;
            }

            if (world[WIDTH - 1][i] == Tileset.FLOOR
                    || world[WIDTH - 2][i] == Tileset.FLOOR) {
                world[WIDTH - 1][i] = Tileset.WALL;
            }
        }
    }

    private boolean adjIsFloor(TETile[][] world, int xPos, int yPos) {
        if (world[xPos][yPos] == Tileset.NOTHING
                && (world[xPos + 1][yPos] == Tileset.FLOOR
                || world[xPos - 1][yPos] == Tileset.FLOOR
                || world[xPos][yPos + 1] == Tileset.FLOOR
                || world[xPos][yPos - 1] == Tileset.FLOOR
                || world[xPos - 1][yPos + 1] == Tileset.FLOOR
                || world[xPos + 1][yPos + 1] == Tileset.FLOOR
                || world[xPos - 1][yPos - 1] == Tileset.FLOOR
                || world[xPos + 1][yPos - 1] == Tileset.FLOOR)) {
            return true;
        }

        return false;
    }

    private int[] generateHallway(TETile[][] worldFrame, Random placePick, int numOfHallways) {
        int[] endCoords = new int[2];
        int numHallwaysGenerated = 0;
        while (numHallwaysGenerated < numOfHallways % 3 + 2) {
            int hallwayLength = Math.abs(placePick.nextInt()) % 25 + 5;
            int xPos = Math.abs(placePick.nextInt()) % WIDTH;
            int yPos = Math.abs(placePick.nextInt()) % HEIGHT;

            while (worldFrame[xPos][yPos] != Tileset.FLOOR) {
                xPos = Math.abs(placePick.nextInt()) % WIDTH;
                yPos = Math.abs(placePick.nextInt()) % HEIGHT;
            }

            int direction = Math.abs(placePick.nextInt()) % 4;
            endCoords = hallwayHelper(xPos, yPos, worldFrame, direction, hallwayLength);
            numHallwaysGenerated++;
        }
        return endCoords;
    }

    private int[] hallwayHelper(int x, int y, TETile[][] world, int direction, int length) {
        int[] endCoords = new int[2];
        switch (direction) {
            case 0:
                while (y < HEIGHT && length > 0) {
                    world[x][y] = Tileset.FLOOR;
                    endCoords[0] = x;
                    endCoords[1] = y;
                    y++;
                    length--;
                }
                break;
            case 1:
                while (x < HEIGHT && length > 0) {
                    world[x][y] = Tileset.FLOOR;
                    endCoords[0] = x;
                    endCoords[1] = y;
                    x++;
                    length--;
                }
                break;
            case 2:
                while (y >= 0 && length > 0) {
                    world[x][y] = Tileset.FLOOR;
                    endCoords[0] = x;
                    endCoords[1] = y;
                    y--;
                    length--;
                }
                break;
            case 3:
                while (x >= 0 && length > 0) {
                    world[x][y] = Tileset.FLOOR;
                    endCoords[0] = x;
                    endCoords[1] = y;
                    x--;
                    length--;
                }
                break;
            default:
                break;
        }
        return endCoords;
    }
}
