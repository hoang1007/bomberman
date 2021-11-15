package uet.gryffindor.autopilot;

import java.util.List;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.Bomber;
import uet.gryffindor.game.object.Brick;
import uet.gryffindor.game.object.Wall;
import uet.gryffindor.game.object.enemy.Enemy;
import uet.gryffindor.game.object.items.Item;
import uet.gryffindor.util.OtherUtils;

public class GameState {
  public static final int N_FEATURES = 24;
  enum Type {
    WALL_LEFT, WALL_RIGHT, WALL_DOWN, WALL_UP,
    BRICK_LEFT, BRICK_RIGHT, BRICK_DOWN, BRICK_UP,
    BOMB_LEFT, BOMB_RIGHT, BOMB_DOWN, BOMB_UP,
    PORTAL_LEFT, PORTAL_RIGHT, PORTAL_DOWN, PORTAL_UP,
    ITEM_LEFT, ITEM_RIGHT, ITEM_DOWN, ITEM_UP,
    ENEMY_LEFT, ENEMY_RIGHT, ENEMY_DOWN, ENEMY_UP;
  }

  public static int[] getState(Bomber agent, GameEnvironment env) {
    int[] state = new int[N_FEATURES];

    List<GameObject> objects = env.getGame().getPlayingMap().getObjects();

    List<Enemy> enemies = OtherUtils.filter(objects, Enemy.class);
    List<Wall> wallList = OtherUtils.filter(objects, Wall.class);
    List<Brick> brickList = OtherUtils.filter(objects, Brick.class);
    List<Item> itemList = OtherUtils.filter(objects, Item.class);

    Enemy nearestEnemy = null;
    double maxDis = Double.MIN_VALUE;
    for (Enemy enemy : enemies) {
      double dis = Vector2D.distanceOfPoint(agent.position, enemy.position);

      if (maxDis < dis) {
        maxDis = dis;
        nearestEnemy = enemy;
      }
    }
  }
}