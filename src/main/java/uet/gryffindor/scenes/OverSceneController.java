package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;
import uet.gryffindor.sound.SoundController;
import uet.gryffindor.sound.SoundInGame;

public class OverSceneController {
  @FXML
  public void initialize() {
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.MENU_OVER).loop();
  }

  @FXML
  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }

  @FXML
  public void backToMenu() {
    GameApplication.setRoot("menu");
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
  }

  @FXML
  public void playAgain() {
    GameApplication.setRoot("ingame");
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.PLAYGAME).loop();
  }
}
