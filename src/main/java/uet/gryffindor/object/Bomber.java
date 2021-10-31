package uet.gryffindor.object;

import uet.gryffindor.base.GameObject;
import uet.gryffindor.base.OrderedLayer;
import uet.gryffindor.engine.Input;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

import javafx.scene.canvas.GraphicsContext;

public class Bomber extends GameObject {
  Sprite bomber_sprite = Sprite.player_stand; // ban đầu đứng yên

  private Animator leftMove;
  private Animator rightMove;
  private Animator upMove;
  private Animator downMove;

  private double speed = 5f;

  @Override
  public void start() {
    leftMove = new Animator(4, Sprite.player_left);
    rightMove = new Animator(4, Sprite.player_right);
    upMove = new Animator(4, Sprite.player_up);
    downMove = new Animator(4, Sprite.player_down);

    orderedLayer = OrderedLayer.FOREGROUND;
  }

  @Override
  public void update() {
    move();
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
}