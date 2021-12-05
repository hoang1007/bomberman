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
    double rate = 1;
    animator = new Animator(rate, Sprite.heart);
    effectDuration = 100;
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Bomber) {
      int backupExplosionRadius = Bomb.getExplosionRadius();
      Bomb.setExploredRadius(backupExplosionRadius + 1);

      this.destroy();

      TimeCounter.callAfter(() -> Bomb.setExploredRadius(backupExplosionRadius),
          effectDuration, TimeUnit.SECONDS);
    }
  }
}
