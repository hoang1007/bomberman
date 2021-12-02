package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class SpeedItem extends Item {
  @Override
  public void start() {
    super.start();
    double rate = 1;
    animator = new Animator(rate, Sprite.speedPotion);
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }
}
