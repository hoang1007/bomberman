package uet.gryffindor.game.object.statics.items;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class SpeedItem extends GameObject {
  private SpriteTexture texture;

  @Override
  public void start() {
    texture = null;
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

  @Override
  public Texture getTexture() {
    return this.texture;
  }

}
