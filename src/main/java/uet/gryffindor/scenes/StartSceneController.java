package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;

public class StartSceneController {
  //  private static SoundInGame soundInGame;
  @FXML
  private void startGame() {
    // GameApplication.setRoot("main");
    GameApplication.setRoot("ingame");
    //    soundInGame = new SoundInGame();
    //    soundInGame.initSound();
    //    soundInGame.playAudio();
    //    soundInGame.repeatAudio();
  }

  @FXML
  public void hightScore() {
    System.out.println("ẤN ĐÚNG RỒI ĐẤY. CHOI NGU MÀ CÒN DOI XEM ĐIỂM CAO.");
  }

  @FXML
  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }
}
