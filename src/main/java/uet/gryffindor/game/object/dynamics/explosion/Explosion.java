package uet.gryffindor.game.object.dynamics.explosion;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Explosion extends DynamicObject {
    public static long time;// thời gian xuất hiện vụ nổ

    @Override
    public void start() {
        HashMap<String, Sprite[]> anim = new HashMap<>();
        anim.put("burnOut", Sprite.explosion);

        texture = new AnimateTexture(this, 3, anim);
        time = 600;

        TimeCounter.callAfter(this::destroy, time, TimeUnit.MILLISECONDS);
    }

    @Override
    public void update() {

    }
}
