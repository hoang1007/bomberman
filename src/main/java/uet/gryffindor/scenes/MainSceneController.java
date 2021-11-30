package uet.gryffindor.scenes;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.graphic.sprite.Sprite;

public class MainSceneController {
  @FXML private Canvas canvas;
  private Game game;

  @FXML  private Label scoreLabel;
  @FXML private Label timeLabel;
  @FXML private Label levelLabel;

  /** Hàm khởi tạo được gọi bởi fxml. */
  public void initialize() {
    canvas.addEventHandler(KeyEvent.ANY, Input.INSTANCE);
    Sprite.loadSprite();

    game = new Game(canvas);
    Manager.INSTANCE.setGame(game);
    game.start();
  }

  @FXML
  private void backToMenu() {

//    GameApplication.setRoot("start");
    GameApplication.setRoot("menu");
  }

  public void setInfofInGame() {
    scoreLabel.setText("hi");
    timeLabel.setText("");
    levelLabel.setText("");
  }
}
