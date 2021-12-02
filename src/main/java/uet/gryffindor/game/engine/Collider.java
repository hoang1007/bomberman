package uet.gryffindor.game.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.Geometry;
import uet.gryffindor.util.Pair;

/**
 * Lớp máy va chạm giúp phát hiện va chạm.
 */
public class Collider {
  public final GameObject gameObject;
  private boolean isEnabled = true;

  private HashMap<Collider, Pair<Boolean, Double>> collidedList = new HashMap<>();

  public Collider(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  public void enabled(boolean enabled) {
    this.isEnabled = enabled;
  }

  public boolean isEnabled() {
    return this.isEnabled;
  }

  /**
   * Lấy diện tích giao nhau giữa hai collider.
   * 
   * @param that collider muốn tìm diện tích giao nhau với collider hiện tại
   * @return diện tích giao nhau của hai collider
   */
  public double getOverlapArea(Collider that) {
    return this.collidedList.get(that).second;
  }

  /**
   * Tính diện tích giao nhau của hai collider.
   * 
   * @param that collider muốn kiểm tra
   * @return diện tích của vùng giao nhau
   */
  public double computeOverlapArea(Collider that) {
    Vector2D topLeft1 = this.gameObject.position;
    Vector2D topLeft2 = that.gameObject.position;

    Vector2D botRight1 = topLeft1.add(this.gameObject.dimension);
    Vector2D botRight2 = topLeft2.add(that.gameObject.dimension);

    // tìm tọa độ 4 đỉnh của hình chữ nhật giao
    double xLeft = Math.max(topLeft1.x, topLeft2.x);
    double xRight = Math.min(botRight1.x, botRight2.x);
    double yTop = Math.max(topLeft1.y, topLeft2.y);
    double yBottom = Math.min(botRight1.y, botRight2.y);

    if (xLeft < xRight && yTop < yBottom) {
      return (xRight - xLeft) * (yBottom - yTop);
    }

    return 0;
  }

  /**
   * Kiểm tra va chạm của tất cả các collider được khai báo. Nếu có hai collider
   * va chạm với nhau, các hàm {@link GameObject#onCollision(Collider)} của game
   * object chứa colldier sẽ được gọi.
   */
  public static void checkCollision(List<GameObject> objects) {
    // lập bản đồ lưới, mỗi ô là list object có vị trí tại đó
    // nếu có ô có hai object trở lên thì các object va chạm với nhau
    HashMap<Vector2D, List<GameObject>> posGrid = new HashMap<>();

    for (GameObject object : objects) {
      for (Vector2D pos : getGridPositions(object)) {
        if (posGrid.containsKey(pos)) {
          posGrid.get(pos).add(object);
        } else {
          posGrid.put(pos, new ArrayList<>(List.of(object)));
        }
      }
    }

    for (var pos : posGrid.values()) {
      // nếu có nhiều hơn một object cùng một vị trí
      for (int i = 0; i < pos.size() - 1; i++) {
        for (int j = i + 1; j < pos.size(); j++) {
          checkTwoColliders(pos.get(i).getCollider(), pos.get(j).getCollider());
        }
      }
    }

    for (GameObject object : objects) {
      object.getCollider().checkCollisionExit();
    }
  }

  /**
   * Tìm tất cả các vị trí lưới theo vị trí của object
   * 
   * @param object
   * @return list grid position
   */
  private static List<Vector2D> getGridPositions(GameObject object) {
    List<Vector2D> gridPs = new ArrayList<>();
    Vector2D topLeft = object.position.clone();

    topLeft.x -= topLeft.x % Sprite.DEFAULT_SIZE;
    topLeft.y -= topLeft.y % Sprite.DEFAULT_SIZE;

    gridPs.add(topLeft);

    var unionDim = Geometry.unionRect(topLeft, object.position).second;

    if (unionDim.x != 0) {
      Vector2D right = topLeft.add(new Vector2D(Sprite.DEFAULT_SIZE, 0));
      gridPs.add(right);
    }

    if (unionDim.y != 0) {
      Vector2D down = topLeft.add(new Vector2D(0, Sprite.DEFAULT_SIZE));
      gridPs.add(down);
    }

    if (unionDim.x != 0 && unionDim.y != 0) {
      Vector2D rightDown = topLeft.add(new Vector2D(Sprite.DEFAULT_SIZE, Sprite.DEFAULT_SIZE));
      gridPs.add(rightDown);
    }

    return gridPs;
  }

  private static void checkTwoColliders(Collider a, Collider b) {
    if (a == b || !a.isEnabled || !b.isEnabled) {
      return;
    }

    double overlapArea = a.computeOverlapArea(b);

    // Nếu danh sách va chạm của a chưa có b
    // hoặc vùng giao nhau của a và b bằng không
    // thì gọi onCollsionEnter
    boolean isEnter = !a.collidedList.containsKey(b) || a.collidedList.get(b).second == 0;

    // cập nhật diện tích giao nhau
    a.collidedList.put(b, Pair.of(true, overlapArea));
    b.collidedList.put(a, Pair.of(true, overlapArea));

    if (isEnter) {
      a.gameObject.onCollisionEnter(b);
      b.gameObject.onCollisionEnter(a);
    } else {
      // Nếu đã va chạm
      // thì gọi hàm onCollisionStay
      a.gameObject.onCollisionStay(b);
      b.gameObject.onCollisionStay(a);
    }
  }

  private void checkCollisionExit() {
    if (!this.isEnabled) {
      return;
    }

    List<Collider> exiters = new ArrayList<>();
    for (var key : this.collidedList.keySet()) {
      var value = this.collidedList.get(key);

      if (value.first == false) {
        exiters.add(key);
      } else {
        value.first = false;
      }
    }

    for (var collider : exiters) {
      this.gameObject.onCollisionExit(collider);
      collider.gameObject.onCollisionExit(this);

      this.collidedList.remove(collider);
      collider.collidedList.remove(this);
    }
  }
}