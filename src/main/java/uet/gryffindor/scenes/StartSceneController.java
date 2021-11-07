package uet.gryffindor.scenes;

import java.io.IOException;

import javafx.fxml.FXML;
import uet.gryffindor.Game;

public class StartSceneController {
  @FXML
  private void startGame() {
    try {
      Game.setRoot("main");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
