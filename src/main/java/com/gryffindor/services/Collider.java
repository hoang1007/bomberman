package com.gryffindor.services;

import java.util.ArrayList;
import java.util.List;

import com.gryffindor.base.Vector2D;
import com.gryffindor.object.GameObject;

/**
 * Lớp máy va chạm giúp phát hiện va chạm.
 */
public class Collider {
  private static List<Collider> colliders = new ArrayList<>();

  public final GameObject gameObject;

  private Vector2D dimension;
  public Vector2D position;

  private List<Collider> collidedList;
  private boolean isChangeByGameObject;

  public Collider(GameObject gameObject) {
    this.gameObject = gameObject;
    this.collidedList = new ArrayList<>();
    this.isChangeByGameObject = true;

    colliders.add(this);

    // Thuộc tính mặc định là thuộc tính của game object
    this.dimension = gameObject.dimension.clone();
    this.position = gameObject.position.clone();
  }

  public Collider(GameObject gameObject, Vector2D dimension) {
    this.gameObject = gameObject;
    this.dimension = dimension;
    this.isChangeByGameObject = true;

    this.collidedList = new ArrayList<>();

    colliders.add(this);
  }

  public void changeByGameObject(boolean value) {
    this.isChangeByGameObject = value;
  }

  public Vector2D getDimension() {
    return this.dimension;
  }

  public void setDimension(Vector2D dimension) {
    this.dimension.setValue(dimension.x, dimension.y);
    this.isChangeByGameObject = false;
  }


  /**
   * Hàm cân đối collider với game object
   * (Vì tâm của collider phải trùng với tâm của game object).
   * @return tọa độ trung tâm sau khi cân bằng
   */
  private Vector2D fitObject() {
    Vector2D center = gameObject.position.add(gameObject.dimension.multiply(0.5f));

    if (isChangeByGameObject) {
      this.dimension.setValue(gameObject.dimension.x, gameObject.dimension.y);
    }

    this.position = center.subtract(dimension.multiply(0.5f));
    return center;
  }

  /**
   * Kiểm tra xem hai collider có va chạm với nhau hay không.
   * @param that collider muốn kiểm tra
   * @return true nếu va chạm
   */
  public boolean isCollision(Collider that) {
    this.fitObject();
    that.fitObject();

    Vector2D topLeft1 = this.position;
    Vector2D topLeft2 = that.position;

    Vector2D botRight1 = topLeft1.add(this.dimension);
    Vector2D botRight2 = topLeft2.add(that.dimension);

    // tìm tọa độ 4 đỉnh của hình chữ nhật giao
    double xLeft = Math.max(topLeft1.x, topLeft2.x);
    double xRight = Math.min(botRight1.x, botRight2.x);
    double yTop = Math.max(topLeft1.y, topLeft2.y);
    double yBottom = Math.min(botRight1.y, botRight2.y);

    if (xLeft <= xRight && yTop <= yBottom) {
      return true;
    }

    return false;
  }

  /** 
   * Kiểm tra va chạm của tất cả các collider được khai báo.
   * Nếu có hai collider va chạm với nhau, các hàm {@link GameObject#onCollision(Collider)}
   * của game object chứa colldier sẽ được gọi.
  */
  public static void checkCollision() {
    for (int i = 0; i < colliders.size() - 1; i++) {
      Collider a = colliders.get(i);

      for (int j = i + 1; j < colliders.size(); j++) {
        Collider b = colliders.get(j);

        // Nếu a va chạm với b
        if (a.isCollision(b)) {
          // Nếu danh sách va chạm của a chưa có b
          // thì gọi onCollsionEnter
          if (a.collidedList.contains(b)) {
            // Nếu đã va chạm
            // thì gọi hàm onCollsionStay
            a.gameObject.onCollisionStay(b);
            b.gameObject.onCollisionStay(a);
          } else {
            a.gameObject.onCollisionEnter(b);
            a.collidedList.add(b);

            b.gameObject.onCollisionEnter(a);
            b.collidedList.add(a);
          }
        } else {
          // Nếu danh sách va chạm của a vẫn chứa b
          // a và b mới bắt đầu rời va chạm
          int indexOfb = a.collidedList.indexOf(b);
          
          if (indexOfb != -1) {
            a.gameObject.onCollisionExit(b);
            a.collidedList.remove(indexOfb);

            b.gameObject.onCollisionExit(a);
            b.collidedList.remove(a);
          }
        }
      }
    }
  }
}
