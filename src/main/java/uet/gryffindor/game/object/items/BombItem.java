package uet.gryffindor.game.object.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BombItem extends Item {

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
    context.setFill(Color.AZURE);
    context.fillRect(position.x, position.y, dimension.x, dimension.y);
  }
  
}
