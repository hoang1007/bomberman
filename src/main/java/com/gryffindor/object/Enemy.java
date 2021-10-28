package com.gryffindor.object;

import com.gryffindor.base.Vector2D;
import com.gryffindor.services.Collider;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends GameObject {
  Color colorFill = Color.BLACK;

  @Override
  public void start() {
    this.position.setValue(300, 200);

    this.collider.setDimension(new Vector2D(100, 100));
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(GraphicsContext context) {
    context.setFill(Color.BLACK);
    context.strokeRect(collider.position.x, collider.position.y, collider.getDimension().x, collider.getDimension().y);
    
    context.setFill(colorFill);
    context.fillRect(position.x, position.y, dimension.x, dimension.y);
  }

  @Override
  public void onCollisionStay(Collider that) {
    System.out.println("Bomber attack!");
    colorFill = Color.RED;
    this.collider.setDimension(this.collider.getDimension().add(Vector2D.one()));
  }

  @Override
  public void onCollisionEnter(Collider that) {
    colorFill = Color.BLUE;
  }

  @Override
  public void onCollisionExit(Collider that) {
    colorFill = Color.BLACK;
  }
}
