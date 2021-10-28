package com.gryffindor.object;

import com.gryffindor.base.Vector2D;
import com.gryffindor.services.Collider;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {

  public static final double SPEECH = 5f;

  public Vector2D position; // Vị trí của đối tượng.
  public Vector2D dimension; // Kích thước của object
  protected Collider collider;

  public GameObject() {
    position = Vector2D.zero();
    dimension = new Vector2D(50, 50);
    collider = new Collider(this);
    
    start();
  }

  /**
   * Khởi tạo các thuộc tính (thay thế cho contructor).
   */
  public abstract void start();

  /**
   * Cập nhật mỗi frame.
   */
  public abstract void update();

  /**
   * Render mỗi frame.
   */
  public abstract void render(GraphicsContext context);

  /**
   * Hàm được gọi khi hai {@link Collider} bắt đầu va chạm.
   * @param that collider bị va chạm
   */
  public void onCollisionEnter(Collider that) {

  }

  /**
   * Hàm được gọi mỗi frame khi hai {@link Collider} chồng lên nhau.
   * @param that collider bị chồng.
   */
  public void onCollisionStay(Collider that) {

  }

  /**
   * Hàm được gọi khi hai {@link Collider} rời khỏi va chạm.
   * @param that collider bị va chạm
   */
  public void onCollisionExit(Collider that) {

  }
}
