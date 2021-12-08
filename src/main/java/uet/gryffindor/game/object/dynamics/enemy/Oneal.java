package uet.gryffindor.game.object.dynamics.enemy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.movement.AStar;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.game.movement.MovableMap;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.Explosion;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.util.Geometry;

public class Oneal extends Enemy {
  private int attackRadius = 20;
  private Direction direction = Direction.UP;
  private double speed = 3.0;
  private Random random = new Random();
  private Queue<Vector2D> chasePath = new LinkedList<>();
  private Vector2D oldPosition;
  private boolean isBlocked = false;
  private State state = State.NORMAL;

  // Đếm mốc thời điểm gọi hai hàm để đồng bộ
  private int handleStamp = 0;
  private int updateStamp = 0;

  @Override
  public void start() {
    this.texture = new AnimateTexture(this, 5, Sprite.oneal);

    // Object giúp phát hiện bomber có vào vùng tấn công hay không
    GameObject.addObject(new GameObject() {
      @Override
      public void start() {
        this.orderedLayer = OrderedLayer.FOREGROUND;
        this.position = Oneal.this.position;
        // d = 2*r + 1
        this.dimension = new Vector2D(attackRadius, attackRadius).add(this.dimension).multiply(2);
      }

      @Override
      public void update() {
        Vector2D center = Oneal.this.position.add(Oneal.this.dimension.multiply(0.5));
        this.position = center.subtract(this.dimension.multiply(0.5));

        handleStamp += 1;
      }

      @Override
      public void onCollisionStay(Collider that) {
        if (that.gameObject instanceof Bomber) {
          boolean isInside = this.collider.getOverlapArea(that) == that.gameObject.dimension.x
              * that.gameObject.dimension.y;

          if (isInside && chasePath.isEmpty()) {
            speed = 4.0;
            var rect = Geometry.unionRect(that.gameObject.position.smooth(Sprite.DEFAULT_SIZE, 1),
                Oneal.this.position.smooth(Sprite.DEFAULT_SIZE, 1));

            MovableMap map = new MovableMap(rect.first, rect.second);
            for (GameObject m : getMap().getObjects()) {
              if (m instanceof Unmovable) {
                map.addObstacle(m.position);
              }
            }

            chasePath = AStar.findPath(map, Oneal.this.position, that.gameObject.position, speed);
            switchMode(State.ATTACK);
          }
        }
      }

      @Override
      public void onCollisionExit(Collider that) {
        if (that.gameObject instanceof Bomber) {
          speed = 3.0;
          chasePath.clear();
        }
      }
    });
  }

  @Override
  public void update() {
    updateStamp += 1;
    if (updateStamp != handleStamp) {
      updateStamp = handleStamp;
      return;
    }

    if (!isBlocked) {
      oldPosition = position.smooth(Sprite.DEFAULT_SIZE, 1);

      switch (state) {
        case NORMAL:
          move();
          break;
        case ATTACK:  
          chase();
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable || that.gameObject instanceof Magma) {
      position = oldPosition;
      isBlocked = true;
      switchMode(State.NORMAL);
    } else if (that.gameObject instanceof Explosion) {
      Manager.INSTANCE.getGame().addScore(10);
      isBlocked = true;
      this.dead();
    }
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable || that.gameObject instanceof Magma) {
      int dirCode = 0;
      do {
        dirCode = random.nextInt(4);
      } while (dirCode == direction.ordinal());

      direction = Direction.valueOf(dirCode);
      isBlocked = false;
    }
  }

  private void move() {
    this.position = direction.forward(position, speed);
    this.texture.changeTo(direction.toString());
  }

  private void chase() {
    if (chasePath.isEmpty()) {
      switchMode(State.NORMAL);
    } else {
      this.position = chasePath.remove();
    }
  }

  public void switchMode(State s) {
    if (this.state != s) {
      this.state = s;

      if (s == State.NORMAL) {
        this.texture.changeTo(direction.toString());
        chasePath.clear();
      } else {
        this.texture.changeTo("attack");
      }
    }
  }

  enum State {
    NORMAL, ATTACK;
  }
}