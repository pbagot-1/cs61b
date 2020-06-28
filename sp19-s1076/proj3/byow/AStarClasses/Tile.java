package byow.AStarClasses;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;

public class Tile {
    private int xPos;
    private int yPos;
    private int order;
    TETile[][] world;

    public Tile(int xPos, int yPos, int order, TETile[][] world) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.order = order;
        this.world = world;
    }

    public ArrayList<Tile> neighborTiles(Tile[][] tileFrame) {
        ArrayList<Tile> tiles = new ArrayList();
        try {
            if (tileFrame[xPos][yPos + 1] != null) {
                tiles.add(tileFrame[xPos][yPos + 1]);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (tileFrame[xPos - 1][yPos + 1] != null) {
                tiles.add(tileFrame[xPos - 1][yPos + 1]);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (tileFrame[xPos - 1][yPos] != null) {
                tiles.add(tileFrame[xPos - 1][yPos]);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (tileFrame[xPos - 1][yPos- 1] != null) {
                tiles.add(tileFrame[xPos - 1][yPos - 1]);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (tileFrame[xPos][yPos - 1] != null) {
                tiles.add(tileFrame[xPos][yPos  -1]);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (tileFrame[xPos + 1][yPos - 1] != null) {
                tiles.add(tileFrame[xPos + 1][yPos - 1]);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (tileFrame[xPos + 1][yPos] != null) {
                tiles.add(tileFrame[xPos + 1][yPos]);
            }
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            if (tileFrame[xPos + 1][yPos + 1] != null) {
                tiles.add(tileFrame[xPos + 1][yPos + 1]);
            }
        } catch (IndexOutOfBoundsException e) {
        }

        return tiles;

    }

    public int getOrder() {
        return order;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
}


