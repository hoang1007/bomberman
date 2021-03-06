package uet.gryffindor.game.movement;

import java.util.HashMap;

import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.sprite.Sprite;

/** Bản đồ mô tả vị trí có thể đến và không thể đến trong map. Cần để A* có thể tìm đường. */
public class MovableMap {
  private HashMap<Vector2D, Boolean> map;
  private Vector2D position;
  private Vector2D dimension;

  /**
   * Khởi tạo bản đồ có thể đi tới mọi vị trí.
   * @param position vị trí của bản đồ
   * @param dimension kích thước của bản đồ
   */
  public MovableMap(Vector2D position, Vector2D dimension) {
    if (validateMap(position, dimension)) {
      this.position = position;
      this.dimension = dimension;
      this.map = new HashMap<>();

      for (int i = (int) position.x; i <= position.x + dimension.x; i += Sprite.DEFAULT_SIZE) {
        for (int j = (int) position.y; j <= position.y + dimension.y; j += Sprite.DEFAULT_SIZE) {
          map.put(new Vector2D(i, j), true);
        }
      }
    } else {
      throw new UnsupportedOperationException(
          "Position and dimension must be multiple of GRID_SIZE");
    }
  }

  /**
   * Kiểm tra xem map có thỏa mãn là một mesh grid hay không.
   *
   * @param position vị trí của bản đồ
   * @param dimension kích thước của bản đồ
   * @return
   */
  private boolean validateMap(Vector2D position, Vector2D dimension) {
    boolean cond1 = position.x % Sprite.DEFAULT_SIZE == 0;
    boolean cond2 = position.y % Sprite.DEFAULT_SIZE == 0;
    boolean cond3 = dimension.x % Sprite.DEFAULT_SIZE == 0;
    boolean cond4 = dimension.y % Sprite.DEFAULT_SIZE == 0;

    return cond1 && cond2 && cond3 && cond4;
  }

  private boolean isInside(Vector2D p) {
    Vector2D rightDown = this.position.add(this.dimension);

    boolean cond1 = this.position.x <= p.x && this.position.y <= p.y;
    boolean cond2 = p.x <= rightDown.x && p.y <= rightDown.y;

    return cond1 && cond2;
  }

  /**
   * Thêm vị trí không thể đến trong map.
   * @param position vị trí muốn thêm
   */
  public void addObstacle(Vector2D position) {
    if (isInside(position)) {
      map.put(position, false);
    }
  }

  /** Lấy trạng thái tại vị trí. */
  public boolean at(Vector2D position) {
    Boolean b = this.map.get(position);
    return b == null ? false : b;
  }

  public Vector2D getPosition() {
    return this.position;
  }

  public Vector2D getDimension() {
    return this.dimension;
  }
}
