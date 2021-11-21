package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class BombItem extends Item {

  @Override
  public void start() {
    this.setTexture(new SpriteTexture(Sprite.bomb[0], this));

    effectDuration = 100;
  }

  @Override
  public void update() {}
}
