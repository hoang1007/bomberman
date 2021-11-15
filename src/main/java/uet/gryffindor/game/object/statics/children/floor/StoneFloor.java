package uet.gryffindor.game.object.statics.children.floor;

import uet.gryffindor.game.object.statics.Floor;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class StoneFloor extends Floor {
    private int at;

    public StoneFloor(int at) {
        this.at = at;
    }

    @Override
    public void start() {
        texture = new SpriteTexture(Sprite.tiles[at], this);
        this.collider.setEnable(false);
    }

    @Override
    public void update() {

    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}