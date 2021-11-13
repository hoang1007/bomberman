package uet.gryffindor.game.object.dynamics.enemy;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Oneal extends GameObject {
  private RectTexture texture;

  @Override
  public void start() {
    texture = new RectTexture(Color.AQUA, this);
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
