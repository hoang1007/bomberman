package uet.gryffindor.scenes;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import uet.gryffindor.base.GameObject;
import uet.gryffindor.engine.Collider;
import uet.gryffindor.engine.FpsTracker;
import uet.gryffindor.engine.Input;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.object.Bomber;
import uet.gryffindor.object.Grass;
import uet.gryffindor.object.Portal;

public class MainSceneController {
  @FXML
  private Canvas canvas;

  private GraphicsContext context;

  public void initialize() {
    context = canvas.getGraphicsContext2D();
    canvas.addEventHandler(KeyEvent.ANY, Input.INSTANCE);
    Sprite.loadSprite();

    // add Object
    GameObject.objects.add(new Bomber());
    GameObject.objects.add(new Portal());
    GameObject.objects.add(new Grass());

    new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (FpsTracker.isNextFrame(now)) {
          update();
          Collider.checkCollision();
          render();
        }
      }
    }.start();
  }

  public void start() {
    new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (FpsTracker.isNextFrame(now)) {
          update();
          Collider.checkCollision();
          render();
        }
      }
    }.start();
  }

  private void update() {
    int currentSize = GameObject.objects.size();
    for (int i = 0; i < GameObject.objects.size(); i++) {
      GameObject.objects.get(i).update();

      // cập nhật cho trường hợp update có hủy object
      int updateSize = GameObject.objects.size();

      if (currentSize > updateSize) {
        i = i - (currentSize - updateSize);
        currentSize = updateSize;
      }
    }
  }

  private void render() {
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    GameObject.objects.forEach(obj -> obj.render(context));
  }
}
