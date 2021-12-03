package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class BombItem extends Item {

  @Override
  public void start() {
    super.start();
    double rate = 1;
    animator = new Animator(rate, Sprite.explosionPotion);
    effectDuration = 100;

  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }
}
