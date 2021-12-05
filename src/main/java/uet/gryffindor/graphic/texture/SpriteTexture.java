package uet.gryffindor.graphic.texture;

import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.graphic.sprite.Sprite;

public class SpriteTexture extends Texture {
  private Sprite sprite;

  public SpriteTexture(Sprite sprite, GameObject gameObject) {
    super(gameObject);
    this.sprite = sprite;
  }

  public Sprite getSprite() {
    return this.sprite;
  }

  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }

  @Override
  public void render(GraphicsContext context, Camera camera) {
    Vector2D posInCanvas = camera.getRelativeposition(this.gameObject);

    if (posInCanvas != null) {
      context.drawImage(sprite.getSpriteSheet().getImage(), sprite.getX(), sprite.getY(), sprite.getWidth(),
          sprite.getHeight(), posInCanvas.x, posInCanvas.y, gameObject.dimension.x, gameObject.dimension.y);
    }
  }
}
