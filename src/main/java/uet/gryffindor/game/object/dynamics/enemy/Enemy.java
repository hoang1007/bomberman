package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.object.DynamicObject;

public abstract class Enemy extends DynamicObject {
  protected Enemy() {
    this.orderedLayer = OrderedLayer.MIDGROUND;
  }

}
