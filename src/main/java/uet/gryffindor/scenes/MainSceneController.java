package uet.gryffindor.scenes;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.graphic.sprite.Sprite;

public class MainSceneController {
  @FXML
  private Canvas canvas;
  private Game game;

  public void initialize() {
    canvas.addEventHandler(KeyEvent.ANY, Input.INSTANCE);
    Sprite.loadSprite();

    game = new Game(canvas.getGraphicsContext2D());
    game.start();
  }


  @FXML
  private void backToMenu() {
    GameApplication.setRoot("start");
    game.clear();
  }
}
