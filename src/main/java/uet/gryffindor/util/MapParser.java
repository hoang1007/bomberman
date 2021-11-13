package uet.gryffindor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Balloom;
import uet.gryffindor.game.object.dynamics.enemy.Oneal;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Grass;
import uet.gryffindor.game.object.statics.Portal;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.game.object.statics.items.BombItem;
import uet.gryffindor.game.object.statics.items.FlameItem;
import uet.gryffindor.game.object.statics.items.SpeedItem;
import uet.gryffindor.graphic.sprite.Sprite;

public class MapParser {

  /**
   * Tạo map từ file config.
   * 
   * @param file file config
   * @return map
   */
  public static Map parse(InputStream config) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(config))) {
      String[] mapInfo = reader.readLine().split(" ");
      int level = Integer.parseInt(mapInfo[0]);
      int height = Integer.parseInt(mapInfo[1]);
      int width = Integer.parseInt(mapInfo[2]);

      char[][] rawMap = new char[height][width];
      List<GameObject> objects = new ArrayList<>();

      for (int i = 0; i < height; i++) {
        rawMap[i] = reader.readLine().toCharArray();

        for (int j = 0; j < width; j++) {
          Vector2D position = new Vector2D(j, i);

          GameObject object = decodeSymbol(rawMap[i][j]);
          object.position = position.multiply(Sprite.DEFAULT_SIZE);

          objects.add(object);
        }
      }

      return new Map(rawMap, objects, level);
    } catch (IOException e) {
      System.out.println("Error while create map from config file: " + e.getMessage());
      return null;
    }
  }

  /**
   * Giải mã game object.
   * 
   * @param symbol kí tự mã hóa
   * @return game object bị mã hóa
   */
  public static GameObject decodeSymbol(Character symbol) {
    switch (symbol) {
    case '#':
      return new Wall();
    case '*':
      return new Brick();
    case 'x':
      return new Portal();
    case 'p':
      return new Bomber();
    case '1':
      return new Balloom();
    case '2':
      return new Oneal();
    case 'b':
      return new BombItem();
    case 'f':
      return new FlameItem();
    case 's':
      return new SpeedItem();
    default:
      return new Grass();
    }
  }
}
