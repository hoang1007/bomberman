package uet.gryffindor.game.object.items;

import javafx.scene.paint.Color;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.Texture;

public class BombItem extends Item {
  private RectTexture texture;
  
  @Override
  public void start() {
    texture = new RectTexture(Color.AZURE, this);
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
