package byow.Core;
import byow.TileEngine.Tileset;
public class EnemyClass extends CharacterClass{


    public EnemyClass(int xPos, int yPos, String choice) {
        this.xPos = xPos;
        this.yPos = yPos;
        tile = Tileset.AVATAR;
    }
}
