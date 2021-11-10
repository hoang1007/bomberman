package uet.gryffindor.game.engine;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input implements EventHandler<KeyEvent> {
  public static Input INSTANCE = new Input();
  private KeyCode keyCode = KeyCode.UNDEFINED;

  @Override
  public void handle(KeyEvent event) {
    if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
      keyCode = event.getCode();
    } else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
      keyCode = KeyCode.UNDEFINED;
    }
  }

  public KeyCode getCode() {
    return this.keyCode;
  }
}
