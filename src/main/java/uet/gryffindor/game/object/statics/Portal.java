package uet.gryffindor.game.object.statics;

import uet.gryffindor.game.object.StaticObject;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class Portal extends StaticObject {
  @Override
  public void start() {
    this.setTexture(new SpriteTexture(Sprite.obstacle[3], this));
  }

  @Override
  public void update() {}
}
