package uet.gryffindor.game.object;

import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;
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

public class Bomber extends GameObject {
  Sprite bomber_sprite = Sprite.player_stand; // ban đầu đứng yên

  private Animator leftMove;
  private Animator rightMove;
  private Animator upMove;
  private Animator downMove;

  private double speed = 4f;

  private boolean isBlocked = false;
  private Vector2D oldPosition;

  @Override
  public void start() {
    leftMove = new Animator(3, Sprite.player_left);
    rightMove = new Animator(3, Sprite.player_right);
    upMove = new Animator(3, Sprite.player_up);
    downMove = new Animator(3, Sprite.player_down);

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
        this.position.y -= speed;
        bomber_sprite = upMove.getSprite();
        break;
      case DOWN:
        this.position.y += speed;
        bomber_sprite = downMove.getSprite();
        break;
      case RIGHT:
        this.position.x += speed;
        bomber_sprite = rightMove.getSprite();
        break;
      case LEFT:
        this.position.x -= speed;
        bomber_sprite = leftMove.getSprite();
        break;
      default:
        bomber_sprite = Sprite.player_stand;
        break;
    }
  }

  @Override
  public void render(GraphicsContext context) {
    context.drawImage(bomber_sprite.getSpriteSheet().getImage(), 
            bomber_sprite.getX(), bomber_sprite.getY(), 
            bomber_sprite.getWidth(), bomber_sprite.getHeight(), 
            this.position.x, this.position.y, this.dimension.x, this.dimension.y);
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
      bomber_sprite = Sprite.player_stand;
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
}