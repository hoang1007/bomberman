package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Enemy extends DynamicObject {

    @Override
    public void start() {
        double rate = 3;
        super.setTexture(new AnimateTexture(this, rate, Sprite.enemy));
        orderedLayer = OrderedLayer.MIDGROUND;
    }

    @Override
    public void update() {
    }

}
