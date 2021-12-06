package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.object.statics.Portal;
import uet.gryffindor.sound.SoundController;

public class WinSceneController {
  public static int level = 1;
  @FXML private Label outScore;

  @FXML
  public void initialize() {
    setScore();
    MainSceneController.game.destroy();
    SoundController.INSTANCE.getSound(SoundController.WIN_BG).loop();
  }

  public void setScore() {
    outScore.setText(Integer.toString(Manager.INSTANCE.getGame().getScore()));
  }

  public void nextMap() {
    level++;
    GameApplication.setRoot("ingame");
    if (level % 3 == 2) {
      Portal.nextLevel();
    }
    if (level % 3 == 0) {
      Portal.nextLevel();
      Portal.nextLevel();
    }
    //Game.pause = true;
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.PLAYGAME).loop();
  }

  /** Trở về menu chính. */
  public void backToMenu() {
    MainSceneController.game.destroy();
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
    GameApplication.setRoot("menu");
  }

  /** Thoát game. */
  public void quitGame() {
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    Platform.exit();
    System.exit(0);
  }

}
