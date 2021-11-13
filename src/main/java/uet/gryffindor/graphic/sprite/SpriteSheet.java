package uet.gryffindor.graphic.sprite;

import javafx.scene.image.Image;
import uet.gryffindor.GameApplication;

/**
 * Class này lưu trữ ảnh gốc, load các thứ cần trong Sprite.java
 */
public class SpriteSheet {

    public static SpriteSheet player = new SpriteSheet("playerAndBomb");
    public static SpriteSheet bombExplosion = new SpriteSheet("playerAndBomb");

    private Image img;

    public SpriteSheet(String sheetName) {
        img = new Image(GameApplication.class.getResourceAsStream("img/" + sheetName + ".png"));
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
