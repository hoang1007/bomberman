package uet.gryffindor.game.object;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.texture.AnimateTexture;

public abstract class DynamicObject extends GameObject {
  protected AnimateTexture texture;

  @Override
  public AnimateTexture getTexture() {
    return this.texture;
  }

  public void setTexture(AnimateTexture texture) {
    this.texture = texture;
  }
}
