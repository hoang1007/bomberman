package uet.gryffindor.game.object.statics.children.wall;

import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Tiles extends Wall {
    private int at;

    public Tiles(int at) {
        this.at = at;
    }

    @Override
    public void start() {
        texture = new SpriteTexture(Sprite.tiles[at], this);
    }

    @Override
    public void update() {

    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public void setAt(int at) {
        this.at = at;
    }

    public int getAt() {
        return at;
    }
}
