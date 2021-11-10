package uet.gryffindor.game.object;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Wall extends GameObject implements Unmovable {
  private RectTexture texture;
  
  @Override
  public void start() {
    texture = new RectTexture(Color.GRAY, this);
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
