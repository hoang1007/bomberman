package com.gryffindor.object;

import com.gryffindor.services.Input;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomber extends GameObject {
  Image bomber = new Image(getClass().getResource("/bomber.jpeg").toExternalForm(), 50, 50, true, true);

  @Override
  public void start() {
    // TODO Auto-generated method stub
  }

  @Override
  public void update() {
    switch (Input.INSTANCE.getCode()) {
      case UP:
        this.position.y -= 1f;
        break;
      case DOWN:
        this.position.y += 1f;
        break;
      case RIGHT:
        this.position.x += 1f;
        break;
      case LEFT:
        this.position.x -= 1f;
        break;
      default:
        break;
    }
  }

  @Override
  public void render(GraphicsContext context) {
    context.drawImage(bomber, this.position.x, this.position.y);
  }
}