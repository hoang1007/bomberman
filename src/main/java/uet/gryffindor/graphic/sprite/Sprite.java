package uet.gryffindor.graphic.sprite;

import java.util.HashMap;

/** Class này lưu trữ thông tin pixel của 1 sprite */
public class Sprite {
  // dynamics
  public static HashMap<String, Sprite[]> player = new HashMap<>();
  public static HashMap<String, Sprite[]> blackPlayer = new HashMap<>();
  public static HashMap<String, Sprite[]> balloom = new HashMap<>();
  public static HashMap<String, Sprite[]> oneal = new HashMap<>();
  public static HashMap<String, Sprite[]> circleEnemy = new HashMap<>();
  public static HashMap<String, Sprite[]> magma = new HashMap<>();

  public static Sprite[] bomb;
  public static Sprite[] explosion;
  public static Sprite[] explosionPotion;
  public static Sprite[] heart;
  public static Sprite[] speedPotion;
  public static Sprite[] flamePotion;

  // public static Sprite[] balloom;
  // public static Sprite[] oneal;

  // statics
  public static Sprite[] obstacle;
  public static Sprite[] portal;
  public static Sprite[] iceMap;
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

  public static void loadSprite() {
    loadPlayer();
    loadBlackPlayer();
    loadBomb();
    loadEnemy();
    loadExplosion();
    loadItems();
    loadObstacle();
    loadPortal();
    loadIceMap();
  }

  private static void loadIceMap() {
    iceMap = new Sprite[30];
    int id = 0;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 6; j++) {
        iceMap[id++] = new Sprite(j * 100, i * 100, 100, 100, SpriteSheet.iceCastle);

        if (id == iceMap.length) {
          break;
        }
      }
    }
  }

  private static void loadBlackPlayer() {
    Sprite[] mesh = new Sprite[12];
    final int wUnit = 17;
    final int hUnit = 24;

    for (int i = 0; i < mesh.length; i++) {
      mesh[i] = new Sprite(i * wUnit, 0, wUnit, hUnit, SpriteSheet.blackBomber);
    }

    blackPlayer.put("up", new Sprite[] { mesh[2], mesh[1], mesh[0] });
    blackPlayer.put("down", new Sprite[] { mesh[8], mesh[7], mesh[6] });
    blackPlayer.put("left", new Sprite[] { mesh[5], mesh[4], mesh[3] });
    blackPlayer.put("right", new Sprite[] { mesh[11], mesh[10], mesh[9] });
    blackPlayer.put("dead", new Sprite[] {
        new Sprite(289, 98, wUnit, hUnit, SpriteSheet.blackBomber),
        new Sprite(272, 99, wUnit, hUnit, SpriteSheet.blackBomber),
        new Sprite(255, 100, wUnit, hUnit, SpriteSheet.blackBomber),
        new Sprite(238, 100, wUnit, hUnit, SpriteSheet.blackBomber),
        new Sprite(216, 100, 19, 24, SpriteSheet.blackBomber),
        new Sprite(190, 102, 22, 22, SpriteSheet.blackBomber),
        new Sprite(164, 103, 24, 21, SpriteSheet.blackBomber),
        new Sprite(138, 103, 25, 21, SpriteSheet.blackBomber),
        new Sprite(114, 102, 24, 22, SpriteSheet.blackBomber)
    });
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

    Sprite[] player_dead = new Sprite[1];
    player_dead[0] = new Sprite(2, 135, 18, 28, SpriteSheet.player);

    player.put("up", player_up);
    player.put("down", player_down);
    player.put("left", player_left);
    player.put("right", player_right);
    player.put("dead", player_dead);
  }

  private static void loadBomb() {
    bomb = new Sprite[3];
    bomb[0] = new Sprite(212, 10, 18, 22, SpriteSheet.bomb);
    bomb[1] = new Sprite(196, 10, 18, 22, SpriteSheet.bomb);
    bomb[2] = new Sprite(177, 10, 18, 22, SpriteSheet.bomb);
  }

  private static void loadEnemy() {
    // Monster
    Sprite[] balloomSprites = new Sprite[7];
    balloomSprites[0] = new Sprite(16, 5, 16, 16, SpriteSheet.enemy);
    balloomSprites[1] = new Sprite(32, 5, 16, 16, SpriteSheet.enemy);
    balloomSprites[2] = new Sprite(49, 5, 16, 16, SpriteSheet.enemy);
    balloomSprites[3] = new Sprite(64, 5, 16, 16, SpriteSheet.enemy);
    balloomSprites[4] = new Sprite(80, 5, 16, 16, SpriteSheet.enemy);
    balloomSprites[5] = new Sprite(96, 5, 16, 16, SpriteSheet.enemy);
    balloomSprites[6] = new Sprite(112, 5, 16, 16, SpriteSheet.enemy);

    balloom.put("up", new Sprite[] { balloomSprites[2], balloomSprites[4] });
    balloom.put("down", new Sprite[] { balloomSprites[2], balloomSprites[4] });
    balloom.put("left", new Sprite[] { balloomSprites[0], balloomSprites[1], balloomSprites[2] });
    balloom.put("right", new Sprite[] { balloomSprites[4], balloomSprites[5], balloomSprites[6] });

    Sprite[] onealSprites = new Sprite[8];
    onealSprites[0] = new Sprite(5, 88, 16, 16, SpriteSheet.enemy);
    onealSprites[1] = new Sprite(24, 88, 16, 16, SpriteSheet.enemy);
    onealSprites[2] = new Sprite(41, 88, 16, 16, SpriteSheet.enemy);
    onealSprites[3] = new Sprite(59, 88, 16, 16, SpriteSheet.enemy);
    onealSprites[4] = new Sprite(77, 88, 16, 16, SpriteSheet.enemy);
    onealSprites[5] = new Sprite(95, 88, 16, 16, SpriteSheet.enemy);
    onealSprites[6] = new Sprite(112, 88, 16, 16, SpriteSheet.enemy);
    onealSprites[7] = new Sprite(131, 88, 16, 16, SpriteSheet.enemy);

    oneal.put("up", new Sprite[] { onealSprites[2], onealSprites[5] });
    oneal.put("down", new Sprite[] { onealSprites[2], onealSprites[5] });
    oneal.put("left", new Sprite[] { onealSprites[0], onealSprites[1], onealSprites[2] });
    oneal.put("right", new Sprite[] { onealSprites[5], onealSprites[6], onealSprites[7] });

    Sprite[] circleEnemySprite = new Sprite[7];
    circleEnemySprite[0] = new Sprite(16, 47, 16, 16, SpriteSheet.enemy);
    circleEnemySprite[1] = new Sprite(34, 47, 16, 16, SpriteSheet.enemy);
    circleEnemySprite[2] = new Sprite(50, 47, 16, 16, SpriteSheet.enemy);
    circleEnemySprite[3] = new Sprite(66, 47, 16, 16, SpriteSheet.enemy);
    circleEnemySprite[4] = new Sprite(82, 47, 16, 16, SpriteSheet.enemy);
    circleEnemySprite[5] = new Sprite(98, 47, 16, 16, SpriteSheet.enemy);
    circleEnemySprite[6] = new Sprite(115, 47, 16, 16, SpriteSheet.enemy);

    circleEnemy.put("up", circleEnemySprite);
    circleEnemy.put("down", circleEnemySprite);
    circleEnemy.put("left", circleEnemySprite);
    circleEnemy.put("right", circleEnemySprite);

    Sprite[] magmaSprite = new Sprite[8];
    for (int i = 0; i <= 1; i++) {
      for (int j = 0; j <= 3; j++) {
        magmaSprite[i * 4 + j] = new Sprite(j * 144, i * 144, 115, 120, SpriteSheet.magma);
      }
    }
    magma.put("up", magmaSprite);

  }

  public static void loadExplosion() {
    explosion = new Sprite[12];
    for (int i = 0; i <= 5; i++) {
      explosion[i] = new Sprite(i * 330, 0, 320, 350, SpriteSheet.explosion);
      explosion[i + 6] = new Sprite(i * 330, 350, 320, 350, SpriteSheet.explosion);
    }
  }

  public static void loadItems() {
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

    flamePotion = new Sprite[12];
    for (int i = 0; i <= 11; i++) {
      flamePotion[i] = new Sprite(i * 530, 0, 530, 600, SpriteSheet.flamePotion);
    }
  }

  public static void loadObstacle() {
    obstacle = new Sprite[4];
    obstacle[0] = new Sprite(17, 28, 112, 146, SpriteSheet.obstacle);
    obstacle[1] = new Sprite(145, 28, 112, 146, SpriteSheet.obstacle);
    obstacle[2] = new Sprite(271, 28, 112, 146, SpriteSheet.obstacle);
    obstacle[3] = new Sprite(513, 20, 188, 163, SpriteSheet.obstacle);

    wall2D = new Sprite(
        0,
        0,
        SpriteSheet.wall2D.getWidth(),
        SpriteSheet.wall2D.getHeight(),
        SpriteSheet.wall2D);

    tiles = new Sprite[28];
    int x = 0;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 7; j++) {
        tiles[(x++)] = new Sprite(157 * j, 157 * i, 140, 135, SpriteSheet.tiles);
      }
    }

    tilesFloor = new Sprite(333, 196, 140, 141, SpriteSheet.tiles);
  }

  public static void loadPortal() {
    portal = new Sprite[6];
    // portal[0] = new Sprite(0, 0, 150, 320, SpriteSheet.portal);
    // portal[1] = new Sprite(137, 0, 150, 320, SpriteSheet.portal);
    portal[0] = new Sprite(300, 0, 150, 320, SpriteSheet.portal);
    portal[1] = new Sprite(457, 0, 150, 320, SpriteSheet.portal);
    portal[2] = new Sprite(640, 0, 150, 320, SpriteSheet.portal);
    // portal[3] = new Sprite(20, 330, 150, 320, SpriteSheet.portal);
    // portal[6] = new Sprite(190, 330, 150, 320, SpriteSheet.portal);
    // portal[7] = new Sprite(380, 330, 150, 320, SpriteSheet.portal);

    int n = 1;
    for (int i = 3; i <= 5; i++) {
      portal[i] = portal[i - n];
      n += 2;
    }
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
