package uet.gryffindor.game.object.dynamics;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.Manager;
import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Bomb extends DynamicObject {
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
        for (int j = 0; j < 4; j++) {
            for (int i = 1; i <= explosionRadius; i++) {
                Vector2D neighbor = Direction.valueOf(j).forward(this.position, Sprite.DEFAULT_SIZE * i);
                if (hasObstacle(neighbor)) {
                    break;
                }
                GameObject.instantiate(Explosion.class, neighbor);
            }
        }

        this.destroy();
    }

    public boolean hasObstacle(Vector2D position_) {
        Map myMap = Manager.INSTANCE.getGame().getPlayingMap();
        Wall obj1 = myMap.getObject(position_, Wall.class);
        Brick obj2 = myMap.getObject(position_, Brick.class);
        if (obj1 != null || (obj2 != null && !obj2.canDestroy)) {
            return true;
        }
        return false;
    }

    // public void setExploredRadius(int radius) {
    // this.explosionRadius = radius;
    // }

    // public int getExplosionRadius() {
    // return this.explosionRadius;
    // }
}
