package uet.gryffindor.autopilot;

import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.graphic.sprite.Sprite;

public class GameState {
  public static final int N_FEATURES = 24;
  // enum Type {
  //   WALL_LEFT, WALL_RIGHT, WALL_DOWN, WALL_UP,
  //   BRICK_LEFT, BRICK_RIGHT, BRICK_DOWN, BRICK_UP,
  //   BOMB_LEFT, BOMB_RIGHT, BOMB_DOWN, BOMB_UP,
  //   PORTAL_LEFT, PORTAL_RIGHT, PORTAL_DOWN, PORTAL_UP,
  //   ITEM_LEFT, ITEM_RIGHT, ITEM_DOWN, ITEM_UP,
  //   ENEMY_LEFT, ENEMY_RIGHT, ENEMY_DOWN, ENEMY_UP;
  // }

  private static final int left = 0;
  private static final int right = 1;
  private static final int down = 2;
  private static final int up = 3;

  public INDArray data;
  String ele = "";

  private GameState(int[] array) {
    this.data = Nd4j.create(array, new long[]{1, array.length}, DataType.INT16);
    
    for (int i : array) {
      ele += i;
    }
  }

  public static GameState getStateAsArray(Vector2D agentPos, GameEnvironment env) {
    int[] state = new int[N_FEATURES];
    int i = 0;
    // wall
    for (int k : getRelativeWithWall(agentPos, env)) {
      state[i++] = k;
    }

    // brick
    for (int k : new int[4]) {
      state[i++] = k;
    }

    // bomb
    for (int k : new int[4]) {
      state[i++] = k;
    }

    // portal
    for (int k : new int[4]) {
      state[i++] = k;
    }

    // item
    for (int k : getRelativeNearestObject(agentPos, env, Item.class)) {
      state[i++] = k;
    }
    
    // enemy
    for (int k : new int[4]) {
      state[i++] = k;
    }

    return new GameState(state);
  }

  private static <T extends GameObject> int[] getRelativeNearestObject(Vector2D agentPos, GameEnvironment env, Class<T> clazz) {
    T nearestObj = null;
    double minDis = Double.MAX_VALUE;
    for (T enemy : env.getObject(clazz)) {
      double dis = Vector2D.distanceOfPoint(agentPos, enemy.position);

      if (minDis > dis) {
        minDis = dis;
        nearestObj = enemy;
      }
    }
    
    return getRelativePosition(agentPos, nearestObj.position);
  }

  private static int[] getRelativeWithWall(Vector2D agentPos, GameEnvironment env) {
    int[] pos = new int[4];
    
    Vector2D wallPos = agentPos.clone();

    // up
    wallPos.setValue(agentPos.x, agentPos.y - Sprite.DEFAULT_SIZE);
    pos[up] = env.getWallMap().get(wallPos.toString()) == null ? 0 : 1;

    // down
    wallPos.setValue(agentPos.x, agentPos.y + Sprite.DEFAULT_SIZE);
    pos[down] = env.getWallMap().get(wallPos.toString()) == null ? 0 : 1;

    // left
    wallPos.setValue(agentPos.x - Sprite.DEFAULT_SIZE, agentPos.y);
    pos[left] = env.getWallMap().get(wallPos.toString()) == null ? 0 : 1;

    // right
    wallPos.setValue(agentPos.x + Sprite.DEFAULT_SIZE, agentPos.y);
    pos[right] = env.getWallMap().get(wallPos.toString()) == null ? 0 : 1;

    return pos;
  }

  private static int[] getRelativePosition(Vector2D posSrc, Vector2D posDst) {
    int[] pos = new int[4];

    if (posDst.x < posSrc.x) {
      pos[left] = 1;
    } else if (posDst.x > posSrc.x) {
      pos[right] = 1;
    }

    if (posDst.y < posSrc.y) {
      pos[up] = 1;
    } else if (posDst.y > posSrc.y) {
      pos[down] = 1;
    }

    return pos;
  }

  @Override
  public String toString() {
    return ele;
  }
}