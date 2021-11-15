package uet.gryffindor.graphic.sprite;

import java.util.HashMap;

/**
 * Class này lưu trữ thông tin pixel của 1 sprite
 */
public class Sprite {
    public static HashMap<String, Sprite[]> player = new HashMap<>();

    public static int DEFAULT_SIZE = 35; // width = height của 1 sprite
    private double x; // tọa độ x so với ảnh cha
    private double y; // tọa độ y so với ảnh cha
    private double sWidth; // chiều rộng ảnh con
    private double sHeight;// chiều cao ảnh con
    private SpriteSheet spriteSheet; // lớp chứa ảnh cha

    public Sprite(double _x, double _y, double sWidth, double sHeight, SpriteSheet _spriteSheet) {
        this.x = _x;
        this.y = _y;
        this.sWidth = sWidth;
        this.sHeight = sHeight;
        this.spriteSheet = _spriteSheet;
    }

    public static void loadSprite() {
        Sprite[] playerRight = new Sprite[6];
        playerRight[0] = new Sprite(15, 80, 28, 52, SpriteSheet.player);
        playerRight[1] = new Sprite(59, 80, 29, 52, SpriteSheet.player);
        playerRight[2] = new Sprite(99, 80, 28, 52, SpriteSheet.player);
        playerRight[3] = new Sprite(141, 80, 28, 50, SpriteSheet.player);
        playerRight[4] = new Sprite(181, 80, 28, 52, SpriteSheet.player);
        playerRight[5] = new Sprite(232, 80, 28, 52, SpriteSheet.player);

        Sprite[] playerLeft = new Sprite[6];
        playerLeft[0] = new Sprite(233, 203, 28, 52, SpriteSheet.player);
        playerLeft[1] = new Sprite(179, 203, 29, 52, SpriteSheet.player);
        playerLeft[2] = new Sprite(135, 203, 28, 52, SpriteSheet.player);
        playerLeft[3] = new Sprite(96, 203, 28, 50, SpriteSheet.player);
        playerLeft[4] = new Sprite(54, 203, 28, 52, SpriteSheet.player);
        playerLeft[5] = new Sprite(13, 203, 28, 52, SpriteSheet.player);

        Sprite[] playerUp = new Sprite[6];
        playerUp[0] = new Sprite(231, 146, 38, 50, SpriteSheet.player);
        playerUp[1] = new Sprite(181, 146, 38, 50, SpriteSheet.player);
        playerUp[2] = new Sprite(137, 146, 38, 50, SpriteSheet.player);
        playerUp[3] = new Sprite(94, 146, 38, 50, SpriteSheet.player);
        playerUp[4] = new Sprite(54, 146, 38, 50, SpriteSheet.player);
        playerUp[5] = new Sprite(12, 146, 38, 50, SpriteSheet.player);

        Sprite[] playerDown = new Sprite[6];
        playerDown[0] = new Sprite(230, 15, 34, 52, SpriteSheet.player);
        playerDown[1] = new Sprite(178, 15, 34, 52, SpriteSheet.player);
        playerDown[2] = new Sprite(136, 15, 34, 52, SpriteSheet.player);
        playerDown[3] = new Sprite(94, 15, 34, 52, SpriteSheet.player);
        playerDown[4] = new Sprite(52, 15, 34, 52, SpriteSheet.player);
        playerDown[5] = new Sprite(12, 15, 34, 52, SpriteSheet.player);


        player.put("right", playerRight);
        player.put("up", playerUp);
        player.put("down", playerDown);
        player.put("left", playerLeft);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return sWidth;
    }

    public double getHeight() {
        return sHeight;
    }

    public SpriteSheet getSpriteSheet() {
      return this.spriteSheet;
    }
}
