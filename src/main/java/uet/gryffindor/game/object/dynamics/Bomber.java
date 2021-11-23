package uet.gryffindor.game.object.dynamics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Bomber extends DynamicObject {
  private DoubleProperty speed;

  private boolean isBlocked = false;
  private Vector2D oldPosition;

  @Override
  public void start() {
    this.setTexture(new AnimateTexture(this, 3, Sprite.player));
    Manager.INSTANCE.getGame().getCamera().setFocusOn(this);
    speed = new SimpleDoubleProperty(8f);

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
    switch (Input.INSTANCE.getCode()) {
      case UP:
        this.position.y -= speed.get();
        texture.changeTo("up");
        break;
      case DOWN:
        this.position.y += speed.get();
        texture.changeTo("down");
        break;
      case RIGHT:
        this.position.x += speed.get();
        texture.changeTo("right");
        break;
      case LEFT:
        this.position.x -= speed.get();
        texture.changeTo("left");
        break;
      default:
        texture.pause();
        break;
    }
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      // nếu bomber va chạm với vật thể tĩnh
      // khôi phục vị trí trước khi va chạm
      position = oldPosition.smooth(this.dimension.x, 0.3);
      // gắn nhãn bị chặn
      isBlocked = true;
    }
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      isBlocked = false;
    }
  }
}