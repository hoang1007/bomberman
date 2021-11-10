package uet.gryffindor.game.object.items;

import uet.gryffindor.game.base.GameObject;

public abstract class Item extends GameObject {
  private long effectDuration;

  public long getEffectDuration() {
    return this.effectDuration;
  }
}
