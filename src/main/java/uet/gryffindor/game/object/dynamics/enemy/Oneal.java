package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Oneal extends Enemy {

    @Override
    public void start() {
        this.texture = new AnimateTexture(this, 5, Sprite.oneal);
        this.texture.changeTo("up");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }
}