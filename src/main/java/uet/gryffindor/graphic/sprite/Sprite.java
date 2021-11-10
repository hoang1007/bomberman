package uet.gryffindor.graphic.sprite;

/**
 * Class này lưu trữ thông tin pixel của 1 sprite
 */
public class Sprite {

    public static Sprite player_stand;
    public static Sprite[] player_right;
    public static Sprite[] player_left;
    public static Sprite[] player_up;
    public static Sprite[] player_down;

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
        player_stand = new Sprite(229, 15, 36, 58, SpriteSheet.player);
        player_right = new Sprite[6];
        player_right[0] = new Sprite(15, 80, 28, 52, SpriteSheet.player);
        player_right[1] = new Sprite(59, 80, 29, 52, SpriteSheet.player);
        player_right[2] = new Sprite(99, 80, 28, 52, SpriteSheet.player);
        player_right[3] = new Sprite(141, 80, 28, 50, SpriteSheet.player);
        player_right[4] = new Sprite(181, 80, 28, 52, SpriteSheet.player);
        player_right[5] = new Sprite(232, 80, 28, 52, SpriteSheet.player);

        player_left = new Sprite[6];
        player_left[0] = new Sprite(233, 203, 28, 52, SpriteSheet.player);
        player_left[1] = new Sprite(179, 203, 29, 52, SpriteSheet.player);
        player_left[2] = new Sprite(135, 203, 28, 52, SpriteSheet.player);
        player_left[3] = new Sprite(96, 203, 28, 50, SpriteSheet.player);
        player_left[4] = new Sprite(54, 203, 28, 52, SpriteSheet.player);
        player_left[5] = new Sprite(13, 203, 28, 52, SpriteSheet.player);

        player_up = new Sprite[6];
        player_up[0] = new Sprite(231, 146, 38, 50, SpriteSheet.player);
        player_up[1] = new Sprite(181, 146, 38, 50, SpriteSheet.player);
        player_up[2] = new Sprite(137, 146, 38, 50, SpriteSheet.player);
        player_up[3] = new Sprite(94, 146, 38, 50, SpriteSheet.player);
        player_up[4] = new Sprite(54, 146, 38, 50, SpriteSheet.player);
        player_up[5] = new Sprite(12, 146, 38, 50, SpriteSheet.player);

        player_down = new Sprite[6];
        player_down[0] = new Sprite(230, 15, 34, 52, SpriteSheet.player);
        player_down[1] = new Sprite(178, 15, 34, 52, SpriteSheet.player);
        player_down[2] = new Sprite(136, 15, 34, 52, SpriteSheet.player);
        player_down[3] = new Sprite(94, 15, 34, 52, SpriteSheet.player);
        player_down[4] = new Sprite(52, 15, 34, 52, SpriteSheet.player);
        player_down[5] = new Sprite(12, 15, 34, 52, SpriteSheet.player);
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
