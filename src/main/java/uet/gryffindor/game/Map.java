package uet.gryffindor.game;

import uet.gryffindor.GameApplication;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.MapParser;
import uet.gryffindor.util.SortedList;

public class Map {
  private int[][] rawMap;
  private SortedList<GameObject> objects;
  private int level;
  private int score;
  private int height;
  private int width;

  /**
   * Màn chơi của game.
   * 
   * @param rawMap  bản đồ thô sơ
   * @param objects các game object có trong map
   * @param level   level của map
   */
  public Map(int[][] rawMap, SortedList<GameObject> objects, int level) {
    this.rawMap = rawMap;
    this.objects = objects;
    this.level = level;
    this.score = 0;
    height = rawMap.length * Sprite.DEFAULT_SIZE;
    width = rawMap[0].length * Sprite.DEFAULT_SIZE;

  }

  public SortedList<GameObject> getObjects() {
    return this.objects;
  }

  public int[][] getRawMap() {
    return this.rawMap;
  }

  public int getRawMapAt(int x, int y) {
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
   * @param position
   * @param type class của object
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

  public static Map getByLevel(int level) {
    level = 2;
    return MapParser.parse(GameApplication.class.getResourceAsStream("map/" + level + ".txt"));
  }
}
