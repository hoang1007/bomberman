package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Magma extends Enemy {

  @Override
  public void start() {
    this.texture = new AnimateTexture(this, 1, Sprite.magma);
    super.orderedLayer = OrderedLayer.BACKGROUND;
  }

  @Override
  public void update() {
  }

  @Override
  public void onCollisionEnter(Collider that) {
  }

}
