package uet.gryffindor.object;

import uet.gryffindor.base.GameObject;
import uet.gryffindor.engine.Collider;
import uet.gryffindor.engine.TimeCounter;

import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Portal extends GameObject {
  Color fill = Color.BLACK;

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


  @Override
  public void onCollisionEnter(Collider that) {
    System.out.println("2s remaining...");

    TimeCounter.callAfter(this::death, 2, TimeUnit.SECONDS);
  }

  public void death() {
    fill = Color.BLUE;
    System.out.println("ok");
  }
}
