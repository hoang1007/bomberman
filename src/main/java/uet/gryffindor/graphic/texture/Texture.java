package uet.gryffindor.graphic.texture;

import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.engine.Camera;

public abstract class Texture {
  protected GameObject gameObject;

  protected Texture(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  public abstract void render(GraphicsContext context, Camera camera);
}
