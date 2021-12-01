package uet.gryffindor.game.object.dynamics;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.movement.Direction;
import uet.gryffindor.game.object.dynamics.explosion.Explosion;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Bomb extends GameObject {
    private SpriteTexture texture;
    private Animator aboutToExplore;
    private boolean explored;
    public static long time; // giới hạn thời gian
    private long startTime;
    private int explosionRadius; // bán kính vụ nổ

    @Override
    public void start() {
        texture = new SpriteTexture(Sprite.bomb[2], this);
        explored = false;

        double rate = 2;
        aboutToExplore = new Animator(rate, Sprite.bomb);
        orderedLayer = OrderedLayer.MIDGROUND;

        startTime = System.currentTimeMillis();
        time = 2000; // giới hạn 2 giây
        explosionRadius = 1;
    }

    @Override
    public void update() {
        if (!explored) {
            texture.setSprite(aboutToExplore.getSprite());
            // khi quá thời gian,xuất hiện vụ nổ và xóa bomb
            if (System.currentTimeMillis() - startTime >= time) {
                explored = true;
                explore();
                deleteBomb();
            }
        }
    }

    @Override
    public Texture getTexture() {
        return this.texture;
    }

    public void deleteBomb() {
        this.destroy();
    }

    /** hiệu ứng nổ. */
    public void explore() {
        if (explored) {
            // thêm vụ nổ ở trung tâm.
            GameObject.instantiate(Explosion.class, this.position);
            for (int i = 1; i <= explosionRadius; i++) {
                for (int j = 0; j < 4; j++) {
                    Vector2D neighbor = Direction.valueOf(j).forward(position, Sprite.DEFAULT_SIZE * i);
                    GameObject.instantiate(Explosion.class, neighbor);
                }
            }
        }
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isExplored() {
        return this.explored;
    }

    public int getExplosionRadius() {
        return this.explosionRadius;
    }
}
