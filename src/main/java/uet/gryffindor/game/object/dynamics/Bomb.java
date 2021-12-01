package uet.gryffindor.game.object.dynamics;

import uet.gryffindor.game.Manager;
import uet.gryffindor.game.Map;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.explosion.Explosion;
import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.game.object.statics.items.Item;
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
            // thêm vụ nổ các hướng sang phải
            for (int i = 1; i <= explosionRadius; i++) {
                int x = (int) this.position.x + i * Sprite.DEFAULT_SIZE;
                int y = (int) this.position.y;
                if (addExplosionAt(x, y) == false) {
                    break;
                }
            }

            // thêm vụ nổ các hướng sang trái
            for (int i = 1; i <= explosionRadius; i++) {
                int x = (int) this.position.x - i * Sprite.DEFAULT_SIZE;
                int y = (int) this.position.y;
                if (addExplosionAt(x, y) == false) {
                    break;
                }
            }

            // thêm vụ nổ các hướng lên trên
            for (int i = 1; i <= explosionRadius; i++) {
                int x = (int) this.position.x;
                int y = (int) this.position.y - i * Sprite.DEFAULT_SIZE;
                if (addExplosionAt(x, y) == false) {
                    break;
                }
            }

            // thêm vụ nổ các hướng xuống dưới
            for (int i = 1; i <= explosionRadius; i++) {
                int x = (int) this.position.x;
                int y = (int) this.position.y + i * Sprite.DEFAULT_SIZE;
                if (addExplosionAt(x, y) == false) {
                    break;
                }
            }
        }
    }

    /** Thêm 1 explosion tại (x,y) */
    public boolean addExplosionAt(int x, int y) {
        int coordinatesX = (x + Sprite.DEFAULT_SIZE / 2) / Sprite.DEFAULT_SIZE;
        int coordinatesY = (y + Sprite.DEFAULT_SIZE / 2) / Sprite.DEFAULT_SIZE;
        // nếu vướng tường , return false.
        if (entangle(coordinatesX, coordinatesY)) {
            return false;
        } else {
            GameObject.instantiate(Explosion.class, new Vector2D(x, y));

            return true;
        }
    }

    public boolean entangle(int coordinatesX, int coordinatesY) {
        Map myMap = Manager.INSTANCE.getGame().getPlayingMap();

        String symbol = myMap.getRawMapAt(coordinatesY, coordinatesX);
        if (symbol.endsWith("f7") && !symbol.startsWith("o3") || symbol.equals("w25") || symbol.equals("w1")
                || symbol.equals("w4") || symbol.contains("f23")) {
            for (int i = 0; i < myMap.getObjects().size(); i++) {
                if (myMap.getObjects().get(i) instanceof Brick) {
                    Brick b = (Brick) myMap.getObjects().get(i);
                    if (b.position.equals(
                            new Vector2D(coordinatesX * Sprite.DEFAULT_SIZE, coordinatesY * Sprite.DEFAULT_SIZE))) {

                        Item item = b.getItem();
                        if (item != null) {
                            item.start();
                            myMap.getObjects().add(item);
                        }
                        myMap.getObjects().remove(i);
                        i--;

                    }
                }
            }
            return false;
        }
        return true;
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
