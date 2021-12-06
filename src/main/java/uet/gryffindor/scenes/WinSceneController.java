package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.statics.Portal;
import uet.gryffindor.sound.SoundController;
import javafx.scene.control.Label;


import java.awt.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class WinSceneController {

  @FXML private Label outScore;

  @FXML
  public void initialize() {
    if(MainSceneController.score > HighScoreSceneController.highestScore) {
      HighScoreSceneController.highestScore = MainSceneController.score;
    }
    MainSceneController.game.stopTime();
    setScore();

    SoundController.INSTANCE.getSound(SoundController.WIN_BG).loop();

  }

  public void setScore() {
    outScore.setText(String.valueOf(MainSceneController.score));
  }

  public void nextMap() {
    GameApplication.setRoot("ingame");
    Portal.nextLevel();
    //Game.pause = true;
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.PLAYGAME).loop();
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
