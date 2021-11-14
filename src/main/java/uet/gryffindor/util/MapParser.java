package uet.gryffindor.util;

// import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
// import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Balloom;
import uet.gryffindor.game.object.dynamics.enemy.Oneal;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Portal;
import uet.gryffindor.game.object.statics.children.floor.StoneFloor;
import uet.gryffindor.game.object.statics.children.wall.Tiles;
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
        List<GameObject> objects = new ArrayList<>();

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            rawMap[i][j] = scanner.nextInt();
            System.out.print(rawMap[i][j] + " ");
            Vector2D position = new Vector2D(j, i);
            GameObject object = decodeSymbol(rawMap[i][j]);

            if (object != null) {
              object.start();
              object.position = position.multiply(Sprite.DEFAULT_SIZE);
              objects.add(object);
            }

          }
          System.out.println();
        }

        Bomber player = new Bomber();
        player.start();
        objects.add(player);

        return new Map(rawMap, objects, level);
      }
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
  public static GameObject decodeSymbol(Integer symbol) {
    switch (symbol) {
    case 1: {
      return new Tiles(1);
    }
    case 2: {
      return new Tiles(2);
    }
    case 4: {
      return new Tiles(4);
    }
    case 6: {
      return new Tiles(6);
    }
    case 7: {
      return new Tiles(7);
    }
    case 9: {
      return new Tiles(9);
    }
    case 10: {
      return new Tiles(10);
    }
    case 11: {
      return new StoneFloor();
    }
    default:
      return null;
    }
  }
}
