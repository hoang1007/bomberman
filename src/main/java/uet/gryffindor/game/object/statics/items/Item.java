package uet.gryffindor.game.object.statics.items;

import uet.gryffindor.game.object.StaticObject;

public abstract class Item extends StaticObject {
  protected long effectDuration;

  public void setEffectDuration(long effectDuration) {
    this.effectDuration = effectDuration;
  }

  public long getEffectDuration() {
    return this.effectDuration;
  }
}
