package uet.gryffindor.game.object;

import java.util.concurrent.TimeUnit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Bomber extends GameObject {
  SpriteTexture texture;

  private Animator leftMove;
  private Animator rightMove;
  private Animator upMove;
  private Animator downMove;

  private DoubleProperty speed;

  private boolean isBlocked = false;
  private Vector2D oldPosition;

  @Override
  public void start() {
    Manager.INSTANCE.getGame().getCamera().setFocusOn(this);
    speed = new SimpleDoubleProperty(3f);
    texture = new SpriteTexture(Sprite.player_stand, this);

    leftMove = new Animator(4, Sprite.player_left).bindRate(speed);
    rightMove = new Animator(4, Sprite.player_right).bindRate(speed);
    upMove = new Animator(4, Sprite.player_up).bindRate(speed);
    downMove = new Animator(4, Sprite.player_down).bindRate(speed);

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
        texture.setSprite(upMove.getSprite()); 
        break;
      case DOWN:
        this.position.y += speed.get();
        texture.setSprite(downMove.getSprite());
        break;
      case RIGHT:
        this.position.x += speed.get();
        texture.setSprite(rightMove.getSprite());
        break;
      case LEFT:
        this.position.x -= speed.get();
        texture.setSprite(leftMove.getSprite());
        break;
      default:
        texture.setSprite(Sprite.player_stand);
        break;
    }
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      // nếu bomber va chạm với vật thể tĩnh
      // khôi phục vị trí trước khi va chạm
      position = oldPosition.smooth(this.dimension.x);
      // gắn nhãn bị chặn
      isBlocked = true;
    }
  }

  @Override
  public void onCollisionStay(Collider that) {
    double area = collider.getDimension().x * collider.getDimension().y;
    // nếu bomber đi vào trung tâm portal (overlap area > 0.85)
    // chuyển sang map tiếp theo
    if (that.gameObject instanceof Portal && collider.getOverlapArea(that) / area > 0.85) {
      texture.setSprite(Sprite.player_stand);
      isBlocked = true;

      TimeCounter.callAfter(Manager.INSTANCE.getGame()::nextLevel, 1, TimeUnit.SECONDS);
    }
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      isBlocked = false;
    }
  }

  @Override
  public Texture getTexture() {
    return this.texture;
  }
}