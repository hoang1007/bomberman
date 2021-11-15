package uet.gryffindor.game.object.statics;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Brick extends GameObject implements Unmovable {
  protected SpriteTexture texture;

  @Override
  public void start() {
  }

  @Override
  public void update() {
  }

  @Override
  public Texture getTexture() {
    return this.texture;
  }
}
