package uet.gryffindor.game.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;

public class Grass extends GameObject {
  @Override
  public void start() {
    this.position.setValue(400, 300);
    this.collider.setEnable(false);
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

  @Override
  public void render(GraphicsContext context) {
    context.setFill(Color.GREEN);
    context.fillRect(position.x, position.y, dimension.x, dimension.y);
  }
}
