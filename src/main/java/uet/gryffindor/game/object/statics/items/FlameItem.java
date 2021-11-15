package uet.gryffindor.game.object.statics.items;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.texture.Texture;

public class FlameItem extends GameObject {
  private RectTexture texture;

  @Override
  public void start() {
    texture = new RectTexture(Color.AQUAMARINE, this);
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
