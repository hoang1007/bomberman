package uet.gryffindor.engine;

import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.base.GameObject;
import uet.gryffindor.util.ExecuteFunction;

public class TimeCounter extends GameObject {
  private long time = 0;
  private ExecuteFunction function;
  private TaskType taskType;

  private static final TimeCounter INSTANCE = new TimeCounter();

  @Override
  public void start() {
    GameObject.objects.add(this);
    collider.setEnable(false);
    position.setValue(-100, -100);
    dimension.setValue(-100, -100);
  }

  @Override
  public void update() {
    if (time > 0) {
      time -= FpsTracker.getFrameTime();

      switch (taskType) {
      case AFTER:
        if (time <= 0) {
          function.invoke();
        }
        break;

      case DURING:
        function.invoke();
        break;
      default:
        break;
      }
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
    INSTANCE.function = function;
    // convert time to nanoseconds
    INSTANCE.time = timeUnit.toNanos(time);
    INSTANCE.taskType = TaskType.AFTER;
  }

  /**
   * Thực thi hàm trong khoảng thời gian cho trước.
   * @param function hàm muốn thực thi
   * @param time thời gian thực thi hàm
   * @param timeUnit đơn vị thời gian
   */
  public static void callDuring(ExecuteFunction function, long time, TimeUnit timeUnit) {
    INSTANCE.function = function;
    INSTANCE.time = timeUnit.toNanos(time);
    INSTANCE.taskType = TaskType.DURING;
  }

  enum TaskType {
    AFTER, DURING
  }
}
