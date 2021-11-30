package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

import java.util.Random;

public class Balloom extends Enemy {
  private Direction direction = Direction.UP;
  private double speed = 2.0;
  private Random random = new Random();

  @Override
  public void start() {
    this.texture = new AnimateTexture(this, 6, Sprite.balloom);
  }

  @Override
  public void update() {
    move();
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      this.position.smooth(Sprite.DEFAULT_SIZE, 1);

      int dirCode = 0;
      do {
        dirCode = random.nextInt(4);
      } while (dirCode == direction.ordinal());

      direction = Direction.valueOf(dirCode);
    }
  }

  private void move() {
    position = direction.forward(position, speed);
    texture.changeTo(direction.toString());
  }
}
