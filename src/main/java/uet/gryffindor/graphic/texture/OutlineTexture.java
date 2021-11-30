package uet.gryffindor.graphic.texture;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Camera;

public class OutlineTexture extends Texture {
  private Paint paint;

  public OutlineTexture(GameObject gameObject, Paint p) {
    super(gameObject);
    this.paint = p;
  }

  @Override
  public void render(GraphicsContext context, Camera camera) {
    Vector2D posInCanvas = gameObject.position.subtract(camera.fitFocus().getPosition());

    context.setFill(paint);
    context.strokeRect(
        posInCanvas.x, posInCanvas.y, gameObject.dimension.x, gameObject.dimension.y);
  }
}
