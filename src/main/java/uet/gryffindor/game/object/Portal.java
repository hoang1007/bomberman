package uet.gryffindor.game.object;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Portal extends GameObject {
  private RectTexture texture;

  @Override
  public void start() {
    texture = new RectTexture(Color.RED, this);
  }

  @Override
  public void update() {

  }

  @Override
  public Texture getTexture() {
    return this.texture;
  }
}
