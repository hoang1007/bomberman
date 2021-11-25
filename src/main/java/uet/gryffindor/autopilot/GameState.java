package uet.gryffindor.autopilot;

import java.util.HashMap;

import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.OtherUtils;

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

  private String ele = "";
  private INDArray array;

  private GameState(int[] array) {
    if (array.length != N_FEATURES) {
      System.out.println("sadasd");
    }
    
    for (int i : array) {
      ele += i;
    }

    this.array = Nd4j.create(array, new long[] {1, array.length}, DataType.INT16);
  }

  public INDArray getNdArray() {
    return this.array;
  }

  /** Array of features. */
  public static GameState getState(Vector2D agentPos, GameEnvironment env) {
    int[] state = new int[N_FEATURES];
    int i = 0;
    // wall
    for (int k : getRelativeWithWall(agentPos, env)) {
      state[i++] = k;
    }

    // brick
    for (int k : getRelativeWithObstacles(agentPos, env)) {
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
    for (int k : getRelativeWithItems(agentPos, env)) {
      state[i++] = k;
    }
    
    // enemy
    for (int k : getRelativeWithEnemies(agentPos, env)) {
      state[i++] = k;
    }

    return new GameState(state);
  }

  private static int[] getRelativeWithObstacles(Vector2D agentPos, GameEnvironment env) {
    HashMap<Vector2D, Integer[]> neighbors = new HashMap<>();
    neighbors.put(agentPos.subtract(new Vector2D(0, Sprite.DEFAULT_SIZE)), null); // up
    neighbors.put(agentPos.add(new Vector2D(0, Sprite.DEFAULT_SIZE)), null);  // down
    neighbors.put(agentPos.subtract(new Vector2D(Sprite.DEFAULT_SIZE, 0)), null); // left
    neighbors.put(agentPos.add(new Vector2D(Sprite.DEFAULT_SIZE, 0)), null);  // right

    for (Brick brick : env.getObject(Brick.class)) {
      if (neighbors.containsKey(brick.position)) {
        neighbors.put(brick.position, OtherUtils.toObject(getRelativePosition(agentPos, brick.position)));
      }
    }

    int[] pos = new int[4];

    for (Integer[] relativePos : neighbors.values()) {
      if (relativePos != null) {
        for (int i = 0; i < pos.length; i++) {
          if (relativePos[i] == 1) {
            pos[i] = 1;
          }
        }
      }
    }

    return pos;
  }

  private static int[] getRelativeWithItems(Vector2D agentPos, GameEnvironment env) {
    int[] pos = new int[4];
    Item item = env.getNearestObject(Item.class);

    if (item == null) {
      return pos;
    }

    return getRelativePosition(agentPos, item.position);
  }

  private static int[] getRelativeWithWall(Vector2D agentPos, GameEnvironment env) {
    int[] pos = new int[4];
    
    Vector2D wallPos = agentPos.clone();

    // up
    wallPos.setValue(agentPos.x, agentPos.y - Sprite.DEFAULT_SIZE);
    pos[up] = env.getWallMap().get(wallPos) == null ? 0 : 1;

    // down
    wallPos.setValue(agentPos.x, agentPos.y + Sprite.DEFAULT_SIZE);
    pos[down] = env.getWallMap().get(wallPos) == null ? 0 : 1;

    // left
    wallPos.setValue(agentPos.x - Sprite.DEFAULT_SIZE, agentPos.y);
    pos[left] = env.getWallMap().get(wallPos) == null ? 0 : 1;

    // right
    wallPos.setValue(agentPos.x + Sprite.DEFAULT_SIZE, agentPos.y);
    pos[right] = env.getWallMap().get(wallPos) == null ? 0 : 1;

    return pos;
  }

  private static int[] getRelativeWithEnemies(Vector2D agentPos, GameEnvironment env) {
    int[] pos = new int[4];
    double dangerZone = Sprite.DEFAULT_SIZE * 3;

    for (Enemy enemy : env.getObject(Enemy.class)) {
      if (Vector2D.euclideanDistance(agentPos, enemy.position) <= dangerZone) {
        int[] posI = getRelativePosition(agentPos, enemy.position);

        for (int i = 0; i < posI.length; i++) {
          if (posI[i] == 1) {
            pos[i] = 1;
          }
        }
      }
    }

    return pos;
  }

  private static int[] getRelativePosition(Vector2D posSrc, Vector2D posDst) {
    int[] pos = new int[4];
    
    if (posDst == null) {
      return null;
    }

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

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}