package uet.gryffindor.graphic.texture;

import java.util.HashMap;

import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.graphic.sprite.Sprite;

public class AnimateTexture extends Texture {
  private HashMap<String, Sprite[]> sprites;
  private int current = 0;
  private double rate; // Frame per global frame
  private String type;
  private boolean loop = true;

  /**
   * Khởi tạo animate texture.
   * @param object game object
   * @param rate tần số của animate frame (global frame per frame)
   * @param sprites các sprites
   */
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
      if (loop) {
        current = 0;
        id = 0;
      } else {
        id = s.length - 1;
      }
    }

    return s[id];
  }

  /**
   * Set loopable for this animation.
   * @param loop enable or not
   */
  public void loopable(boolean loop) {
    this.loop = loop;
  }

  public void changeTo(String type) {
    this.type = type;
    this.current = 0;
  }

  public void pause() {
    current = 0;
  }

  /**
   * Đặt rate cho animation.
   *
   * @param rate thuộc tính xác định xem cứ bao nhiêu frame gốc của chương trình
   *             thì một frame của
   *             animation sẽ được gọi.
   */
  public void setRate(double rate) {
    this.rate = rate;
  }

  public double getRate() {
    return this.rate;
  }

  /**
   * Thời gian chạy một animation theo đơn vị global frame.
   * 
   * @param type sprites có trong animation
   * @return
   */
  public long getDuration(String type) {
    return this.sprites.get(type).length * Math.round(rate);
  }

  public Sprite[] getSprites(String type) {
    return this.sprites.get(type);
  }

  @Override
  public void render(GraphicsContext context, Camera camera) {
    Vector2D posInCanvas = camera.getRelativeposition(this.gameObject);
    Sprite sprite = getSprite();

    if (posInCanvas != null) {
      context.drawImage(sprite.getSpriteSheet().getImage(), 
          sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), 
          posInCanvas.x, posInCanvas.y, gameObject.dimension.x, gameObject.dimension.y);
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
