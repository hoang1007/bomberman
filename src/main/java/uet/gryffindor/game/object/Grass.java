package uet.gryffindor.game.object;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Grass extends GameObject {
  private RectTexture texture;

  @Override
  public void start() {
    this.collider.setEnable(false);

    texture = new RectTexture(Color.GREEN, this);
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
