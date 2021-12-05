package uet.gryffindor.game.object.dynamics;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Explosion extends DynamicObject {
    public static long time = 300;// thời gian xuất hiện vụ nổ

    private Explosion nextExplosion;

    @Override
    public void start() {
        HashMap<String, Sprite[]> anim = new HashMap<>();
        anim.put("anim", Sprite.explosion);
        texture = new AnimateTexture(this, 1, anim);

        TimeCounter.callAfter(this::destroy, time, TimeUnit.MILLISECONDS);
    }

    @Override
    public void update() {

    }

    @Override
    public void onCollisionEnter(Collider that) {
        if (that.gameObject instanceof Unmovable) {
            for (var obj = this.nextExplosion; obj != null; obj = obj.nextExplosion) {
                obj.destroy();
            }

            if (that.gameObject instanceof Wall) {
                this.destroy();
            }
        }
    }

    public void setNextExplosion(Explosion nextExplosion) {
        this.nextExplosion = nextExplosion;
    }
}
