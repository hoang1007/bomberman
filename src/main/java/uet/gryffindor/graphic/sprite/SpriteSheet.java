package uet.gryffindor.graphic.sprite;

import javafx.scene.image.Image;
import uet.gryffindor.GameApplication;

/**
 * Class này lưu trữ ảnh gốc, load các thứ cần trong Sprite.java
 */
public class SpriteSheet {
    // dynamics
    public static SpriteSheet player = new SpriteSheet("dynamics/player/playerAndBomb.png");
    public static SpriteSheet bomb = new SpriteSheet("dynamics/player/playerAndBomb.png");
    public static SpriteSheet blackBomber = new SpriteSheet("dynamics/player/player2.png");
    public static SpriteSheet explosion = new SpriteSheet("dynamics/explosion/explosion01.png");
    public static SpriteSheet enemy = new SpriteSheet("dynamics/enemy/enemy.png");
    public static SpriteSheet magma = new SpriteSheet("dynamics/enemy/magma.png");

    public static SpriteSheet explosionPotion = new SpriteSheet("dynamics/items/explosion-potion.png");
    public static SpriteSheet heart = new SpriteSheet("dynamics/items/heart.png");
    public static SpriteSheet speedPotion = new SpriteSheet("dynamics/items/speed-potion.png");
    public static SpriteSheet flamePotion = new SpriteSheet("dynamics/items/flame-potion.png");
    public static SpriteSheet iceCastle = new SpriteSheet("statics/ice.png");

    // statics
    public static SpriteSheet obstacle = new SpriteSheet("statics/block.png");
    public static SpriteSheet wall2D = new SpriteSheet("statics/wall.png");
    public static SpriteSheet tiles = new SpriteSheet("statics/tiles.jpg");
    public static SpriteSheet portal = new SpriteSheet("statics/portal.png");

    private Image img;

    public SpriteSheet(String sheetName) {
        System.out.println(sheetName);
        img = new Image(GameApplication.class.getResourceAsStream("img/" + sheetName));
    }

    public Image getImage() {
        return img;
    }

    public double getWidth() {
        return img.getWidth();
    }

    public double getHeight() {
        return img.getHeight();
    }

}
