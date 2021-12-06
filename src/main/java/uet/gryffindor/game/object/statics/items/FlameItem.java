package uet.gryffindor.game.object.statics.items;

import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.dynamics.Bomb;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class FlameItem extends Item {

  @Override
  public void start() {
    super.start();
    effectDuration = 10_000;
    double rate = 1;
    animator = new Animator(rate, Sprite.flamePotion);
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Bomber) {
      Bomb.setExploredRadius(Bomb.getExplosionRadius() + 1);

      TimeCounter.callAfter(() -> {
        Bomb.setExploredRadius(Bomb.getExplosionRadius() - 1);
      }, effectDuration, TimeUnit.MILLISECONDS);

      this.destroy();
    }
  }
}
