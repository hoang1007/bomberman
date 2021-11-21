package uet.gryffindor.game.object.statics;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.object.StaticObject;

public class Brick extends StaticObject implements Unmovable {

  @Override
  public void start() {
    orderedLayer = OrderedLayer.MIDGROUND;
  }

  @Override
  public void update() {}
}
