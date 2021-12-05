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
  private int score;
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

  public int getScore() {
    return this.score;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  /**
   * Tìm object với loại mong muốn theo position.
   * 
   * @param position
   * @param type     class của object
   * @return object
   */
  public <T> T getObject(Vector2D position, Class<T> type) {
    for (GameObject object : this.objects) {
      if (type.isInstance(object) && object.position.equals(position)) {
        return type.cast(object);
      }
    }

    return null;
  }
}
