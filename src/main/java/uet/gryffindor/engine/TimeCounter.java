package uet.gryffindor.engine;

import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.base.GameObject;
import uet.gryffindor.util.ExecuteFunction;

public class TimeCounter extends GameObject {
  private long frameCount = 0;
  private ExecuteFunction function;
  private TaskType taskType;

  @Override
  public void start() {
    GameObject.objects.add(this);
    collider.setEnable(false);
    position.setValue(-100, -100);
    dimension.setValue(-100, -100);
  }

  @Override
  public void update() {
    if (frameCount > 0) {
      frameCount--;

      switch (taskType) {
      case AFTER:
        if (frameCount == 0) {
          function.invoke();
        }
        break;

      case DURING:
        function.invoke();
        break;
      default:
        break;
      }
    } else {
      this.destroy();
    }
  }

  @Override
  public void render(GraphicsContext context) {

  }

  /**
   * Thực thi hàm sau khoảng thời gian cho trước.
   * @param function hàm muốn thực thi
   * @param time thời gian đếm ngược
   * @param timeUnit đơn vị thời gian
   */
  public static void callAfter(ExecuteFunction function, long time, TimeUnit timeUnit) {
    callAfter(function, timeUnit.toNanos(time) / FpsTracker.getFrameTime());
  }

  /**
   * Thực thi hàm sau số frame cho trước.
   * @param function hàm muốn thực thi
   * @param frame số frame
   */
  public static void callAfter(ExecuteFunction function, long frame) {
    System.out.println("oi ban oi");
    TimeCounter instance = new TimeCounter();

    instance.function = function;
    instance.frameCount = frame;
    instance.taskType = TaskType.AFTER;
  }

  /**
   * Thực thi hàm trong khoảng thời gian cho trước.
   * @param function hàm muốn thực thi
   * @param time thời gian thực thi hàm
   * @param timeUnit đơn vị thời gian
   */
  public static void callDuring(ExecuteFunction function, long time, TimeUnit timeUnit) {
    callDuring(function, timeUnit.toNanos(time) / FpsTracker.getFrameTime());
  }

  /**
   * Thực thi hàm trong số frame cho trước.
   * @param function hàm muốn thực thi
   * @param frame số frame
   */
  public static void callDuring(ExecuteFunction function, long frame) {
    TimeCounter instance = new TimeCounter();

    instance.function = function;
    instance.frameCount = frame;
    instance.taskType = TaskType.DURING;
  }

  enum TaskType {
    AFTER, DURING
  }
}
