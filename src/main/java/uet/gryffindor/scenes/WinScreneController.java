package uet.gryffindor.scenes;

import javafx.fxml.FXML;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.sound.SoundController;

import java.util.concurrent.TimeUnit;

public class WinScreneController {
  @FXML
  public void initialize() {
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.WIN_EFFECT).play();
    TimeCounter.callAfter(() -> {
      SoundController.INSTANCE.getSound(SoundController.WIN_BG).loop();
    }, 5, TimeUnit.SECONDS);
  }
}
