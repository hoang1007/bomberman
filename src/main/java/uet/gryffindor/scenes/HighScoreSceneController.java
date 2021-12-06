package uet.gryffindor.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uet.gryffindor.GameApplication;
import uet.gryffindor.sound.SoundController;

public class HighScoreSceneController {
  public static int highestScore = 0;
  @FXML private Label hightScore;
  @FXML private Label nameHight;

  public void initialize() {
    setTop1();
    SoundController.INSTANCE.getSound(SoundController.WIN_EFFECT).play();
  }

  public void setTop1() {
    hightScore.setText(String.valueOf(highestScore));
    nameHight.setText("Gryffindor");
  }

  public void backToMenu() {
    GameApplication.setRoot("menu");
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
  }
}
