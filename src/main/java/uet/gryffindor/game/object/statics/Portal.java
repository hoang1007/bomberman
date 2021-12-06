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
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Bomber) {
      System.out.println("2s remaining...");
      SoundController.INSTANCE.stopAll();
      SoundController.INSTANCE.getSound(SoundController.WIN_EFFECT).play();
      TimeCounter.callAfter(() -> {
        GameApplication.setRoot("WinScene");
      }, 3, TimeUnit.SECONDS);
      //TimeCounter.callAfter(this::nextLevel, 2, TimeUnit.SECONDS);
      Game.pause = true;
    }
  }

  public static void nextLevel() {
    Game myGame = Manager.INSTANCE.getGame();
    myGame.start();
  }
}
