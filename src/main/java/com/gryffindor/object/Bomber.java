package com.gryffindor.object;

import com.gryffindor.Config;
import com.gryffindor.graphic.sprite.Sprite;
import com.gryffindor.graphic.sprite.SpriteSheet;
import com.gryffindor.services.Input;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomber extends GameObject {
  Image img_bomber = SpriteSheet.player.getImage();
  Sprite bomber_sprite = Sprite.player_stand; // ban đầu đứng yên

  @Override
  public void start() {
    // gán giá trị ...
    super._total_frame_right = Config.config.getPlayerTotalFrameRight();
    super._total_frame_left = Config.config.getPlayerTotalFrameLeft();
    super._total_frame_up = Config.config.getPlayerTotalFrameUp();
    super._total_frame_down = Config.config.getPlayerTotalFrameDown();

  }

  @Override
  public void update() {
    switch (Input.INSTANCE.getCode()) {
    case UP:
      this.position.y -= SPEECH;
      this.status = Status.UP;
      bomber_sprite = Sprite.player_up[frame_run / FRAME_FPS];
      frameRun(super._total_frame_up);
      break;
    case DOWN:
      this.position.y += SPEECH;
      this.status = Status.DOWN;
      bomber_sprite = Sprite.player_down[frame_run / FRAME_FPS];
      frameRun(super._total_frame_down);

      break;
    case RIGHT:
      this.position.x += SPEECH;
      this.status = Status.RIGHT;
      bomber_sprite = Sprite.player_right[frame_run / FRAME_FPS];
      frameRun(super._total_frame_right);
      break;
    case LEFT:
      this.position.x -= SPEECH;
      this.status = Status.LEFT;
      bomber_sprite = Sprite.player_left[frame_run / FRAME_FPS];
      frameRun(super._total_frame_left);
      break;
    default:
      bomber_sprite = Sprite.player_stand;
      this.status = Status.NONE;
      break;
    }
  }

  @Override
  public void render(GraphicsContext context) {

    update();

    context.drawImage(img_bomber, bomber_sprite.getX(), bomber_sprite.getY(), bomber_sprite.getWidth(),
        bomber_sprite.getHeight(), this.position.x, this.position.y, Sprite.DEFAULT_SIZE, Sprite.DEFAULT_SIZE);
  }

  void frameRun(int type_frame) {
    frame_run++;
    if (frame_run / FRAME_FPS >= type_frame)
      frame_run = 0;
  }
}