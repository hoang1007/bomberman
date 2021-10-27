package com.gryffindor.object;

import com.gryffindor.graphic.sprite.Sprite;
import com.gryffindor.graphic.sprite.SpriteSheet;
import com.gryffindor.services.Input;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomber extends GameObject {
  Image img_bomber = SpriteSheet.player.getImage();

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
    context.drawImage(img_bomber, Sprite.player_stand.getX(), Sprite.player_stand.getY(),
        Sprite.player_stand.getWidth(), Sprite.player_stand.getHeight(), this.position.x, this.position.y,
        Sprite.DEFAULT_SIZE, Sprite.DEFAULT_SIZE);
  }
}