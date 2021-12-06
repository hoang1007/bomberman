package uet.gryffindor.game.map;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.SortedList;

public abstract class Map {
  private String[][] rawMap;
  private SortedList<GameObject> objects;
  private int level;
  private int height;
  private int width;

  protected Map(InputStream config) {
    try (Scanner sc = new Scanner(config)) {
      level = sc.nextInt();
      height = sc.nextInt();
      width = sc.nextInt();

      rawMap = new String[height][width];
      objects = new SortedList<>();

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Vector2D position = new Vector2D(j, i);

          String tokens = sc.next();
          rawMap[i][j] = tokens;

          for (GameObject obj : decode(tokens)) {
            obj.position = position.multiply(Sprite.DEFAULT_SIZE);
            objects.add(obj);
          }
        }
      }
    }
  }

  /**
   * Khởi tạo tất cả các game object có trong map.
   */
  public void init() {
    // Gọi phương thức khởi tạo thuộc tính
    // Bao gồm cả trường hợp kích thước của list thay đổi
    for (int i = 0; i < objects.size(); i++) {
      int oldSize = objects.size();
      objects.get(i).start();
      int curSize = objects.size();

      if (curSize > oldSize) {
        var obj = objects.getLastElement();
        if (obj.first <= i) {
          i += 1;
        }
      }
    }

    objects.sort();
  }

  protected abstract List<GameObject> decode(String tokens);

  public void setLevel(int level) {
    this.level = level;
  }

  public SortedList<GameObject> getObjects() {
    return this.objects;
  }

  public String[][] getRawMap() {
    return this.rawMap;
  }

  public String getRawMapAt(int x, int y) {
    return rawMap[x][y];
  }

  public int getLevel() {
    return this.level;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  /**
   * Tìm object.
   * 
   * @param position vị trí của object
   * @param type     class của object
   * @return object. Null nếu không có tại vị trí đã chỉ định
   */
  public <T> T getObject(Vector2D position, Class<T> type) {
    for (GameObject object : this.objects) {
      if (type.isInstance(object) && object.position.equals(position)) {
        return type.cast(object);
      }
    }

    return null;
  }

  /**
   * Tìm object loại type đầu tiên có trong map.
   * @param <T> kiểu dữ liệu của object
   * @param type class của object
   * @return object nếu có trong map. Null nếu không có.
   */
  public <T extends GameObject> T getObject(Class<T> type) {
    for (GameObject obj : this.objects) {
      if (type.isInstance(obj)) {
        return type.cast(obj);
      }
    }

    return null;
  }
}
