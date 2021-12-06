package uet.gryffindor.game.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.map.Map;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.Texture;

/** Lớp cơ bản của game. Mọi đối tượng của game nên kế thừa lớp này. */
public abstract class GameObject implements Comparable<GameObject> {
  private static Map map; // bản đồ chứa các game object
  public Vector2D position; // Vị trí của đối tượng.
  public Vector2D dimension; // Kích thước của object

  protected Collider collider; // phát hiện va chạm
  protected OrderedLayer orderedLayer; // thứ tự render

  /** Khởi tạo mặc định của object. */
  protected GameObject() {
    position = Vector2D.zero();
    dimension = new Vector2D(Sprite.DEFAULT_SIZE, Sprite.DEFAULT_SIZE);
    orderedLayer = OrderedLayer.BACKGROUND;
    collider = new Collider(this);
  }

  /**
   * Khởi tạo game object.
   *
   * @param clazz class của game object
   */
  public static GameObject instantiate(Class<? extends GameObject> clazz) {
    try {
      Constructor<? extends GameObject> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      GameObject obj = constructor.newInstance();

      GameObject.addObject(obj);

      return obj;
    } catch (InstantiationException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException
        | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Khởi tạo game object.
   *
   * @param clazz    class của game object
   * @param position vị trí của game object
   */
  public static GameObject instantiate(Class<? extends GameObject> clazz, Vector2D position) {
    try {
      Constructor<? extends GameObject> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      GameObject obj = constructor.newInstance();
      obj.position = position;

      GameObject.addObject(obj);
      return obj;
    } catch (InstantiationException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException
        | NoSuchMethodException
        | SecurityException e) {
      e.printStackTrace();
      return null;
    }
  }

  /** Khởi tạo các thuộc tính (thay thế cho contructor). */
  public abstract void start();

  /** Cập nhật mỗi frame. */
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

  public Texture getTexture() {
    return null;
  }

  public Map getMap() {
    return GameObject.map;
  }

  /** Hủy game object. */
  public void destroy() {
    map.getObjects().remove(this);
  }

  public Collider getCollider() {
    return this.collider;
  }

  @Override
  public int compareTo(GameObject that) {
    return this.orderedLayer.compareTo(that.orderedLayer);
  }

  public static void setMap(Map map) {
    GameObject.map = map;
  }

  public static void addObject(GameObject object) {
    object.start();
    map.getObjects().add(object);
  }

  public void setOrder(OrderedLayer order) {
    this.orderedLayer = order;
  }
}
