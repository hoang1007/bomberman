package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class BombItem extends Item {
  public static int power = 1;

  @Override
  public void start() {
    super.start();
    super.setEffectDuration(10_000);
    double rate = 1;
    animator = new Animator(rate, Sprite.explosionPotion);
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }
}
