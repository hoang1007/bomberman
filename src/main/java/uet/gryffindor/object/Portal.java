package uet.gryffindor.object;

import uet.gryffindor.base.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Portal extends GameObject {
  Color fill = Color.RED;

  @Override
  public void start() {
    position.setValue(300, 200);
  }

  @Override
  public void update() {

  }

  @Override
  public void render(GraphicsContext context) {
    context.setFill(fill);
    context.fillRect(position.x, position.y, dimension.x, dimension.y);
  }
}
