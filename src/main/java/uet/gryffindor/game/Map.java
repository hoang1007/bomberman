package uet.gryffindor.game;

import java.util.List;

import uet.gryffindor.GameApplication;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.util.MapParser;

public class Map {
  private char[][] rawMap;
  private List<GameObject> objects;
  private int level;
  private int score;

  /**
   * Màn chơi của game.
   * 
   * @param rawMap  bản đồ thô sơ
   * @param objects các game object có trong map
   * @param level   level của map
   */
  public Map(char[][] rawMap, List<GameObject> objects, int level) {
    this.rawMap = rawMap;
    this.objects = objects;
    this.level = level;
    this.score = 0;
  }

  public List<GameObject> getObjects() {
    return this.objects;
  }

  public char[][] getRawMap() {
    return this.rawMap;
  }

  public char getRawMapAt(int x, int y) {
    return rawMap[x][y];
  }

  public int getLevel() {
    return this.level;
  }

  public int getScore() {
    return this.score;
  }

  public static Map getByLevel(int level) {
    return MapParser.parse(GameApplication.class.getResourceAsStream("map/" + level + ".txt"));
  }
}
