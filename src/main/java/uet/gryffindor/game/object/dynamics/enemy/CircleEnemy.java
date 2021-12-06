package uet.gryffindor.game.object.dynamics.enemy;

import java.util.Random;

import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.game.object.dynamics.Explosion;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.scenes.MainSceneController;
import uet.gryffindor.sound.SoundController;

public class CircleEnemy extends Enemy {
    private Direction direction = Direction.UP;
    private double speed = 2.0;
    private Random random = new Random();

    @Override
    public void start() {
        this.texture = new AnimateTexture(this, 6, Sprite.circleEnemy);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void onCollisionEnter(Collider that) {
        if (that.gameObject instanceof Unmovable || that.gameObject instanceof Magma) {
            this.position = this.position.smooth(Sprite.DEFAULT_SIZE, 1);

            int dirCode = 0;
            do {
                dirCode = random.nextInt(4);
            } while (dirCode == direction.ordinal());

            direction = Direction.valueOf(dirCode);
        } else if (that.gameObject instanceof Explosion) {
            MainSceneController.score += 5;
            SoundController.INSTANCE.getSound(SoundController.ENEMY_DIE).play(); // âm thanh khi enemy chết.
            this.destroy();
        }
    }

    private void move() {
        position = direction.forward(position, speed);
        texture.changeTo(direction.toString());
    }
}
