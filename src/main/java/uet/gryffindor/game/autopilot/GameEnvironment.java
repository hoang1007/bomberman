package uet.gryffindor.game.autopilot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uet.gryffindor.game.Game;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.game.object.dynamics.Bomb;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.Pair;

public class GameEnvironment {
    private Game game;
    private Bomber agent;
    private List<Enemy> enemies = new ArrayList<>();
    private HashMap<Vector2D, GameObject> objectMatrix = new HashMap<>();

    public void initialize(Game game) {
        this.game = game;
        this.refesh();
    }

    public void refesh() {
        objectMatrix.clear();
        enemies.clear();
        for (GameObject object : game.getPlayingMap().getObjects()) {
            if (object instanceof Bomber) {
                agent = (Bomber) object;
            // } else if (object instanceof Enemy) {
            //     enemies.add((Enemy) object);
            } else {
                objectMatrix.put(object.position, object);
            }
        }
    }

    public List<Pair<Direction, GameObject>> getDangers() {
        List<Pair<Direction, GameObject>> dangers = new ArrayList<>();

        GameObject[] neighbors = getNeighbors();

        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] instanceof Bomb) {
                dangers.add(Pair.of(Direction.valueOf(i), neighbors[i]));
            } else if (neighbors[i] instanceof Enemy) {
                dangers.add(Pair.of(Direction.valueOf(i), neighbors[i]));
            } else if (neighbors[i] instanceof Unmovable) {
                dangers.add(Pair.of(Direction.valueOf(i), neighbors[i]));
            }
        }

        return dangers;
    }

    private GameObject[] getNeighbors() {
        GameObject[] neighbors = new GameObject[4];

        neighbors[Direction.UP.ordinal()] = objectMatrix.get(
            agent.position.subtract(new Vector2D(0, Sprite.DEFAULT_SIZE))
        );

        neighbors[Direction.DOWN.ordinal()] = objectMatrix.get(
            agent.position.add(new Vector2D(0, Sprite.DEFAULT_SIZE))
        );
        
        neighbors[Direction.LEFT.ordinal()] = objectMatrix.get(
            agent.position.subtract(new Vector2D(Sprite.DEFAULT_SIZE, 0))
        );

        neighbors[Direction.RIGHT.ordinal()] = objectMatrix.get(
            agent.position.add(new Vector2D(Sprite.DEFAULT_SIZE, 0))
        );

        return neighbors;
    }
}
