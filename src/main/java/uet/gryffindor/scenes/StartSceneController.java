package uet.gryffindor.scenes;

import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;

public class StartSceneController {
  @FXML
  private void startGame() {
    GameApplication.setRoot("main");
  }
}
