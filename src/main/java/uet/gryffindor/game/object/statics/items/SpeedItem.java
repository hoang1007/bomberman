package uet.gryffindor.game.object.statics.items;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class SpeedItem extends Item {
  private static Stack<Double> boostedHistory = new Stack<Double>();
  private static double power = 1.5;
  private static final double speedThreshold = 400f;

  @Override
  public void start() {
    super.start();
    effectDuration = 10_000;
    double rate = 1;
    animator = new Animator(rate, Sprite.speedPotion);
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
    
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Bomber) {
      Bomber bomber = (Bomber) that.gameObject;
      double backupSpeed = bomber.getSpeed();
      double speedBoosted = backupSpeed * power;

      boostedHistory.push(backupSpeed);

      if (speedBoosted > speedThreshold) {
        speedBoosted = speedThreshold;
      }

      bomber.setSpeed(speedBoosted);

      TimeCounter.callAfter(() -> bomber.setSpeed(boostedHistory.pop()),
          effectDuration, TimeUnit.MILLISECONDS);

      this.destroy();
    }
  }
}
