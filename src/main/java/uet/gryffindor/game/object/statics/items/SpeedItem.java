package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class SpeedItem extends Item {
  @Override
  public void start() {
    this.setTexture(new SpriteTexture(Sprite.heart[0], this));
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Bomber) {
      this.destroy();
    }
  }
}
