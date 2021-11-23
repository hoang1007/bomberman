package uet.gryffindor.game.object.dynamics.enemy;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.Direction;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Oneal extends Enemy {
    private int attackRadius = 105;
    private Direction direction = Direction.UP;
    private double speed = 3.0;
    private Random random = new Random();
    private Stack<Direction> traceBack = new Stack<>();

    @Override
    public void start() {
        this.texture = new AnimateTexture(this, 5, Sprite.oneal);
        this.texture.changeTo("up");

        GameObject.addObject(new GameObject() {

            @Override
            public void start() {
                this.position = Oneal.this.position;
                // d = 2*r + 1
                this.collider.setDimension(new Vector2D(attackRadius, attackRadius).add(this.dimension).multiply(2));
            }

            @Override
            public void update() {

            }

            @Override
            public void onCollisionEnter(Collider that) {
                
            }

            @Override
            public void onCollisionStay(Collider that) {
                if (that.gameObject instanceof Bomber) {
                    System.out.println("In range attack");
                    
                    if (traceBack.isEmpty()) {
                        traceBack = findPath(that.gameObject.position);
                    }
                }
            }

            @Override
            public void onCollisionExit(Collider that) {
                if (that.gameObject instanceof Bomber) {
                    System.out.println("Bomber escaped");
                    traceBack.empty();
                }
            }
        });
    }

    @Override
    public void update() {
        // Nếu đang đuổi theo mục tiêu (stack không rỗng)
        // thì di chuyển theo path có trong stack
        // nếu không thì di chuyển random
        if (!traceBack.isEmpty()) {
            direction = traceBack.pop();
            move();
        }
    }

    @Override
    public void onCollisionEnter(Collider that) {
        if (that.gameObject instanceof Unmovable) {
            this.position.smooth(Sprite.DEFAULT_SIZE, 1);

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

    private Stack<Direction> findPath(Vector2D dstPosition) {
        Stack<Direction> path = new Stack<>();
        Queue<MoveStep> queue = new PriorityQueue<>();
        Map<Vector2D, MoveStep> evaluated = new HashMap<>();

        Vector2D srcPosition = this.position.clone().smooth(Sprite.DEFAULT_SIZE, 1);
        dstPosition = dstPosition.clone().smooth(Sprite.DEFAULT_SIZE, 1);

        queue.add(this.new MoveStep(srcPosition, null, null, Double.MAX_VALUE));

        while (!queue.isEmpty()) {
            MoveStep current = queue.remove();
            evaluated.put(current.position, current);

            if (Vector2D.manhattanDistance(current.position, dstPosition) < 5) {
                dstPosition = current.position;
                break;
            }

            MoveStep[] neighbors = findNeighbors(current, srcPosition, dstPosition);

            for (MoveStep neighbor : neighbors) {
                Unmovable obj = this.getMap().getObject(neighbor.position, Unmovable.class);
                
                if (obj != null) {
                    System.out.println(obj);
                    continue;
                }

                if (!evaluated.containsKey(neighbor.position)) {
                    queue.add(neighbor);
                }
            }
        }

        MoveStep traceBack = evaluated.get(dstPosition);

        while (traceBack.preStep.direction != null) {
            path.push(traceBack.direction);
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
                this.position.y -= speed;
                break;
            case DOWN:
                this.position.y += speed;
                break;
            case LEFT:
                this.position.x -= speed;
                break;
            case RIGHT:
                this.position.x += speed;
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
    }
}