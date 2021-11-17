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
  public static final int enemy = -10;

  private static double disToItem = Double.MAX_VALUE;

  public static int getReward(GameAction action, GameEnvironment env) {
    Bomber agent = env.getAgent();

    if (agent.isBlocked()) {
      return obstacle;
    }

    if (action == GameAction.BOMB || action == GameAction.STAND) {
      return useless;
    }

    Item item = env.getObject(Item.class).get(0);

    double dis = Vector2D.distanceOfPoint(item.position, agent.position);

    if (dis < disToItem) {
      disToItem = dis;
      return 1;
    } else if (dis > disToItem) {
      return -1;
    }

    return 0;
  }
}
