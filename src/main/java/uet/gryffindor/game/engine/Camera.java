package uet.gryffindor.game.engine;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;

public class Camera {
  private Vector2D position;
  private Vector2D canvasDims;
  private GameObject focusObject;

  public Camera(Vector2D canvasDims) {
    this.position = Vector2D.zero();
    this.canvasDims = canvasDims;
  }

  public Camera(Vector2D position, Vector2D canvasDims) {
    this.position = position;
    this.canvasDims = canvasDims;
  }

  /**
   * Chiếu cảnh với game object là trung tâm.
   * @param object game object muốn camera tập trung vào
   */
  public void setFocusOn(GameObject object) {
    this.focusObject = object;
  }

  /**
   * Điều chỉnh vị trí của camera 
   * để focus object ở giữa khung render.
   */
  public Camera fitFocus() {
    if (focusObject != null) {
      // đặt focus object tại tâm của canvas
      // nhưng nếu khung render của camera ngoài khung canvas
      // thì chỉnh lại
      position = focusObject.position.subtract(canvasDims.multiply(0.5));

      if (position.x < 0) {
        position.x = 0;
      } else if (position.x > canvasDims.x) {
        position.x = canvasDims.x;
      }

      if (position.y < 0) {
        position.y = 0;
      } else if (position.y > canvasDims.y) {
        position.y = canvasDims.y;
      }
    }

    return this;
  }

  /**
   * Kiểm tra xem một đối tượng có ở trong khung render hay không.
   */
  public boolean validate(Vector2D position, Vector2D dimension) {
    boolean condition1 = 0 <= position.x + dimension.x 
                        && position.x <= canvasDims.x;

    boolean condition2 = 0 <= position.y + dimension.y 
                        && position.y <= canvasDims.y;

    return condition1 && condition2;
  }

  public Vector2D getPosition() {
    return position;
  }
}
