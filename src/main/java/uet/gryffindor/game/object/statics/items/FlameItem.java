package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class FlameItem extends Item {
  public static int power = 2;

  @Override
  public void start() {
    super.start();
    super.setEffectDuration(10_000);
    double rate = 1;
    animator = new Animator(rate, Sprite.flamePotion);
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }
}
