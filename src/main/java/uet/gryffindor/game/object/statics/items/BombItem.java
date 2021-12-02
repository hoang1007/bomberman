package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class BombItem extends Item {

  @Override
  public void start() {
    super.start();
    double rate = 1;
    animator = new Animator(rate, Sprite.explosionPotion);

  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }
}
