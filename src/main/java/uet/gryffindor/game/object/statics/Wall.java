package uet.gryffindor.game.object.statics;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public abstract class Wall extends GameObject implements Unmovable {
    protected SpriteTexture texture;
}
