package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.object.StaticObject;
import uet.gryffindor.graphic.Animator;

public abstract class Item extends StaticObject {
  protected long effectDuration;
  protected Animator animator;

  @Override
  public void start() {
    orderedLayer = OrderedLayer.MIDGROUND;
  }

  public void setEffectDuration(long effectDuration) {
    this.effectDuration = effectDuration;
  }

  public long getEffectDuration() {
    return this.effectDuration;
  }
}
