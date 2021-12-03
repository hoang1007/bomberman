package uet.gryffindor.game.object.dynamics;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Bomb extends DynamicObject implements Unmovable {
    public static long time = 2000; // giới hạn thời gian
    public static int explosionRadius = 1; // bán kính vụ nổ

    @Override
    public void start() {
        HashMap<String, Sprite[]> anim = new HashMap<>();
        anim.put("anim", Sprite.bomb);
        texture = new AnimateTexture(this, 2, anim);

        orderedLayer = OrderedLayer.MIDGROUND;

        TimeCounter.callAfter(this::explore, time, TimeUnit.MILLISECONDS);
    }

    @Override
    public void update() {

    }

    /** hiệu ứng nổ. */
    public void explore() {
        // thêm vụ nổ ở trung tâm.
        GameObject.instantiate(Explosion.class, this.position);
        // thêm vụ nổ các hướng
        for (int i = 1; i <= explosionRadius; i++) {
            for (int j = 0; j < 4; j++) {
                Vector2D neighbor = Direction.valueOf(j).forward(this.position, Sprite.DEFAULT_SIZE * i);
                GameObject.instantiate(Explosion.class, neighbor);
            }
        }

        this.destroy();
    }

    // public void setExploredRadius(int radius) {
    // this.explosionRadius = radius;
    // }

    // public int getExplosionRadius() {
    // return this.explosionRadius;
    // }
}
