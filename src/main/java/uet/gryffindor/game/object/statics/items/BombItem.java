package uet.gryffindor.game.object.statics.items;

import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class BombItem extends Item {
  public static int power = 1;

  @Override
  public void start() {
    super.start();
    effectDuration = 10_000;
    double rate = 1;
    animator = new Animator(rate, Sprite.explosionPotion);
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Bomber) {
      Bomber bomber = (Bomber) that.gameObject;
      bomber.setBombsCount(bomber.getBombCount() + 1);

      TimeCounter.callAfter(() -> bomber.setBombsCount(bomber.getBombCount() - 1),
          effectDuration, TimeUnit.MILLISECONDS);

      this.destroy();
    }
  }
}
