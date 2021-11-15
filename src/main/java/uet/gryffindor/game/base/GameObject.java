package uet.gryffindor.game.base;

import uet.gryffindor.game.Map;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.Texture;

/**
 * Lớp cơ bản của game. Mọi đối tượng của game nên kế thừa lớp này.
 */
public abstract class GameObject implements Comparable<GameObject> {
  private static Map map; // bản đồ chứa các game object
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

    // start();
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

  public abstract Texture getTexture();

  /**
   * Hủy game object.
   */
  public void destroy() {
    collider.setEnable(false);
    map.getObjects().remove(this);
  }

  @Override
  public int compareTo(GameObject that) {
    return this.orderedLayer.compareTo(that.orderedLayer);
  }

  public static void setMap(Map map) {
    GameObject.map = map;
  }

  public static void addObject(GameObject object) {
    map.getObjects().add(object);
  }
}
