package uet.gryffindor.graphic.texture;

import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.graphic.sprite.Sprite;

import java.util.HashMap;

public class AnimateTexture extends Texture {
  private HashMap<String, Sprite[]> sprites;
  private int current = 0;
  private double rate; // Frame per global frame
  private String type;

  public AnimateTexture(GameObject object, double rate, HashMap<String, Sprite[]> sprites) {
    super(object);
    this.sprites = sprites;
    this.rate = rate;

    for (String k : sprites.keySet()) {
      type = k;
      break;
    }
  }

  private Sprite getSprite() {
    Sprite[] s = this.sprites.get(type);

    int id = (int) (current++ / rate);
    if (id >= s.length) {
      current = 0;
      id = 0;
    }

    return s[id];
  }

  public void changeTo(String type) {
    this.type = type;
  }

  public void pause() {
    current = 0;
  }

  /**
   * Đặt rate cho animation.
   *
   * @param rate thuộc tính xác định xem cứ bao nhiêu frame gốc của chương trình thì một frame của
   *     animation sẽ được gọi.
   */
  public void setRate(double rate) {
    this.rate = rate;
  }

  public double getRate() {
    return this.rate;
  }

  @Override
  public void render(GraphicsContext context, Camera camera) {
    Vector2D posInCanvas = gameObject.position.subtract(camera.fitFocus().getPosition());
    Sprite sprite = getSprite();

    if (camera.validate(posInCanvas, gameObject.dimension)) {
      context.drawImage(
          sprite.getSpriteSheet().getImage(),
          sprite.getX(),
          sprite.getY(),
          sprite.getWidth(),
          sprite.getHeight(),
          posInCanvas.x,
          posInCanvas.y,
          gameObject.dimension.x,
          gameObject.dimension.y);
    }
  }

  /**
   * Thay đổi rate dựa vào giá trị cho trước.
   *
   * @param value giá trị bind
   */
  public AnimateTexture bindRate(ObservableValue<? extends Number> value) {
    value.addListener(
        (observable, oldVal, newVal) -> {
          this.rate -= (newVal.doubleValue() - oldVal.doubleValue()) / rate;

          if (this.rate < 1) {
            this.rate = 1;
          }
        });

    return this;
  }
}
