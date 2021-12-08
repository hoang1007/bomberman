package uet.gryffindor.game.object.dynamics.enemy;

import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.sound.SoundController;

public abstract class Enemy extends DynamicObject {

  protected Enemy() {
    this.orderedLayer = OrderedLayer.MIDGROUND;
  }

  public void dead() {
    SoundController.INSTANCE.getSound(
          SoundController.ENEMY_DIE).play(); // âm thanh khi enemy chết.

    this.collider.enabled(false);
    this.texture.changeTo("dead");
    this.texture.loopable(false);
    TimeCounter.callAfter(this::destroy, this.texture.getDuration("dead"));
  }
}
