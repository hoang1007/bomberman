package uet.gryffindor.game.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import uet.gryffindor.decision.ActionNode;
import uet.gryffindor.decision.ConditionNode;
import uet.gryffindor.decision.DecisionTree;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.dynamics.Bomb;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.game.object.dynamics.explosion.Explosion;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.Geometry;
import uet.gryffindor.util.IterateUtils;

public class AutoPilot {
  private Bomber agent;
  private DynamicObject vision;
  private List<GameObject> objects = new ArrayList<>();
  private Queue<Vector2D> path = new LinkedList<>();
  private Action action = Action.Undefined;
  private DecisionTree decisionTree;
  private MovableMap map;
  private Inspector inspector;
  private Random random = new Random();

  public AutoPilot(Bomber agent) {
    this.agent = agent;
    this.inspector = this.new Inspector();

    decisionTree = new DecisionTree(
        new ConditionNode.Builder()
            .setConditionName("Is agent in a safety place?")
            .setCondition(inspector::isSafety)
            .setPositive(
                new ConditionNode.Builder()
                    .setConditionName("Is the exit available?")
                    .setCondition(inspector::isPortalAvailable)
                    .setPositive(new ActionNode<Action>().setAction(Action.GotoTheExit))
                    .setNegative(
                        new ConditionNode.Builder()
                            .setConditionName("Is there any item in the vision?")
                            .setCondition(inspector::hasItems)
                            .setPositive(
                                new ConditionNode.Builder()
                                    .setConditionName("Is there any enemy threatened agent?")
                                    .setCondition(inspector::isThreatened)
                                    .setPositive(new ActionNode<Action>().setAction(Action.KillEnemy))
                                    .setNegative(new ActionNode<Action>().setAction(Action.EarnItem))
                                    .build())
                            .setNegative(
                                new ConditionNode.Builder()
                                    .setConditionName("Is there any enemy in the vision?")
                                    .setCondition(inspector::hasEnemies)
                                    .setPositive(new ActionNode<Action>().setAction(Action.KillEnemy))
                                    .setNegative(new ActionNode<Action>().setAction(Action.DestroyObstacle))
                                    .build())
                            .build())
                    .build())
            .setNegative(new ActionNode<Action>().setAction(Action.GotoSafeZone))
            .build());

    vision = new DynamicObject() {
      private double visionRadius = 120;

      @Override
      public void start() {
        this.dimension = new Vector2D(visionRadius, visionRadius).multiply(2);
      }

      @Override
      public void update() {
        Vector2D center = agent.position.add(agent.dimension.multiply(0.5));
        this.position = center.subtract(this.dimension.multiply(0.5));
      }

      @Override
      public void onCollisionStay(Collider that) {
        if (!(that.gameObject instanceof Floor)) {
          objects.add(that.gameObject);
        }
      }
    };

    GameObject.addObject(vision);
  }

  private MovableMap initialMap() {
    Vector2D pos = vision.position.smooth(Sprite.DEFAULT_SIZE, 1);
    Vector2D dim = vision.dimension.smooth(Sprite.DEFAULT_SIZE, 1);
    MovableMap map = new MovableMap(pos, dim);

    for (var obj : objects) {
      if (obj instanceof Unmovable) {
        map.addObstacle(obj.position);
      } else if (obj instanceof Bomb) {
        // nếu gặp bom thì đánh dấu là vùng không thể tới
        map.addObstacle(obj.position);
        int explosionRadius = ((Bomb) obj).getExplosionRadius();

        for (int i = 1; i <= explosionRadius; i++) {
          map.addObstacles(
              Direction.UP.forward(obj.position, i * Sprite.DEFAULT_SIZE),
              Direction.DOWN.forward(obj.position, i * Sprite.DEFAULT_SIZE),
              Direction.LEFT.forward(obj.position, i * Sprite.DEFAULT_SIZE),
              Direction.RIGHT.forward(obj.position, i * Sprite.DEFAULT_SIZE));
        }
      } else if (obj instanceof Explosion) {
        map.addObstacle(obj.position);
      }
    }

    return map;
  }

  private void processing() {
    map = initialMap();
    Action action = (Action) decisionTree.forward().getAction();

    switch (action) {
      case KillEnemy: {
        Enemy enemy = (Enemy) inspector.target.get("enemy");
      }
        break;
      case DestroyObstacle: {
        Brick obstacle = IterateUtils.getItem(objects, Brick.class);
        if (obstacle != null) {
          path = AStar.findPath(map, agent.position, obstacle.position, agent.getSpeed());
        } else {
          action = Action.Undefined;
        }
      }
        break;
      case EarnItem:
        break;
      case GotoSafeZone:
        break;
      case GotoTheExit:
        break;
      default:
        break;
    }
  }

  public void action() {
    processing();
    if (path.isEmpty()) {
      // agent.position = Direction.valueOf(random.nextInt(4)).forward(agent.position, agent.getSpeed());
    } else {
      agent.position = path.remove();
    }

    objects.clear();
  }

  static enum Action {
    KillEnemy,
    EarnItem,
    DestroyObstacle,
    GotoSafeZone,
    GotoTheExit,
    Undefined
  }

  class Inspector {
    HashMap<String, GameObject> target = new HashMap<>();

    /** Kiểm tra xem agent có trong vùng nổ của bomb hay không. */
    boolean isSafety() {
      return map.at(agent.position);
    }

    boolean isPortalAvailable() {
      return false;
    }

    boolean hasItems() {
      for (GameObject obj : objects) {
        if (obj instanceof Item) {
          target.put("item", obj);
          return true;
        }
      }

      return false;
    }

    boolean hasEnemies() {
      for (GameObject obj : objects) {
        if (obj instanceof Enemy) {
          target.put("enemy", obj);
          return true;
        }
      }

      return false;
    }

    boolean isThreatened() {
      for (GameObject obj : objects) {
        if (obj instanceof Enemy) {
          if (Geometry.manhattanDistance(obj.position, agent.position) <= 2 * Sprite.DEFAULT_SIZE) {
            target.put("enemy", obj);
            return true;
          }
        }
      }

      return false;
    }
  }
}