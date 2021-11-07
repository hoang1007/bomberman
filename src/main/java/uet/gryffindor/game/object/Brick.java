package uet.gryffindor.game.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.behavior.Unmovable;

public class Brick extends GameObject implements Unmovable {

  @Override
  public void start() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(GraphicsContext context) {
    context.setFill(Color.ALICEBLUE);
    context.fillRect(position.x, position.y, dimension.x, dimension.y);
  }
}
