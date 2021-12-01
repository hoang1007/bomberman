package uet.gryffindor.game.movement;

import uet.gryffindor.game.base.Vector2D;

public enum Direction {
    UP("up"), DOWN("down"), LEFT("left"), RIGHT("right"), NONE("none");

    private String name;
    private Direction(String name) {
        this.name = name;
    }

    public static Direction valueOf(int ordinal) {
        switch (ordinal) {
            case 0:
                return UP;
            case 1:
                return DOWN;
            case 2:
                return LEFT;
            case 3:
                return RIGHT;
            default:
                return NONE;
        }
    }

    public Vector2D forward(Vector2D position, double step) {
        Vector2D result = position.clone();

        switch (this) {
            case UP:
                result.y -= step;
                break;
            case DOWN:
                result.y += step;
                break;
            case LEFT:
                result.x -= step;
                break;
            case RIGHT:
                result.x += step;
                break;
            default:
                break;
        }

        return result;
    }

    public static Direction of(Vector2D src, Vector2D dst) {
        if (src.x == dst.x) {
            if (src.y < dst.y) {
                return DOWN;
            } else if (src.y > dst.y) {
                return UP;
            }
        } else if (src.y == dst.y) {
            if (src.x < dst.x) {
                return RIGHT;
            } else if (src.x > dst.x) {
                return LEFT;
            }
        }

        return NONE;
    }

    public String toString() {
        return this.name;
    }
}
