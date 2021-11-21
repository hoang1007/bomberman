package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class FlameItem extends Item {

  @Override
  public void start() {
    this.setTexture(new SpriteTexture(Sprite.speedPotion[0], this));
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }
}
