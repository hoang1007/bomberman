package uet.gryffindor.game.object;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Bomb extends GameObject {
    private SpriteTexture texture;
    private Animator aboutToExplore;
    private boolean explored;
    private int countToExplored;
    private int intervalToExplored;

    @Override
    public void start() {
        texture = new SpriteTexture(Sprite.bombExplosion[2], this);
        explored = false;

        double rate = 4;
        aboutToExplore = new Animator(rate, Sprite.bombExplosion);
        orderedLayer = OrderedLayer.MIDGROUND;

        countToExplored = 0;
        intervalToExplored = 50;
    }

    @Override
    public void update() {
        if (!explored) {
            texture.setSprite(aboutToExplore.getSprite());
            countToExplored++;
            if (countToExplored >= intervalToExplored) {
                countToExplored = 0;
                explored = true;
                objects.remove(this);
            }
        }
    }

    public void setPosition(Vector2D position) {
        super.position = position.clone();
    }

    @Override
    public Texture getTexture() {
        return this.texture;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isExplored() {
        return this.explored;
    }

}
