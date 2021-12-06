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

  public static int level;
  public static int score;
  public static int heart;
  @FXML private Canvas canvas;
  public static Game game;

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
    game.destroy();
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
    GameApplication.setRoot("menu");
  }

  public void setInfoInGame() {
    scoreLabel.setText(score + "");
    levelLabel.setText(level + "");
    heartLabel.setText(heart + "");
  }
}
