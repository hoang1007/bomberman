package uet.gryffindor.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Config;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.sound.SoundController;
import uet.gryffindor.util.Transporter;

public class MainSceneController {

  public static int heart = 0;
  public static int level = 0;
  public static int score = 0;

  @FXML
  private Canvas canvas;
  private Game game;

  @FXML
  private Label heartLabel;
  @FXML
  private Label scoreLabel;
  @FXML
  private Label timeLabel;
  @FXML
  private Label levelLabel;

  /** Hàm khởi tạo được gọi bởi fxml. */
  public void initialize() {
    canvas.addEventHandler(KeyEvent.ANY, Input.INSTANCE);
    Sprite.loadSprite();
    Config config = Transporter.INSTANCE.get("config", Config.class);

    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
      setInfoInGame();
    }));
    timeLine.setCycleCount(Animation.INDEFINITE);
    timeLine.play();

    game = new Game(canvas, config);
    Manager.INSTANCE.setGame(game);
    game.start();
  }

  @FXML
  private void backToMenu() {

    // GameApplication.setRoot("start");
    GameApplication.setRoot("menu");
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
  }

  public void setInfoInGame() {
    scoreLabel.setText(score + "");
    levelLabel.setText(level + "");
    heartLabel.setText(heart + "");
  }
}
