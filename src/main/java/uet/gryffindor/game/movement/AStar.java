package uet.gryffindor.game.movement;

import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.Geometry;

import java.util.*;

public class AStar {
  static class MoveStep implements Comparable<MoveStep> {
    public Vector2D position;
    public Direction direction;
    public MoveStep preStep;
    private double hCost;
    private double gCost;
    private double cost;

    public MoveStep(MoveStep preStep, Direction direction, Vector2D dstPos) {
      this.position = direction.forward(preStep.position, Sprite.DEFAULT_SIZE);

      this.direction = direction;
      this.preStep = preStep;
      this.hCost = preStep.hCost + Geometry.manhattanDistance(position, preStep.position);
      this.gCost = Geometry.manhattanDistance(position, dstPos);

      this.cost = this.hCost + this.gCost;
    }

    public MoveStep(Vector2D position, Direction direction, MoveStep preStep, double cost) {
      this.position = position;
      this.direction = direction;
      this.preStep = preStep;
      this.cost = cost;
    }

    @Override
    public int compareTo(MoveStep o) {
      return Double.compare(this.cost, o.cost);
    }

    @Override
    public String toString() {
      return this.direction.toString();
    }
  }

  private static MoveStep[] findNeighbors(MoveStep curStep, Vector2D src, Vector2D dst) {
    MoveStep[] ms = new MoveStep[4];

    ms[Direction.UP.ordinal()] = new MoveStep(curStep, Direction.UP, dst);
    ms[Direction.DOWN.ordinal()] = new MoveStep(curStep, Direction.DOWN, dst);
    ms[Direction.LEFT.ordinal()] = new MoveStep(curStep, Direction.LEFT, dst);
    ms[Direction.RIGHT.ordinal()] = new MoveStep(curStep, Direction.RIGHT, dst);

    return ms;
  }

  /**
   * Chuyển list direction trên grid thành list position step.
   *
   * @param path
   * @param src
   * @param dst
   * @param step
   * @return
   */
  private static Queue<Vector2D> adjustPath(
    Stack<MoveStep> path, Vector2D src, Vector2D dst, double step) {
    Queue<Vector2D> result = new LinkedList<>();

    while (!path.isEmpty()) {
      Vector2D gridPos = path.pop().position;

      if (src.x > gridPos.x) {
        while (src.x - gridPos.x >= step) {
          src = Direction.LEFT.forward(src, step);
          result.add(src);
        }

        if (src.x > gridPos.x) {
          src.x = gridPos.x;
          result.add(src);
        }
      } else if (src.x < gridPos.x) {
        while (gridPos.x - src.x >= step) {
          src = Direction.RIGHT.forward(src, step);
          result.add(src);
        }

        if (src.x < gridPos.x) {
          src.x = gridPos.x;
          result.add(src);
        }
      }

      if (src.y > gridPos.y) {
        while (src.y - gridPos.y >= step) {
          src = Direction.UP.forward(src, step);
          result.add(src);
        }

        if (src.y > gridPos.y) {
          src = gridPos.clone();
          result.add(src);
        }
      } else if (src.y < gridPos.y) {
        while (gridPos.y - src.y >= step) {
          src = Direction.DOWN.forward(src, step);
          result.add(src);
        }

        if (src.y < gridPos.y) {
          src = gridPos.clone();
          result.add(src);
        }
      }
    }

    return result;
  }

  public static Queue<Vector2D> findPath(
    MovableMap canMove, Vector2D srcPosition, Vector2D dstPosition, double step) {
    Stack<MoveStep> path = new Stack<>();
    Queue<MoveStep> queue = new PriorityQueue<>();
    Map<Vector2D, MoveStep> evaluated = new HashMap<>();

    Vector2D srcGridPos = srcPosition.smooth(Sprite.DEFAULT_SIZE, 1);
    Vector2D dstGridPos = dstPosition.smooth(Sprite.DEFAULT_SIZE, 1);

    queue.add(new MoveStep(srcGridPos, null, null, Double.MAX_VALUE));

    while (!queue.isEmpty()) {
      MoveStep current = queue.remove();
      evaluated.put(current.position, current);

      if (current.position.equals(dstGridPos)) {
        break;
      }

      MoveStep[] neighbors = findNeighbors(current, srcGridPos, dstGridPos);

      for (MoveStep neighbor : neighbors) {
        if (neighbor != null) {
          if (canMove.at(neighbor.position) == false) {
            continue;
          }

          if (!evaluated.containsKey(neighbor.position)) {
            queue.add(neighbor);
          }
        }
      }
    }

    MoveStep traceBack = evaluated.get(dstGridPos);

    if (traceBack != null && traceBack.preStep != null) {
      while (traceBack.preStep.direction != null) {
        path.push(traceBack);
        traceBack = traceBack.preStep;
      }
    }

    return adjustPath(path, srcPosition, dstPosition, step);
  }
}
