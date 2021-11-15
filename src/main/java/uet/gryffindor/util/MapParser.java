package uet.gryffindor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamic.Bomber;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class MapParser {

  /**
   * Tạo map từ file config.
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
      SortedList<GameObject> objects = new SortedList<>();

      for (int i = 0; i < height; i++) {
        rawMap[i] = reader.readLine().toCharArray();
        
        for (int j = 0; j < width; j++) {
          Vector2D position = new Vector2D(j, i);

          GameObject object = decodeSymbol(rawMap[i][j]);
          if (object != null) {
            object.position = position.multiply(Sprite.DEFAULT_SIZE);

            objects.add(object);
          }
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
   * @param symbol kí tự mã hóa
   * @return game object bị mã hóa
   */
  public static GameObject decodeSymbol(Character symbol) {
    switch (symbol) {
      case 'p':
        Bomber bomber = new Bomber();
        bomber.setTexture(new AnimateTexture(bomber, 3, Sprite.player));
        return bomber;
      default:
        return null;
    }
  }
}
