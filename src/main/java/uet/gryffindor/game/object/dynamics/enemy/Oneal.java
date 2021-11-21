package uet.gryffindor.game.object.dynamics.enemy;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import uet.gryffindor.autopilot.GameAction;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.DynamicObject;

public class Oneal extends DynamicObject {
  private DoubleProperty speed;
  private boolean isBlocked = false;
  private Vector2D oldPosition;

  @Override
  public void start() {
    speed = new SimpleDoubleProperty(6f); // setup tốc độ khác nhau cho từng enemy
    orderedLayer = OrderedLayer.MIDGROUND;
    oldPosition = position.clone();
  }

  @Override
  public void update() {
    if (!isBlocked) {
      oldPosition = position.clone();
      move();
    }
  }

  /**
   * Hàm di chuyển cho enemy.
   * Tạm thời thế thôi cần sử lại cho nó đi khôn hơn.
   */
  private void move() {
    int value = ((int) (Math.random() * 100)) % 4 ;
    // random hướng cho enemy.
    switch (GameAction.valueOf(value)) {
      case UP:
        this.position.y -= speed.get();
        // load texture
        break;
      case DOWN:
        this.position.y += speed.get();
        // load texture
        break;
      case RIGHT:
        this.position.x += speed.get();
        // load texture
        break;
      case LEFT:
        this.position.x -= speed.get();
        // load texture: texture.changeTo("name");
        break;
      default:
        texture.pause();
        break;
    }
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable) { // nếu oneal va chạm với vật thể tĩnh
      position = oldPosition.smooth(this.dimension.x); // khôi phục vị trí trước khi va chạm
      isBlocked = true; // gắn nhãn bị chặn
    }
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      isBlocked = false;
    }
  }
}
