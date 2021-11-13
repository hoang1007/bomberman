package uet.gryffindor.game.object.dynamics.explosion;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class Explosion extends GameObject {

    private SpriteTexture texture;
    private Animator burnOut;

    public static long time;// thời gian xuất hiện vụ nổ
    private long startTime;

    @Override
    public void start() {
        texture = new SpriteTexture(Sprite.explosion[0], this);
        double rate = 3;
        burnOut = new Animator(rate, Sprite.explosion);
        time = 1200;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        texture.setSprite(burnOut.getSprite());
        if (System.currentTimeMillis() - startTime >= time) {
            deleteExplosion();
        }
    }

    public void deleteExplosion() {
        GameObject.objects.remove(this);
    }

    @Override
    public SpriteTexture getTexture() {
        return texture;
    }

}
