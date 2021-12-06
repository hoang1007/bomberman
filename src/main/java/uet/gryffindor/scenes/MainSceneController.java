package uet.gryffindor.scenes;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Config;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.sound.SoundController;
import uet.gryffindor.util.Transporter;

public class MainSceneController {
  public static Game game;

  @FXML
  private Canvas canvas;
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
    
    game = new Game(canvas, config);
    Manager.INSTANCE.setGame(game);
    game.bindDisplayInfo(scoreLabel, levelLabel, heartLabel);
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
}
