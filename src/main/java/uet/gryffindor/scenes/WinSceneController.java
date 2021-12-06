package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;
import uet.gryffindor.sound.SoundController;
import javafx.scene.control.Label;


public class WinSceneController {

  @FXML private Label outScore;

  @FXML
  public void initialize() {
    setScore();
    SoundController.INSTANCE.getSound(SoundController.WIN_BG).loop();
  }

  public void setScore() {
    outScore.setText(String.valueOf(MainSceneController.score));
  }

  public void backToMenu() {
    MainSceneController.game.destroy();
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
    GameApplication.setRoot("menu");
  }

  public void quitGame() {
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    Platform.exit();
    System.exit(0);
  }

}
