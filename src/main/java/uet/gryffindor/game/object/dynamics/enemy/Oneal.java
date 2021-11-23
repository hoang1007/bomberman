package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Oneal extends Enemy {
    private int attackRadius = 105;

    @Override
    public void start() {
        this.texture = new AnimateTexture(this, 5, Sprite.oneal);
        this.texture.changeTo("up");
        
        GameObject.addObject(new GameObject() {

            @Override
            public void start() {
                this.position = Oneal.this.position;
                // d = 2*r + 1
                this.collider.setDimension(new Vector2D(attackRadius, attackRadius).add(this.dimension).multiply(2));
            }

            @Override
            public void update() {
                
            }
            
            @Override
            public void onCollisionEnter(Collider that) {
                if (that.gameObject instanceof Bomber) {
                    System.out.println("In range attack");
                }
            }

            @Override
            public void onCollisionExit(Collider that) {
                if (that.gameObject instanceof Bomber) {
                    System.out.println("Bomber escaped");
                }
            }
        });
    }

    @Override
    public void update() {

    }
}