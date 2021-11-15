package uet.gryffindor.game.object;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.texture.SpriteTexture;

public abstract class StaticObject extends GameObject {
  private SpriteTexture texture;

  @Override
  public SpriteTexture getTexture() {
    return this.texture;
  }

  public void setTexture(SpriteTexture spriteTexture) {
    this.texture = spriteTexture;
  }
}
