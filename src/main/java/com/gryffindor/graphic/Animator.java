package com.gryffindor.graphic;

import com.gryffindor.graphic.sprite.Sprite;

public class Animator {
  private Sprite[] sprites;
  private int current = 0;

  // Frame per global frame.
  private int rate = 3;

  public Animator(Sprite... sprites) {
    this.sprites = sprites;
  }

  /**
   * Khởi tạo animator.
   * @param rate thuộc tính xác định xem cứ bao nhiêu frame gốc của chương trình
   * thì một frame của animation sẽ được gọi.
   * @param sprites các frame của một animation.
   */
  public Animator(int rate, Sprite... sprites) {
    this.rate = rate;
    this.sprites = sprites;
  }

  public Sprite getSprite() {
    Sprite currentSprite = this.sprites[current++ / rate];
    
    if (current / rate >= this.sprites.length) {
      current = 0;
    }

    return currentSprite;
  }

  /**
   * Đặt rate cho animation.
   * @param rate thuộc tính xác định xem cứ bao nhiêu frame gốc của chương trình
   * thì một frame của animation sẽ được gọi.
   */
  public void setRate(int rate) {
    this.rate = rate;
  }
}
