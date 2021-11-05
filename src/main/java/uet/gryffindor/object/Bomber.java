package uet.gryffindor.object;

import uet.gryffindor.base.GameObject;
import uet.gryffindor.base.OrderedLayer;
import uet.gryffindor.engine.Collider;
import uet.gryffindor.engine.Input;
import uet.gryffindor.engine.TimeCounter;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;

public class Bomber extends GameObject {
  Sprite bomber_sprite = Sprite.player_stand; // ban đầu đứng yên

  private Animator leftMove;
  private Animator rightMove;
  private Animator upMove;
  private Animator downMove;

  private double speed = 5f;

  private boolean isBlocked = false;

  @Override
  public void start() {
    leftMove = new Animator(4, Sprite.player_left);
    rightMove = new Animator(4, Sprite.player_right);
    upMove = new Animator(4, Sprite.player_up);
    downMove = new Animator(4, Sprite.player_down);

    orderedLayer = OrderedLayer.MIDGROUND;
  }

  @Override
  public void update() {
    if (!isBlocked) {
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
            bomber_sprite.getX(), bomber_sprite.getY(), bomber_sprite.getWidth(), bomber_sprite.getHeight(), 
            this.position.x, this.position.y, this.dimension.x, this.dimension.y);
  }

  @Override
  public void onCollisionStay(Collider that) {
    double area = collider.getDimension().x * collider.getDimension().y;
    if (that.gameObject instanceof Portal && collider.getOverlapArea(that) / area > 0.85) {
      bomber_sprite = Sprite.player_stand;
      isBlocked = true;

      TimeCounter.callAfter(this::newLevel, 1, TimeUnit.SECONDS);
    }
  }

  private void newLevel() {
    System.out.println("new level");
  }
}