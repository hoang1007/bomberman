package uet.gryffindor.game.map;

import java.util.ArrayList;
import java.util.List;

import uet.gryffindor.GameApplication;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Balloom;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.game.object.dynamics.enemy.Oneal;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class Frozen extends Map {
    public Frozen() {
        super(GameApplication.class.getResourceAsStream("map/frozen.txt"));
    }

    @Override
    protected List<GameObject> decode(String tokens) {
        List<GameObject> objects = new ArrayList<>();

        for (String token : tokens.split("-")) {
            char symbol = token.charAt(0);
            int id = Integer.parseInt(token.substring(1));

            switch (symbol) {
                case 'w':
                    Wall wall = new Wall();
                    wall.setTexture(new SpriteTexture(Sprite.iceMap[id], wall));
                    objects.add(wall);
                    break;
                case 'f':
                    Floor floor = new Floor();
                    floor.setTexture(new SpriteTexture(Sprite.iceMap[id], floor));
                    objects.add(floor);
                    break;
                case 'o':
                    Brick obstacle = new Brick();
                    obstacle.setTexture(new SpriteTexture(Sprite.iceMap[id], obstacle));
                    objects.addAll(List.of(obstacle, createBackground()));
                    break;
                case 'p':
                    objects.addAll(List.of(new Bomber(), createBackground()));
                    break;
                case 'e':
                    Enemy enemy;
                    if (id == 1) {
                        enemy = new Oneal();
                    } else {
                        enemy = new Balloom();
                    }

                    objects.addAll(List.of(enemy, createBackground()));
                    break;
                default:
                    break;
            }
        }

        return objects;
    }

    private GameObject createBackground() {
        final int defaultFloorId = 15;
        Floor bgr = new Floor();
        bgr.setTexture(new SpriteTexture(Sprite.iceMap[defaultFloorId], bgr));
        return bgr;
    }
}
