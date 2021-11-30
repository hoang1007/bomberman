package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;

public class OverSceneController {
  @FXML
  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }

  @FXML
  public void backToMenu() {
    GameApplication.setRoot("menu");
  }

  @FXML
  public void playAgain() {
    GameApplication.setRoot("ingame");
  }
}
