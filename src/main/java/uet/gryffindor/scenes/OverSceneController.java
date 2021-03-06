package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;
import uet.gryffindor.sound.SoundController;

public class OverSceneController {
  @FXML
  public void initialize() {
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.MENU_OVER).loop();
  }

  /** 
   * Thoát game.
  */
  @FXML
  public void quitGame() {
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    Platform.exit();
    System.exit(0);
  }

  /** 
   * Trở về menu chính.
  */
  @FXML
  public void backToMenu() {
    MainSceneController.game.destroy();
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
    GameApplication.setRoot("menu");
  }

  /** 
   * Chơi lại game.
  */
  @FXML
  public void playAgain() {
    MainSceneController.game.destroy();
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.PLAYGAME).loop();
    GameApplication.setRoot("ingame");
  }
}
