package uet.gryffindor.game.object.dynamics.enemy;

import java.util.Random;

import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.game.object.dynamics.Explosion;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Balloom extends Enemy {
  private Direction direction = Direction.UP;
  private double speed = 2.0;
  private boolean isBlocked = false;
  private Vector2D oldPosition;
  private Random random = new Random();

  @Override
  public void start() {
    this.texture = new AnimateTexture(this, 6, Sprite.balloom);
    oldPosition = position.clone();
  }

  @Override
  public void update() {
    if (!isBlocked) {
      move();
    }
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable || that.gameObject instanceof Magma) {
      this.position = oldPosition.smooth(Sprite.DEFAULT_SIZE, 1);
      this.isBlocked = true;
    } else if (that.gameObject instanceof Explosion) {
      Manager.INSTANCE.getGame().addScore(5);
      this.isBlocked = true;
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
    oldPosition.setValue(position);
    position = direction.forward(position, speed);
    texture.changeTo(direction.toString());
  }
}
