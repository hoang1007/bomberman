package uet.gryffindor.game.engine;

import javafx.scene.canvas.Canvas;
import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.sprite.Sprite;

public class Camera {
  private Vector2D position;
  private Vector2D canvasDims;
  private GameObject focusObject;
  private Vector2D mapDims;

  public Camera(Canvas canvas, Map map) {
    this.position = Vector2D.zero();
    this.canvasDims = new Vector2D(canvas.getWidth(), canvas.getHeight());
    this.mapDims = new Vector2D(map.getWidth(), map.getHeight()).multiply(Sprite.DEFAULT_SIZE);
  }

  public Camera(Vector2D position, Canvas canvas, Map map) {
    this.position = position;
    this.canvasDims = new Vector2D(canvas.getWidth(), canvas.getHeight());
    this.mapDims = new Vector2D(map.getWidth(), map.getHeight()).multiply(Sprite.DEFAULT_SIZE);
  }

  public Camera(Canvas canvas) {
    this.position = Vector2D.zero();
    this.canvasDims = new Vector2D(canvas.getWidth(), canvas.getHeight());
  }

  public void setRange(Vector2D range) {
    this.mapDims = range;
  }

  /**
   * Chiếu cảnh với game object là trung tâm.
   *
   * @param object game object muốn camera tập trung vào
   */
  public void setFocusOn(GameObject object) {
    this.focusObject = object;
  }

  /** Điều chỉnh vị trí của camera để focus object ở giữa khung render. */
  public Camera fitFocus() {
    if (focusObject != null) {
      // đặt focus object tại tâm của canvas
      // nhưng nếu khung render của camera ngoài khung canvas
      // thì chỉnh lại
      position = focusObject.position.subtract(canvasDims.multiply(0.5));
      Vector2D downRight = position.add(canvasDims);

      if (position.x < 0) {
        position.x = 0;
      } else if (downRight.x > mapDims.x) {
        position.x = mapDims.x - canvasDims.x;
      }

      if (position.y < 0) {
        position.y = 0;
      } else if (downRight.y > mapDims.y) {
        position.y = mapDims.y - canvasDims.y;
      }
    }

    return this;
  }

  /** Kiểm tra xem một đối tượng có ở trong khung render hay không. */
  public boolean validate(Vector2D position, Vector2D dimension) {
    boolean condition1 = 0 <= position.x + dimension.x && position.x <= canvasDims.x;

    boolean condition2 = 0 <= position.y + dimension.y && position.y <= canvasDims.y;

    return condition1 && condition2;
  }

  public Vector2D getPosition() {
    return position;
  }
}
