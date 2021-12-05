package uet.gryffindor.game.map;

import java.util.ArrayList;
import java.util.List;

import uet.gryffindor.GameApplication;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Balloom;
import uet.gryffindor.game.object.dynamics.enemy.Oneal;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.game.object.statics.Portal;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class LargeDungeon extends Map {

    public LargeDungeon() {
        super(GameApplication.class.getResourceAsStream("map/dungeon2.txt"));
    }

    @Override
    protected List<GameObject> decode(String tokens) {
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