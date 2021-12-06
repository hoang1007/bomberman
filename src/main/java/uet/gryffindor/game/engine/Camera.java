package uet.gryffindor.game.engine;

import javafx.scene.canvas.Canvas;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.map.Map;
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

  /**
   * Điều chỉnh vị trí của camera
   * để focus object ở giữa khung render.
   */
  private Camera fitFocus() {
    if (focusObject != null) {
      // đặt focus object tại tâm của canvas
      // nhưng nếu khung render của camera ngoài khung canvas
      // thì chỉnh lại
      position = focusObject.position.subtract(canvasDims.multiply(0.5));
      
      if (position.x < 0) {
        position.x = 0;
      }

      if (position.y < 0) {
        position.y = 0;
      }

      if (position.x > mapDims.x - canvasDims.x) {
        position.x = mapDims.x - canvasDims.x;
      }

      if (position.y > mapDims.y - canvasDims.y) {
        position.y = mapDims.y - canvasDims.y;
      }
    }

    return this;
  }

  public Vector2D getPosition() {
    return position;
  }

  /**
   * Vị trí của object trong canvas.
   * @param obj
   * @return vị trí của object trong canvas. 
   * null nếu object không nằm trong canvas.
   */
  public Vector2D getRelativeposition(GameObject obj) {
    Vector2D posInCanvas = obj.position.subtract(fitFocus().position);

    if (posInCanvas.x + obj.dimension.x < 0 
        || posInCanvas.y + obj.dimension.y < 0 
        ||  posInCanvas.x > canvasDims.x || posInCanvas.y > canvasDims.y) {
      return null;
    } else {
      return posInCanvas;
    }
  }
}
