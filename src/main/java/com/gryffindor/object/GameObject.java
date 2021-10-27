package com.gryffindor.object;

import java.io.FileReader;
import java.util.Properties;

import com.gryffindor.base.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {

  public static final double SPEECH = 0.5;

  protected Image img;
  protected Vector2D position; // Vị trí của đối tượng.
  protected Status status; // trạng thái di chuyển

  /** tổng số frame */
  protected int _total_frame_left = 0;
  protected int _total_frame_right = 0;
  protected int _total_frame_up = 0;
  protected int _total_frame_down = 0;
  protected int frame_run = 0;

  protected int FRAME_FPS = 15;

  public GameObject() {
    position = Vector2D.zero();
    status = Status.NONE;
    start();
  }

  /**
   * Khởi tạo các thuộc tính (thay thế cho contructor).
   */
  public abstract void start();

  /**
   * Cập nhật mỗi frame.
   */
  public abstract void update();

  /**
   * Render mỗi frame.
   */
  public abstract void render(GraphicsContext context);
}
