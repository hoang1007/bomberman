package uet.gryffindor.game.object.statics;

import java.util.Random;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.StaticObject;
import uet.gryffindor.game.object.dynamics.Explosion;
import uet.gryffindor.game.object.statics.items.BombItem;
import uet.gryffindor.game.object.statics.items.FlameItem;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.game.object.statics.items.SpeedItem;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;

public class Brick extends StaticObject implements Unmovable {

  @Override
  public void start() {
    orderedLayer = OrderedLayer.MIDGROUND;
  }

  @Override
  public void update() {
  }

  public Item getItem() {
    Item item = null;
    int type = new Random().nextInt(10);
    switch (type) {
      case 1:
        item = new BombItem();
        item.setTexture(new SpriteTexture(Sprite.explosionPotion[0], item));
        break;
      case 2:
        item = new FlameItem();
        item.setTexture(new SpriteTexture(Sprite.heart[0], item));
        break;
      case 3:
        item = new SpeedItem();
        item.setTexture(new SpriteTexture(Sprite.speedPotion[0], item));
        break;
      default:
        break;
    }
    if (item != null) {
      item.position.setValue(this.position);
    }
    return item;
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Explosion) {
      Item item = getItem();

      if (item != null) {
        GameObject.addObject(item);
      }

      this.destroy();
    }
  }
}
