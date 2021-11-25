package uet.gryffindor.game.object.dynamics.enemy;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javafx.scene.paint.Color;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.Direction;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.graphic.texture.OutlineTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Oneal extends Enemy {
    private int attackRadius = 50;
    private Direction direction = Direction.UP;
    private double speed = 3.0;
    private Random random = new Random();
    private Stack<MoveStep> traceBack = new Stack<>();
    private Vector2D oldPosition;

    @Override
    public void start() {
        this.texture = new AnimateTexture(this, 5, Sprite.oneal);

        // // Object giúp phát hiện bomber có vào vùng tấn công hay không
        // GameObject.addObject(new GameObject() {
        //     private OutlineTexture texture;

        //     @Override
        //     public void start() {
        //         this.texture = new OutlineTexture(this, Color.RED);
        //         this.orderedLayer = OrderedLayer.FOREGROUND;
        //         this.position = Oneal.this.position;
        //         // d = 2*r + 1
        //         this.dimension = new Vector2D(attackRadius, attackRadius).add(this.dimension).multiply(2);
        //     }

        //     @Override
        //     public void update() {
        //         Vector2D center = Oneal.this.position.add(Oneal.this.dimension.multiply(0.5));
        //         this.position = center.subtract(this.dimension.multiply(0.5));
        //     }

        //     @Override
        //     public void onCollisionStay(Collider that) {
        //         if (that.gameObject instanceof Bomber) {
        //             if (traceBack.isEmpty()) {
        //                 speed = 5.0;
        //                 traceBack = findPath(that.gameObject.position);
        //                 System.out.println(traceBack);
        //             }
        //         }
        //     }

        //     @Override
        //     public void onCollisionExit(Collider that) {
        //         if (that.gameObject instanceof Bomber) {
        //             traceBack.clear();
        //             speed = 3.0;
        //         }
        //     }

        //     @Override
        //     public Texture getTexture() {
        //         return this.texture;
        //     }
        // });
    }

    @Override
    public void update() {
        // Nếu đang đuổi theo mục tiêu (stack không rỗng)
        // thì di chuyển theo path có trong stack
        // nếu không thì di chuyển random
        if (!traceBack.isEmpty()) {
            direction = traceBack.peek().direction;
            double dis = Vector2D.euclideanDistance(traceBack.peek().position, this.position);

            if (dis < speed) {
                this.position = traceBack.pop().position;
            }
        }

        oldPosition = position.clone();
        move();
    }

    @Override
    public void onCollisionEnter(Collider that) {
        if (that.gameObject instanceof Unmovable) {
            position = oldPosition.smooth(Sprite.DEFAULT_SIZE, 1);
            traceBack.clear();

            int dirCode = 0;
            do {
                dirCode = random.nextInt(4);
            } while (dirCode == direction.ordinal());

            direction = Direction.valueOf(dirCode);
        }
    }

    private void move() {
        switch (direction) {
        case UP:
            this.position.y -= speed;
            this.texture.changeTo("up");
            break;
        case DOWN:
            this.position.y += speed;
            this.texture.changeTo("down");
            break;
        case LEFT:
            this.position.x -= speed;
            this.texture.changeTo("left");
            break;
        case RIGHT:
            this.position.x += speed;
            this.texture.changeTo("right");
            break;
        default:
            break;
        }
    }

    private Stack<MoveStep> findPath(Vector2D dstPosition) {
        Stack<MoveStep> path = new Stack<>();
        Queue<MoveStep> queue = new PriorityQueue<>();
        Map<Vector2D, MoveStep> evaluated = new HashMap<>();

        Vector2D srcPosition = this.position.clone().smooth(Sprite.DEFAULT_SIZE, 1);
        dstPosition = dstPosition.clone().smooth(Sprite.DEFAULT_SIZE, 1);

        queue.add(this.new MoveStep(srcPosition, null, null, Double.MAX_VALUE));

        while (!queue.isEmpty()) {
            MoveStep current = queue.remove();
            evaluated.put(current.position, current);

            if (current.position.equals(dstPosition)) {
                break;
            }

            MoveStep[] neighbors = findNeighbors(current, srcPosition, dstPosition);

            for (MoveStep neighbor : neighbors) {
                if (this.getMap().getObject(neighbor.position, Unmovable.class) != null) {
                    continue;
                }

                if (!evaluated.containsKey(neighbor.position)) {
                    queue.add(neighbor);
                }
            }
        }

        MoveStep traceBack = evaluated.get(dstPosition);

        while (traceBack.preStep.direction != null) {
            path.push(traceBack);
            traceBack = traceBack.preStep;
        }

        return path;
    }

    private MoveStep[] findNeighbors(MoveStep curStep, Vector2D src, Vector2D dst) {
        MoveStep[] ms = new MoveStep[4];

        ms[Direction.UP.ordinal()] = this.new MoveStep(curStep, Direction.UP, dst);
        ms[Direction.DOWN.ordinal()] = this.new MoveStep(curStep, Direction.DOWN, dst);
        ms[Direction.LEFT.ordinal()] = this.new MoveStep(curStep, Direction.LEFT, dst);
        ms[Direction.RIGHT.ordinal()] = this.new MoveStep(curStep, Direction.RIGHT, dst);

        return ms;
    }

    private class MoveStep implements Comparable<MoveStep> {
        public Vector2D position;
        public Direction direction;
        public MoveStep preStep;
        private double hCost;
        private double gCost;
        private double cost;

        public MoveStep(MoveStep preStep, Direction direction, Vector2D dstPos) {
            this.position = preStep.position.clone();
            switch (direction) {
            case UP:
                this.position.y -= Sprite.DEFAULT_SIZE;
                break;
            case DOWN:
                this.position.y += Sprite.DEFAULT_SIZE;
                break;
            case LEFT:
                this.position.x -= Sprite.DEFAULT_SIZE;
                break;
            case RIGHT:
                this.position.x += Sprite.DEFAULT_SIZE;
                break;
            }

            this.direction = direction;
            this.preStep = preStep;
            this.hCost = preStep.hCost + Vector2D.manhattanDistance(position, preStep.position);
            this.gCost = Vector2D.manhattanDistance(position, dstPos);

            this.cost = this.hCost + this.gCost;
        }

        public MoveStep(Vector2D position, Direction direction, MoveStep preStep, double cost) {
            this.position = position;
            this.direction = direction;
            this.preStep = preStep;
            this.cost = cost;
        }

        @Override
        public int compareTo(MoveStep o) {
            return Double.compare(this.cost, o.cost);
        }

        @Override
        public String toString() {
            return this.direction.toString();
        }
    }
}