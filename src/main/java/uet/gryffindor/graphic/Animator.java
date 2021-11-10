package uet.gryffindor.graphic;

import javafx.beans.value.ObservableValue;
import uet.gryffindor.graphic.sprite.Sprite;

public class Animator {
  private Sprite[] sprites;
  private int current = 0;

  // Frame per global frame.
  private double rate;

  public Animator(Sprite... sprites) {
    this.sprites = sprites;
  }

  /**
   * Khởi tạo animator.
   * @param rate thuộc tính xác định xem cứ bao nhiêu frame gốc của chương trình
   * thì một frame của animation sẽ được gọi.
   * @param sprites các frame của một animation.
   */
  public Animator(double rate, Sprite... sprites) {
    this.rate = rate;
    this.sprites = sprites;
  }

  public Sprite getSprite() {
    int id = (int) (current++ / rate);
    if (id >= this.sprites.length) {
      current = 0;
      id = 0;
    }

    return this.sprites[id];
  }

  /**
   * Đặt rate cho animation.
   * @param rate thuộc tính xác định xem cứ bao nhiêu frame gốc của chương trình
   * thì một frame của animation sẽ được gọi.
   */
  public void setRate(double rate) {
    this.rate = rate;
  }

  public double getRate() {
    return this.rate;
  }

  /**
   * Thay đổi rate dựa vào giá trị cho trước.
   * @param value giá trị bind
   */
  public Animator bindRate(ObservableValue<? extends Number> value) {
    value.addListener((observable, oldVal, newVal) -> {
      this.rate -= (newVal.doubleValue() - oldVal.doubleValue()) / rate;

      if (this.rate < 1) {
        this.rate = 1;
      }

    });

    return this;
  }

  public int getTotalFrames() {
    return this.sprites.length;
  }
}
