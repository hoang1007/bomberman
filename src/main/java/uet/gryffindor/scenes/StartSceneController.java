package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;
import uet.gryffindor.sound.SoundController;


public class StartSceneController {
  //  private static SoundInGame soundInGame;
  @FXML
  private void startGame() {
    // GameApplication.setRoot("main");
    GameApplication.setRoot("ingame");

    SoundController.play("soundtrack");
  }

  @FXML
  public void hightScore() {
    System.out.println(this.getClass());
    System.out.println("ẤN ĐÚNG RỒI ĐẤY. CHOI NGU MÀ CÒN DOI XEM ĐIỂM CAO.");
  }

  public void selectionBomber1(ActionEvent actionEvent) {
    GameApplication.setRoot("menuOver");
  }

  @FXML
  public void selectionBomber2 () {
    GameApplication.setRoot("menuOver");
  }

  @FXML
  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }


}
