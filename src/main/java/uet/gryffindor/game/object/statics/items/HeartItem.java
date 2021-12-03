package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;

public class HeartItem extends Item {
    public static int power = 1;

    @Override
    public void start() {
        super.start();
        double rate = 1;
        animator = new Animator(rate, Sprite.heart);
    }

    @Override
    public void update() {
        this.getTexture().setSprite(animator.getSprite());
    }
}
