package uet.gryffindor.game.object.statics;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Brick extends GameObject implements Unmovable {
  private RectTexture texture;

  @Override
  public void start() {
    texture = new RectTexture(Color.BROWN, this);
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
