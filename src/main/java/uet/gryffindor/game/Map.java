package uet.gryffindor.game;

import java.util.List;

import uet.gryffindor.GameApplication;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.util.MapParser;
import uet.gryffindor.util.SortedList;

public class Map {
  private int[][] rawMap;
  private List<GameObject> objects;
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
    height = rawMap.length;
    width = rawMap[0].length;

  }

  public List<GameObject> getObjects() {
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

  public static Map getByLevel(int level) {
    level = 2;
    return MapParser.parse(GameApplication.class.getResourceAsStream("map/" + level + ".txt"));
  }
}
