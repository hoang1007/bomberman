package com.gryffindor.object;

import com.gryffindor.base.Vector2D;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
  /** Vị trí của đối tượng. */
  protected Vector2D position;

  public GameObject() {
    position = Vector2D.zero();
    start();
  }

  /** 
   * Khởi tạo các thuộc tính
   * (thay thế cho contructor).
   */
  public abstract void start();

  /** 
   * Cập nhật mỗi frame.
  */
  public abstract void update();

  /**
   * Render mỗi frame.
   * */
  public abstract void render(GraphicsContext context);
}
