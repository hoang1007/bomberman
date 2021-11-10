package uet.gryffindor.graphic.texture;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Camera;

public class RectTexture extends Texture {
  private Paint fill = Color.BLACK;

  public RectTexture(Paint p, GameObject gameObject) {
    super(gameObject);
    this.fill = p;
  }

  @Override
  public void render(GraphicsContext context, Camera camera) {
    Vector2D posInCanvas = gameObject.position.subtract(camera.fitFocus().getPosition());

    if (camera.validate(posInCanvas, gameObject.dimension)) {
      context.setFill(fill);
      context.fillRect(posInCanvas.x, posInCanvas.y, gameObject.dimension.x, gameObject.dimension.y);
    }
  }
}
