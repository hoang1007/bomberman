package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Balloom extends Enemy {

    @Override
    public void start() {
        this.texture = new AnimateTexture(this, 5, Sprite.balloom);
        this.texture.changeTo("up");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }
    
}
