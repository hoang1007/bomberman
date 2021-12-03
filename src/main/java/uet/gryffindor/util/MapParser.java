package uet.gryffindor.util;

import java.io.InputStream;
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
import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.game.object.statics.Portal;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class MapParser {
    public static Map parse(InputStream config) {
        try (Scanner sc = new Scanner(config)) {
            int level = sc.nextInt();
            int height = sc.nextInt();
            int width = sc.nextInt();

            String[][] rawMap = new String[height][width];
            SortedList<GameObject> objects = new SortedList<>();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Vector2D position = new Vector2D(j, i);

                    String tokens = sc.next();
                    rawMap[i][j] = tokens;

                    for (GameObject obj : decodeTokens(tokens)) {
                        obj.position = position.multiply(Sprite.DEFAULT_SIZE);
                        objects.add(obj);
                    }
                }
            }

            return new Map(rawMap, objects, level);
        }
    }

    private static List<GameObject> decodeTokens(String tokens) {
        List<GameObject> objects = new ArrayList<>();
        for (String token : tokens.split("-")) {
            char symbol = token.charAt(0);
            int type = 0;
            try {
                type = Integer.parseInt(token.substring(1));

            } catch (NumberFormatException e) {
                //
            }

            switch (symbol) {
                case 'w':
                    Wall wall = new Wall();
                    wall.setTexture(new SpriteTexture(Sprite.tiles[type], wall));
                    objects.add(wall);
                    break;
                case 'o':
                    Brick obstacle = new Brick();
                    if (type == 3) {
                        obstacle.canDestroy = false;
                    }
                    obstacle.setTexture(new SpriteTexture(Sprite.obstacle[type], obstacle));
                    objects.add(obstacle);
                    break;
                case 'f':
                    Floor floor = new Floor();
                    floor.setTexture(new SpriteTexture(Sprite.tiles[type], floor));
                    objects.add(floor);
                    break;
                case 'P':
                    Portal portal = new Portal();
                    portal.setTexture(new SpriteTexture(Sprite.portal[0], portal));
                    objects.add(portal);
                    break;
                case 'p':
                    Bomber bomber = new Bomber();
                    objects.add(bomber);
                    break;
                case 'e':
                    objects.add(type == 0 ? new Balloom() : new Oneal());
                    break;
                default:
                    break;
            }
        }

        return objects;
    }
}
