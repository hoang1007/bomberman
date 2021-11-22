package uet.gryffindor.util;

// import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
// import java.io.InputStreamReader;
import java.util.Scanner;

import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class MapParser {

  /**
   * Tạo map từ file config.
   * 
   * @param file file config
   * @return map
   */
  public static Map parse(InputStream config) {
    // try (BufferedReader reader = new BufferedReader(new
    // InputStreamReader(config))) {
    // String[] mapInfo = reader.readLine().split(" ");
    // int level = Integer.parseInt(mapInfo[0]);
    // int height = Integer.parseInt(mapInfo[1]);
    // int width = Integer.parseInt(mapInfo[2]);

    // char[][] rawMap = new char[height][width];
    // List<GameObject> objects = new ArrayList<>();

    // for (int i = 0; i < height; i++) {
    // rawMap[i] = reader.readLine().toCharArray();

    // for (int j = 0; j < width; j++) {
    // Vector2D position = new Vector2D(j, i);

    // GameObject object = decodeSymbol(rawMap[i][j]);

    // if (object != null) {
    // object.position = position.multiply(Sprite.DEFAULT_SIZE);
    // objects.add(object);
    // }
    // }
    // }
    try {
      String path = "src/main/resources/uet/gryffindor/map/1.txt";
      File map = new File(path);
      Scanner scanner = new Scanner(map);

      int level = scanner.nextInt();
      int height = scanner.nextInt();
      int width = scanner.nextInt();
      System.out.println("level " + level);
      System.out.println("height " + height);
      System.out.println("width " + width);

      while (scanner.hasNext()) {

        int[][] rawMap = new int[height][width];
        SortedList<GameObject> objects = new SortedList<>();

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            Vector2D position = new Vector2D(j, i);
            GameObject object = null;
            GameObject brick = null;

            String data = scanner.next();
            try {
              rawMap[i][j] = Integer.parseInt(data);
              object = decodeSymbol(rawMap[i][j]);
            } catch (NumberFormatException e) {
              String[] pair = data.split("-");
              brick = decodeSymbol(pair[0]);
              object = decodeSymbol(Integer.parseInt(pair[1]));
            }
            // System.out.print(rawMap[i][j] + " ");

            if (object != null) {
              object.position = position.multiply(Sprite.DEFAULT_SIZE);
              objects.add(object);
            }

            if (brick != null) {
              brick.position = position.multiply(Sprite.DEFAULT_SIZE);
              objects.add(brick);
            }

          }
          // System.out.println();
        }

        Bomber player = new Bomber();
        player.position = new Vector2D(1, 2).multiply(Sprite.DEFAULT_SIZE);
        objects.add(player);
        scanner.close();
        return new Map(rawMap, objects, level);
      }
      scanner.close();
      return null;
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
  public static GameObject decodeSymbol(Object s) {
    if (s instanceof Integer) {
      Integer symbol = (Integer) s;
      if (7 <= symbol && symbol <= 9) {
        Floor floor = new Floor();
        floor.setTexture(new SpriteTexture(Sprite.tiles[symbol], floor));
        return floor;
      } else if (0 <= symbol && symbol <= 27) {
        Wall wall = new Wall();
        wall.setTexture(new SpriteTexture(Sprite.tiles[symbol], wall));
        return wall;
      }

    } else if (s instanceof String) {
      String name = ((String) s).charAt(0) + "";

      if (name.equals("B")) {
        int index = Integer.parseInt(((String) s).charAt(1) + "");
        Brick block = new Brick();
        block.setTexture(new SpriteTexture(Sprite.obstacle[index], block));
        return block;
      } else if (name.equals("R")) {
        Brick rock = new Brick();
        rock.setTexture(new SpriteTexture(Sprite.obstacle[3], rock));
        return rock;
      }
    }
    return null;
  }
}
