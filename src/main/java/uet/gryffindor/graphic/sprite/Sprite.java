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
    public static Sprite[] bomb;
    public static Sprite[] explosion;

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
        player_stand = new Sprite(113, 4, 18, 28, SpriteSheet.player);

        player_right = new Sprite[6];
        player_right[0] = new Sprite(114, 35, 14, 27, SpriteSheet.player);
        player_right[1] = new Sprite(88, 35, 14, 27, SpriteSheet.player);
        player_right[2] = new Sprite(68, 35, 14, 27, SpriteSheet.player);
        player_right[3] = new Sprite(47, 35, 14, 27, SpriteSheet.player);
        player_right[4] = new Sprite(27, 35, 14, 27, SpriteSheet.player);
        player_right[5] = new Sprite(6, 35, 14, 27, SpriteSheet.player);

        player_left = new Sprite[6];
        player_left[0] = new Sprite(135, 98, 15, 26, SpriteSheet.player);
        player_left[1] = new Sprite(87, 98, 15, 26, SpriteSheet.player);
        player_left[2] = new Sprite(65, 98, 15, 26, SpriteSheet.player);
        player_left[3] = new Sprite(46, 98, 15, 26, SpriteSheet.player);
        player_left[4] = new Sprite(26, 98, 15, 26, SpriteSheet.player);
        player_left[5] = new Sprite(6, 98, 15, 26, SpriteSheet.player);

        player_up = new Sprite[6];
        player_up[0] = new Sprite(113, 69, 18, 25, SpriteSheet.player);
        player_up[1] = new Sprite(88, 69, 18, 25, SpriteSheet.player);
        player_up[2] = new Sprite(66, 69, 18, 25, SpriteSheet.player);
        player_up[3] = new Sprite(46, 69, 18, 25, SpriteSheet.player);
        player_up[4] = new Sprite(26, 69, 18, 25, SpriteSheet.player);
        player_up[5] = new Sprite(5, 69, 18, 25, SpriteSheet.player);

        player_down = new Sprite[6];
        player_down[0] = new Sprite(113, 4, 18, 28, SpriteSheet.player);
        player_down[1] = new Sprite(88, 4, 18, 28, SpriteSheet.player);
        player_down[2] = new Sprite(67, 4, 18, 28, SpriteSheet.player);
        player_down[3] = new Sprite(46, 4, 18, 28, SpriteSheet.player);
        player_down[4] = new Sprite(25, 4, 18, 28, SpriteSheet.player);
        player_down[5] = new Sprite(4, 4, 18, 28, SpriteSheet.player);

        bomb = new Sprite[3];
        bomb[0] = new Sprite(212, 10, 18, 22, SpriteSheet.bomb);
        bomb[1] = new Sprite(196, 10, 18, 22, SpriteSheet.bomb);
        bomb[2] = new Sprite(177, 10, 18, 22, SpriteSheet.bomb);

        explosion = new Sprite[12];
        explosion[0] = new Sprite(0, 0, 320, 350, SpriteSheet.explosion);
        explosion[1] = new Sprite(330, 0, 320, 350, SpriteSheet.explosion);
        explosion[2] = new Sprite(660, 0, 320, 350, SpriteSheet.explosion);
        explosion[3] = new Sprite(990, 0, 320, 350, SpriteSheet.explosion);
        explosion[4] = new Sprite(1320, 0, 320, 350, SpriteSheet.explosion);
        explosion[5] = new Sprite(1650, 0, 320, 350, SpriteSheet.explosion);
        explosion[6] = new Sprite(0, 350, 320, 350, SpriteSheet.explosion);
        explosion[7] = new Sprite(330, 350, 320, 350, SpriteSheet.explosion);
        explosion[8] = new Sprite(660, 350, 320, 350, SpriteSheet.explosion);
        explosion[9] = new Sprite(990, 350, 320, 350, SpriteSheet.explosion);
        explosion[10] = new Sprite(1290, 350, 320, 350, SpriteSheet.explosion);
        explosion[11] = new Sprite(1620, 350, 320, 350, SpriteSheet.explosion);

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
