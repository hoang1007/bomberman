package uet.gryffindor.game.object.dynamics;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.scenes.MainSceneController;

public class Explosion extends DynamicObject {
    public static long time = 300;// thời gian xuất hiện vụ nổ

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
        if (that.gameObject instanceof Wall
                || that.gameObject instanceof Brick && !((Brick) (that.gameObject)).canDestroy) {
            this.destroy();
        }
    }
}
