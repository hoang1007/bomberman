package uet.gryffindor.autopilot;

import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.statics.items.Item;

public class GameReward {
  public static final int item = 10;
  public static final int useless = -1;
  public static final int obstacle = -10;
  public static final int portal = 10;
  public static final int flame = -10;
  public static final int dead = -10;

  private static double disToItem = Double.MAX_VALUE;

  /** Reward after take an action. */
  public static int getReward(GameAction action, GameEnvironment env) {
    Bomber agent = env.getAgent();

    if (agent.isBlocked()) {
      return obstacle;
    }

    if (agent.isDeath()) {
      System.out.println("Heavy penance");
      return dead;
    }

    if (agent.isWon()) {
      return item;
    }

    Item item = env.getNearestObject(Item.class);

    double dis = Vector2D.euclideanDistance(item.position, agent.position);

    if (dis < disToItem) {
      disToItem = (dis == 0 ? Double.MAX_VALUE : dis);
      return 1;
    } else if (dis > disToItem) {
      disToItem = dis;
      return -1;
    }

    if (action == GameAction.BOMB || action == GameAction.STAND) {
      return useless;
    }

    return 0;
  }
}
