package uet.gryffindor.game.movement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uet.gryffindor.decision.ActionNode;
import uet.gryffindor.decision.ConditionNode;
import uet.gryffindor.decision.DecisionTree;
import uet.gryffindor.decision.Node;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.dynamics.Bomb;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.game.object.dynamics.explosion.Explosion;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.util.Geometry;
import uet.gryffindor.util.Pair;

public class AutoPilot {
    private Bomber agent;
    private DynamicObject vision;
    private List<GameObject> objects = new ArrayList<>();
    private Queue<Vector2D> path = new LinkedList<>();
    private Action action = Action.Undefined;
    private DecisionTree tree;

    public AutoPilot(Bomber agent) {
        this.agent = agent;

        tree = new DecisionTree(
            new ConditionNode.Builder()
            .setConditionName("Is agent in a safety place?")
            .setPositive(new ActionNode<Action>().setAction(Action.GotoSafeZone))
            .setNegative(
                new ConditionNode.Builder()
                .setConditionName("Is the exit available?")
                .setPositive(new ActionNode<Action>().setAction(Action.GotoTheExit))
                .setNegative(
                    new ConditionNode.Builder()
                    .setConditionName("Is there any item in the vision?")
                    .setPositive(
                        new ConditionNode.Builder()
                        .setConditionName("Is there any enemy threatened agent?")
                        .setPositive(new ActionNode<Action>().setAction(Action.KillEnemy))
                        .setNegative(new ActionNode<Action>().setAction(Action.EarnItem))
                        .build()
                    ).setNegative(
                        new ConditionNode.Builder()
                        .setConditionName("is there any enemy in the vision?")
                        .setPositive(new ActionNode<Action>().setAction(Action.KillEnemy))
                        .setNegative(new ActionNode<Action>().setAction(Action.DestroyObstacle))
                        .build()
                    ).build()
                ).build()
            )
            .build()
        );

        vision = new DynamicObject() {
            private double visionRadius = 105;

            @Override
            public void start() {
                this.dimension = new Vector2D(visionRadius, visionRadius).multiply(2);
            }

            @Override
            public void update() {
                Vector2D center = agent.position.add(agent.dimension.multiply(0.5));
                this.position = center.subtract(this.dimension.multiply(0.5));
            }

            @Override
            public void onCollisionStay(Collider that) {
                objects.clear();
                if (!(that.gameObject instanceof Floor)) {
                    objects.add(that.gameObject);
                }
            }
        };

        GameObject.addObject(vision);
    }

    private Pair<MovableMap, GameObject> inputProcessing() {
        Vector2D pos = vision.position.smooth(Sprite.DEFAULT_SIZE, 1);
        Vector2D dim = vision.dimension.smooth(Sprite.DEFAULT_SIZE, 1);
        MovableMap map = new MovableMap(pos, dim);
        GameObject target = null; // mục tiêu để agent di chuyển đến

        for (GameObject obj : objects) {
            if (obj instanceof Bomb) {
                // nếu gặp bom thì đánh dấu là vùng không thể tới
                map.addObstacle(obj.position);
                int explosionRadius = ((Bomb) obj).getExplosionRadius();

                for (int i = 1; i <= explosionRadius; i++) {
                    map.addObstacles(
                        Direction.UP.forward(obj.position, i * Sprite.DEFAULT_SIZE),
                        Direction.DOWN.forward(obj.position, i * Sprite.DEFAULT_SIZE),
                        Direction.LEFT.forward(obj.position, i * Sprite.DEFAULT_SIZE),
                        Direction.RIGHT.forward(obj.position, i * Sprite.DEFAULT_SIZE)
                    );
                }
            } else if (obj instanceof Explosion) {
                // nếu gặp vụ nổ thì đánh dấu là vùng không thể tới
                map.addObstacle(obj.position);
            } else if (obj instanceof Item) {
                // chuyển target thành item
                target = obj;
            } else if (obj instanceof Enemy) {
                // nếu enemy đe dọa tới agent
                if (Geometry.manhattanDistance(obj.position, agent.position)
                    <= 2 * Sprite.DEFAULT_SIZE) {

                }
                if (target != null) {
                    map.addObstacle(obj.position);
                } else {
                    target = obj;
                }
            } else if (obj instanceof Brick && target == null) {
                target = obj;
            }
        }

        return Pair.of(map, target);
    }

    private void decision() {
        var data = inputProcessing();
        MovableMap map = data.first;
        GameObject target = data.second;

        if (target == null) {
            // nếu không có đích đến
            // kiểm tra xem agent có ở vị trí an toàn hay không
            // nếu vẫn an toàn thì di chuyển random
            // nếu không thì tới vị trí an toàn
            boolean isSafe = map.at(agent.position.smooth(Sprite.DEFAULT_SIZE, 1));

            if (isSafe) {
                action = Action.Undefined;
            } else {
                action = Action.GotoSafeZone;
            }
        } else if (target instanceof Enemy) {
            // đặt bom
            action = Action.PlantBomb;
        } else {
            path.clear();
            path = AStar.findPath(map, agent.position, target.position, agent.getSpeed());
            action = Action.FollowTarget;
        }
    }

    public void doAction() {
        decision();

        switch (action) {
            case Undefined:
                
            default:
                break;
        }
    }

    static enum Action {
        KillEnemy,
        EarnItem,
        DestroyObstacle,
        GotoSafeZone,
        GotoTheExit,
        Undefined
    }
}