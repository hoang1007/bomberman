package uet.gryffindor.game.object.statics;

import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.object.StaticObject;

public class Floor extends StaticObject {

  @Override
  public void start() {
    this.collider.enabled(false);
  }

  @Override
  public void update() {
    
  }

  @Override
  public void onCollisionEnter(Collider that) {
    System.out.println(that.gameObject);
  }
}
