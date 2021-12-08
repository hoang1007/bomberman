package uet.gryffindor.game.object.statics;

import java.util.concurrent.TimeUnit;

import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.StaticObject;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.sound.SoundController;

public class Portal extends StaticObject {
  public static Portal INSTANCE;
  private Animator animator;

  @Override
  public void start() {
    double rate = 1;
    animator = new Animator(rate, Sprite.portal);
    orderedLayer = OrderedLayer.MIDGROUND;
  }

  @Override
  public void update() {
    this.getTexture().setSprite(animator.getSprite());
  }

  @Override
  public void onCollisionStay(Collider that) {
    if (that.gameObject instanceof Bomber) {
      double overlapArea = that.getOverlapArea(this.collider);
      double portalArea = this.dimension.x * this.dimension.y;

      // When bomber go into portal, active portal
      if (overlapArea > .8 * portalArea) {
        System.out.println("2s remaining...");
        SoundController.INSTANCE.stopAll();
        SoundController.INSTANCE.getSound(SoundController.WIN_EFFECT).play();
        TimeCounter.callAfter(() -> {
          GameApplication.setRoot("WinScene");
        }, 2, TimeUnit.SECONDS);

        Game.pause = true;
      }
    }
  }

  public static void nextLevel() {
    Game myGame = Manager.INSTANCE.getGame();
    myGame.start();
  }
}
