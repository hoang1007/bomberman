package com.gryffindor;

import com.gryffindor.graphic.sprite.Sprite;
import com.gryffindor.object.Bomber;
import com.gryffindor.object.GameObject;
import com.gryffindor.services.Input;

import java.util.ArrayList;
import java.util.List;

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

  private List<GameObject> gameObjects = new ArrayList<>();

  @Override
  public void start(Stage stage) {
    canvas = new Canvas(WIDTH, HEIGHT);
    context = canvas.getGraphicsContext2D();

    stage.setScene(new Scene(new Group(canvas)));
    stage.show();

    new AnimationTimer() {
      @Override
      public void handle(long now) {
        update();
        render();
      }
    }.start();

    // add Object
    gameObjects.add(new Bomber());

    handleEvent();
  }

  private void handleEvent() {
    canvas.setFocusTraversable(true);
    canvas.addEventHandler(KeyEvent.ANY, Input.INSTANCE);
  }

  private void render() {
    context.clearRect(0, 0, WIDTH, HEIGHT);
    gameObjects.forEach(obj -> obj.render(context));
  }

  private void update() {
    gameObjects.forEach(GameObject::update);
  }

  public static void main(String[] args) {
    // load sprite
    Sprite.loadSprite();
    launch(args);
  }
}