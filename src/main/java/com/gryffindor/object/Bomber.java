package com.gryffindor.object;

import com.gryffindor.services.Input;

import javafx.scene.canvas.GraphicsContext;

public class Bomber extends GameObject {
  final double step = 5f;
  @Override
  public void start() {

  }

  @Override
  public void update() {
    switch (Input.INSTANCE.getCode()) {
      case UP:
        this.position.y -= step;
        break;
      case DOWN:
        this.position.y += step;
        break;
      case RIGHT:
        this.position.x += step;
        break;
      case LEFT:
        this.position.x -= step;
        break;
      default:
        break;
    }
  }

  @Override
  public void render(GraphicsContext context) {
    context.fillRect(position.x, position.y, collider.getDimension().x, collider.getDimension().y);
  }
}