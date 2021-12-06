package uet.gryffindor.game.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.sprite.Sprite;
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
    double xleft = Math.max(topLeft1.x, topLeft2.x);
    double xright = Math.min(botRight1.x, botRight2.x);
    double ytop = Math.max(topLeft1.y, topLeft2.y);
    double ybottom = Math.min(botRight1.y, botRight2.y);

    if (xleft < xright && ytop < ybottom) {
      return (xright - xleft) * (ybottom - ytop);
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
   * Tìm tất cả các ô mà game object nằm trên đó.
   * 
   * @param object game object
   * @return list grid position
   */
  private static List<Vector2D> getGridPositions(GameObject obj) {
    List<Vector2D> gridPs = new ArrayList<>();
    Vector2D topLeft = obj.position.clone();
    Vector2D bottomRight = obj.position.add(obj.dimension);
    // Tìm grid pos của top left và bottom right
    // duyệt từng ô trong khoảng hai đầu mút
    // rồi thêm vào danh sách
    topLeft.x -= topLeft.x % Sprite.DEFAULT_SIZE;
    topLeft.y -= topLeft.y % Sprite.DEFAULT_SIZE;
    // bottomRight.x -= bottomRight.x % Sprite.DEFAULT_SIZE;
    // bottomRight.y -= bottomRight.y % Sprite.DEFAULT_SIZE;

    for (int i = (int) topLeft.x; i < bottomRight.x; i += Sprite.DEFAULT_SIZE) {
      for (int j = (int) topLeft.y; j < bottomRight.y; j += Sprite.DEFAULT_SIZE) {
        gridPs.add(new Vector2D(i, j));
      }
    }

    return gridPs;
  }

  private static void checkTwoColliders(Collider a, Collider b) {
    if (a == b || !a.isEnabled || !b.isEnabled) {
      return;
    }

    var pair = a.collidedList.get(b);

    // nếu danh sách va chạm của a đã cập nhật b
    // bỏ qua kiểm tra lặp
    if (pair != null && pair.first == true) {
      return;
    }

    // Nếu danh sách va chạm của a chưa có b
    // hoặc vùng giao nhau của a và b bằng không
    // thì gọi onCollsionEnter
    boolean isEnter = pair == null || pair.second == 0;
    double overlapArea = a.computeOverlapArea(b);

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

      // nếu không được cập nhật 
      // thì hai object không còn va chạm
      if (value.first == false) {
        exiters.add(key);
      } else {
        // đặt dirty bit thành false
        // cho lần cập nhật tới
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