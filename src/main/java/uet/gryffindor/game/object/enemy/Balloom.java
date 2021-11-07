package uet.gryffindor.game.object.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Balloom extends Enemy {

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
    context.setFill(Color.ANTIQUEWHITE);
    context.fillRect(position.x, position.y, dimension.x, dimension.y);
  }
  
}
