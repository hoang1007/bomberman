package uet.gryffindor.game.movement;

import java.util.ArrayDeque;
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
import uet.gryffindor.game.engine.TimeCounter;
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
import uet.gryffindor.util.VoidFunction;

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
  private TimeCounter taskDoing;

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
                                    .setPositive(new ActionNode<Action>().setAction(Action.HuntEnemy))
                                    .setNegative(new ActionNode<Action>().setAction(Action.DestroyObstacle))
                                    .build())
                            .build())
                    .build())
            .setNegative(new ActionNode<Action>().setAction(Action.GotoSafeZone))
            .build());

    vision = new DynamicObject() {
      private double visionRadius = 150;

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

  public void processing() {
    map = initialMap();
    Action newAction = (Action) decisionTree.forward().getAction();

    if (newAction.ordinal() < action.ordinal()) { // nhiệm vụ mới có độ ưu tiên cao hơn
      action = newAction;
      // hủy task đang thực hiện nếu có
      if (taskDoing != null) {
        taskDoing.destroy();
      }

      switch (action) {
        case KillEnemy: {
          resetTask(true);
        }
          break;
        case DestroyObstacle: {
          boolean hasObstacle = false;

          for (GameObject obj : objects) {
            if (obj instanceof Brick) {
              Vector2D dst = nextTo(agent.position, obj.position);
              path = AStar.findPath(map, agent.position, dst, Sprite.DEFAULT_SIZE).second;

              if (!path.isEmpty()) {
                taskDoing = TimeCounter.callDuring(() -> {
                  agent.move(path.remove());
                }, path.size()).onComplete(() -> this.resetTask(true));

                hasObstacle = true;
                break;
              }
            }
          }

          if (!hasObstacle) {
            resetTask(false);
          }
        }
          break;
        case EarnItem: {
          Item item = (Item) inspector.target.get("item");
          var res = AStar.findPath(map, agent.position, item.position, Sprite.DEFAULT_SIZE);
          System.out.println("Path by item");
          path = res.second;
          taskDoing = TimeCounter.callDuring(() -> {
            agent.move(path.remove());
          }, path.size()).onComplete(() -> {
            if (res.first) {
              this.resetTask(false);
            } else {
              this.resetTask(true);
            }
          });
        }
          break;
        case GotoSafeZone: {
          // thực hiện BFS để tìm nơi an toàn
          Vector2D safePos = null;

          Queue<Vector2D> bfs = new LinkedList<>();
          HashMap<Vector2D, Vector2D> evaluated = new HashMap<>();
          Vector2D previous = null;

          bfs.add(agent.position);

          while (!bfs.isEmpty() && safePos == null) {
            Vector2D current = bfs.remove();
            evaluated.put(current, previous);

            previous = current;

            Vector2D[] neighbors = new Vector2D[4];

            for (int i = 0; i < neighbors.length; i++) {
              neighbors[i] = Direction.valueOf(i).forward(current, Sprite.DEFAULT_SIZE);
            }

            for (Vector2D neighbor : neighbors) {
              if (!evaluated.containsKey(neighbor)) {
                if (map.at(neighbor) == true) {
                  safePos = neighbor;
                  evaluated.put(safePos, current);
                  break;
                } else {
                  bfs.add(neighbor);
                }
              }
            }
          }

          if (safePos != null) {
            ArrayDeque<Vector2D> path = new ArrayDeque<>();
            for (Vector2D i = safePos.clone(); i != null; i = evaluated.get(i)) {
              path.addFirst(i);
            }
            this.path = path;

            taskDoing = TimeCounter.callDuring(new VoidFunction() {

              @Override
              public void invoke() {
                agent.move(path.remove());
              }

            }, path.size()).onComplete(() -> this.resetTask(false));
          }
        }
          break;
        case GotoTheExit: {

        }
          break;
        case HuntEnemy: {
          GameObject enemy = inspector.target.get("enemy");
          Vector2D dst = nextTo(agent.position, enemy.position);

          path = AStar.findPath(map, agent.position, dst, Sprite.DEFAULT_SIZE).second;

          taskDoing = TimeCounter.callDuring(() -> {
            agent.move(path.remove());
          }, path.size()).onComplete(() -> resetTask(true));
        }
          break;
        case Undefined: {
          agent.move(Direction.valueOf(random.nextInt(4)).forward(agent.position, Sprite.DEFAULT_SIZE));
        }
          break;
        default:
          break;
      }
    }

    System.out.println(action);
    objects.clear();
  }

  private void resetTask(boolean plantBomb) {
    action = Action.Undefined;
    if (plantBomb) {
      agent.dropBomb();
    }
  }

  private Vector2D nextTo(Vector2D src, Vector2D dst) {
    double minDis = Double.MAX_VALUE;
    Vector2D next = dst;

    for (int i = 0; i < 4; i++) {
      Vector2D neighbor = Direction.valueOf(i).forward(dst, Sprite.DEFAULT_SIZE);

      if (map.at(neighbor) == true) {
        double dis = Geometry.manhattanDistance(src, neighbor);

        if (dis < minDis) {
          minDis = dis;
          next = neighbor;
        }
      }
    }

    return next;
  }

  static enum Action {
    KillEnemy,
    GotoSafeZone,
    EarnItem,
    HuntEnemy,
    GotoTheExit,
    DestroyObstacle,
    Undefined
  }

  class Inspector {
    HashMap<String, GameObject> target = new HashMap<>();

    /** Kiểm tra xem agent có trong vùng nổ của bomb hay không. */
    boolean isSafety() {
      for (GameObject obj : objects) {
        if (obj instanceof Bomb || obj instanceof Explosion) {
          return false;
        }
      }
      return true;
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