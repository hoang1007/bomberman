package uet.gryffindor.game.autopilot;

import java.util.ArrayList;
import java.util.List;

import uet.gryffindor.game.movement.Direction;

public class AutoPilot {
    public static Direction getAction(GameEnvironment env) {
        env.refesh();
        var dangers = env.getDangers();

        if (dangers.isEmpty()) {
            return Direction.RIGHT;
        } else {
            List<Direction> directions = new ArrayList<>();
            directions.add(Direction.RIGHT);
            directions.add(Direction.DOWN);
            directions.add(Direction.UP);
            directions.add(Direction.LEFT);

            for (var danger : dangers) {
                directions.remove(danger.first);
            }

            return directions.isEmpty() ? Direction.RIGHT : directions.get(0);
        }
    }
}