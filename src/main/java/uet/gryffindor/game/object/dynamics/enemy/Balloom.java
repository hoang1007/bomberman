package uet.gryffindor.game.object.dynamics.enemy;

import java.util.Random;

import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.dynamics.Direction;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Balloom extends Enemy {
    private Direction direction = Direction.UP;
    private double speed = 2.0;
    private Random random = new Random();

    @Override
    public void start() {
        this.texture = new AnimateTexture(this, 6, Sprite.balloom);
    }

    @Override
    public void update() {
        move();
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
}
