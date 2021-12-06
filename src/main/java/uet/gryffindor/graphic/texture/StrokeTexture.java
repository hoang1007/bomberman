package uet.gryffindor.graphic.texture;

import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Camera;

public class StrokeTexture extends Texture {

  public StrokeTexture(GameObject gameObject) {
    super(gameObject);
  }

  @Override
  public void render(GraphicsContext context, Camera camera) {
    Vector2D posInCanvas = camera.getRelativeposition(this.gameObject);

    if (posInCanvas != null) {
      context.strokeRect(posInCanvas.x, posInCanvas.y, gameObject.dimension.x, gameObject.dimension.y);
    }
  }
  
}
