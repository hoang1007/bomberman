package uet.gryffindor.game.base;

import java.util.List;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.Texture;
import uet.gryffindor.util.SortedList;

/**
 * Lớp cơ bản của game. Mọi đối tượng của game nên kế thừa lớp này.
 */
public abstract class GameObject implements Comparable<GameObject> {
  public static final List<GameObject> objects = new SortedList<>();

  public Vector2D position; // Vị trí của đối tượng.
  public Vector2D dimension; // Kích thước của object

  protected Collider collider; // phát hiện va chạm
  protected OrderedLayer orderedLayer; // thứ tự render

  /** Khởi tạo mặc định của object. */
  public GameObject() {
    position = Vector2D.zero();
    dimension = new Vector2D(Sprite.DEFAULT_SIZE, Sprite.DEFAULT_SIZE);
    orderedLayer = OrderedLayer.BACKGROUND;
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
   * Texture để render mỗi frame.
   */
  public abstract Texture getTexture();

  /**
   * Hàm được gọi khi hai {@link Collider} bắt đầu va chạm.
   * 
   * @param that collider bị va chạm
   */
  public void onCollisionEnter(Collider that) {
  }

  /**
   * Hàm được gọi mỗi frame khi hai {@link Collider} chồng lên nhau.
   * 
   * @param that collider bị chồng.
   */
  public void onCollisionStay(Collider that) {
  }

  /**
   * Hàm được gọi khi hai {@link Collider} rời khỏi va chạm.
   * 
   * @param that collider bị va chạm
   */
  public void onCollisionExit(Collider that) {
  }

  /**
   * Hủy game object.
   */
  public void destroy() {
    GameObject.objects.remove(this);
    collider.setEnable(false);
  }

  @Override
  public int compareTo(GameObject that) {
    return this.orderedLayer.compareTo(that.orderedLayer);
  }

  public static void clear() {
    for (int i = GameObject.objects.size() - 1; !GameObject.objects.isEmpty(); i--) {
      GameObject.objects.get(i).collider.setEnable(false);
      GameObject.objects.remove(i);
    }
  }
}
