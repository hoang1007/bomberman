package uet.gryffindor.game.object.statics.children.brick;

import uet.gryffindor.game.object.statics.Brick;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class ChildBrick extends Brick {
    private int at = 0;

    public ChildBrick(int at) {
        this.at = at;
    }

    public void start() {
        texture = new SpriteTexture(Sprite.brick[at], this);
    }

}
