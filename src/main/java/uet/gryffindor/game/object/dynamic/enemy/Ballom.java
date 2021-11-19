package uet.gryffindor.game.object.dynamic.enemy;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.game.object.DynamicObject;

public class Ballom extends DynamicObject {
  private DoubleProperty speed;

  private boolean isBlocked = false;
  private Vector2D oldPosition;

  @Override
  public void start() {
    Manager.INSTANCE.getGame().getCamera().setFocusOn(this);
    speed = new SimpleDoubleProperty(6f);

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

  private void move() {
    int street = (int) Math.random() * 10;

  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable) { // nếu bomber va chạm với vật thể tĩnh
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
