package uet.gryffindor.graphic.sprite;
import java.util.HashMap;
/** Class này lưu trữ thông tin pixel của 1 sprite */
public class Sprite {
  // dynamics
  public static HashMap<String, Sprite[]> player = new HashMap<>();
  public static HashMap<String, Sprite[]> enemy = new HashMap<>();

  public static Sprite[] bomb;
  public static Sprite[] explosion;
  public static Sprite[] explosionPotion;
  public static Sprite[] heart;
  public static Sprite[] speedPotion;

  public static Sprite[] balloom;
  public static Sprite[] oneal;

  // statics
  public static Sprite[] obstacle;
  // public static Sprite rock;
  public static Sprite wall2D;
  public static Sprite[] tiles;
  public static Sprite tilesFloor;
  public static int DEFAULT_SIZE = 35; // width = height của 1 sprite
  private double x; // tọa độ x so với ảnh cha
  private double y; // tọa độ y so với ảnh cha
  private double sWidth; // chiều rộng ảnh con
  private double sHeight; // chiều cao ảnh con
  private SpriteSheet spriteSheet; // lớp chứa ảnh cha

  public Sprite(double _x, double _y, double sWidth, double sHeight, SpriteSheet _spriteSheet) {
    this.x = _x;
    this.y = _y;
    this.sWidth = sWidth;
    this.sHeight = sHeight;
    this.spriteSheet = _spriteSheet;
  }

  private static void loadPlayer() {
    Sprite[] player_right = new Sprite[6];
    player_right[0] = new Sprite(114, 35, 14, 27, SpriteSheet.player);
    player_right[1] = new Sprite(88, 35, 14, 27, SpriteSheet.player);
    player_right[2] = new Sprite(68, 35, 14, 27, SpriteSheet.player);
    player_right[3] = new Sprite(47, 35, 14, 27, SpriteSheet.player);
    player_right[4] = new Sprite(27, 35, 14, 27, SpriteSheet.player);
    player_right[5] = new Sprite(6, 35, 14, 27, SpriteSheet.player);
    Sprite[] player_left = new Sprite[6];
    player_left[0] = new Sprite(135, 98, 15, 26, SpriteSheet.player);
    player_left[1] = new Sprite(87, 98, 15, 26, SpriteSheet.player);
    player_left[2] = new Sprite(65, 98, 15, 26, SpriteSheet.player);
    player_left[3] = new Sprite(46, 98, 15, 26, SpriteSheet.player);
    player_left[4] = new Sprite(26, 98, 15, 26, SpriteSheet.player);
    player_left[5] = new Sprite(6, 98, 15, 26, SpriteSheet.player);
    Sprite[] player_up = new Sprite[6];
    player_up[0] = new Sprite(113, 69, 18, 25, SpriteSheet.player);
    player_up[1] = new Sprite(88, 69, 18, 25, SpriteSheet.player);
    player_up[2] = new Sprite(66, 69, 18, 25, SpriteSheet.player);
    player_up[3] = new Sprite(46, 69, 18, 25, SpriteSheet.player);
    player_up[4] = new Sprite(26, 69, 18, 25, SpriteSheet.player);
    player_up[5] = new Sprite(5, 69, 18, 25, SpriteSheet.player);
    Sprite[] player_down = new Sprite[6];
    player_down[0] = new Sprite(113, 4, 18, 28, SpriteSheet.player);
    player_down[1] = new Sprite(88, 4, 18, 28, SpriteSheet.player);
    player_down[2] = new Sprite(67, 4, 18, 28, SpriteSheet.player);
    player_down[3] = new Sprite(46, 4, 18, 28, SpriteSheet.player);
    player_down[4] = new Sprite(25, 4, 18, 28, SpriteSheet.player);
    player_down[5] = new Sprite(4, 4, 18, 28, SpriteSheet.player);
    player.put("up", player_up);
    player.put("down", player_down);
    player.put("left", player_left);
    player.put("right", player_right);
  }

  private static void loadBomb() {
    bomb = new Sprite[3];
    bomb[0] = new Sprite(212, 10, 18, 22, SpriteSheet.bomb);
    bomb[1] = new Sprite(196, 10, 18, 22, SpriteSheet.bomb);
    bomb[2] = new Sprite(177, 10, 18, 22, SpriteSheet.bomb);
  }

  public static void loadSprite() {
    // dynamics
    loadPlayer();
    loadBomb();
    explosion = new Sprite[12];
    for (int i = 0; i <= 5; i++) {
      explosion[i] = new Sprite(i * 330, 0, 320, 350, SpriteSheet.explosion);
      explosion[i + 6] = new Sprite(i * 330, 350, 320, 350, SpriteSheet.explosion);
    }
    explosionPotion = new Sprite[8];
    for (int i = 0; i <= 7; i++) {
      explosionPotion[i] = new Sprite(i * 132, 0, 132, 174, SpriteSheet.explosionPotion);
    }
    heart = new Sprite[22];
    for (int i = 0; i <= 21; i++) {
      heart[i] = new Sprite(i * 80, 0, 80, 83, SpriteSheet.heart);
    }
    speedPotion = new Sprite[8];
    for (int i = 0; i <= 7; i++) {
      speedPotion[i] = new Sprite(i * 135, 0, 135, 174, SpriteSheet.speedPotion);
    }

    // Monster
    balloom = new Sprite[7];
    balloom[0] = new Sprite(16, 5, 16, 16, SpriteSheet.balloomAndOneal);
    balloom[1] = new Sprite(32, 5, 16, 16, SpriteSheet.balloomAndOneal);
    balloom[2] = new Sprite(49, 5, 16, 16, SpriteSheet.balloomAndOneal);
    balloom[3] = new Sprite(64, 5, 16, 16, SpriteSheet.balloomAndOneal);
    balloom[4] = new Sprite(80, 5, 16, 16, SpriteSheet.balloomAndOneal);
    balloom[5] = new Sprite(96, 5, 16, 16, SpriteSheet.balloomAndOneal);
    balloom[6] = new Sprite(112, 5, 16, 16, SpriteSheet.balloomAndOneal);

    oneal = new Sprite[8];
    oneal[0] = new Sprite(5, 88, 16, 16, SpriteSheet.balloomAndOneal);
    oneal[1] = new Sprite(24, 88, 16, 16, SpriteSheet.balloomAndOneal);
    oneal[2] = new Sprite(41, 88, 16, 16, SpriteSheet.balloomAndOneal);
    oneal[3] = new Sprite(59, 88, 16, 16, SpriteSheet.balloomAndOneal);
    oneal[4] = new Sprite(77, 88, 16, 16, SpriteSheet.balloomAndOneal);
    oneal[5] = new Sprite(95, 88, 16, 16, SpriteSheet.balloomAndOneal);
    oneal[6] = new Sprite(112, 88, 16, 16, SpriteSheet.balloomAndOneal);
    oneal[7] = new Sprite(131, 88, 16, 16, SpriteSheet.balloomAndOneal);

    enemy.put("balloom", balloom);
    enemy.put("oneal", oneal);

    // statics
    obstacle = new Sprite[4];
    obstacle[0] = new Sprite(17, 28, 112, 146, SpriteSheet.obstacle);
  }
}