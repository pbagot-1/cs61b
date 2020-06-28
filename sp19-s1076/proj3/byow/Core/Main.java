package byow.Core;

import byow.TileEngine.TERenderer;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        TERenderer myRender = new TERenderer();
        myRender.initialize(100, 50);

        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length != 1) {
            Engine engine = new Engine();
            myRender.renderFrame(engine.interactWithInputString(args[0]));
            System.out.println(engine.toString());
        } else {
            Engine engine = new Engine();
            engine.interactWithKeyboard();
        }


    }
}
