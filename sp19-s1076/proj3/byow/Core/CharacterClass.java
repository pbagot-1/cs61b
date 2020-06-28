package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class CharacterClass {
    protected TETile tile;
    protected int xPos;
    protected int yPos;

    public CharacterClass() {

    }

    public CharacterClass(int xPos, int yPos, String choice) {
        this.xPos = xPos;
        this.yPos = yPos;
        tile = Tileset.AVATAR;
    }

    public int getXPos() {return xPos;}
    public int getYPos() {return yPos;}
    public void setXPos(int xPos) {this.xPos = xPos;}
    public void setYPos(int yPos) {this.yPos = yPos;}
    public TETile getTile() {return tile;}
}
