package uet.gryffindor;

import uet.gryffindor.base.GameObject;
import uet.gryffindor.engine.Collider;
import uet.gryffindor.engine.FpsTracker;
import uet.gryffindor.engine.Input;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.object.Bomber;
import uet.gryffindor.object.Grass;
import uet.gryffindor.object.Portal;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * JavaFX App.
 */
public class Game extends Application {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  private Canvas canvas;
  private GraphicsContext context;

  @Override
  public void start(Stage stage) {
    canvas = new Canvas(WIDTH, HEIGHT);
    context = canvas.getGraphicsContext2D();

    stage.setScene(new Scene(new Group(canvas)));
    stage.show();

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

    initialize();
  }

  private void initialize() {
    Sprite.loadSprite();

    handleEvent();
    
    // add Object
    GameObject.objects.add(new Bomber());
    GameObject.objects.add(new Portal());
    GameObject.objects.add(new Grass());
  }

  private void handleEvent() {
    canvas.setFocusTraversable(true);
    canvas.addEventHandler(KeyEvent.ANY, Input.INSTANCE);
  }

  private void render() {
    context.clearRect(0, 0, WIDTH, HEIGHT);
    GameObject.objects.forEach(obj -> obj.render(context));
  }

  private void update() {
    GameObject.objects.forEach(GameObject::update);
  }

  public static void main(String[] args) {
    launch(args);
  }
}