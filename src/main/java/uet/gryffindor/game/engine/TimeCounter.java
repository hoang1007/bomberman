package uet.gryffindor.game.engine;

import java.util.concurrent.TimeUnit;

import uet.gryffindor.util.VoidFunction;

public class TimeCounter extends BaseService {
  private long frameCount = 0;
  private VoidFunction function;
  private VoidFunction callBack;
  private TaskType taskType;

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
      if (callBack != null) {
        callBack.invoke();
      }
      this.destroy();
    }
  }

  public TimeCounter onComplete(VoidFunction func) {
    this.callBack = func;
    return this;
  }

  /**
   * Thực thi hàm sau khoảng thời gian cho trước.
   * 
   * @param function hàm muốn thực thi
   * @param time     thời gian đếm ngược
   * @param timeUnit đơn vị thời gian
   */
  public static TimeCounter callAfter(VoidFunction function, long time, TimeUnit timeUnit) {
    return callAfter(function, timeUnit.toNanos(time) / FpsTracker.getFrameTime());
  }

  /**
   * Thực thi hàm sau số frame cho trước.
   * 
   * @param function hàm muốn thực thi
   * @param frame    số frame
   */
  public static TimeCounter callAfter(VoidFunction function, long frame) {
    TimeCounter instance = new TimeCounter();

    instance.function = function;
    instance.frameCount = frame;
    instance.taskType = TaskType.AFTER;

    return instance;
  }

  /**
   * Thực thi hàm trong khoảng thời gian cho trước.
   * 
   * @param function hàm muốn thực thi
   * @param time     thời gian thực thi hàm
   * @param timeUnit đơn vị thời gian
   */
  public static TimeCounter callDuring(VoidFunction function, long time, TimeUnit timeUnit) {
    return callDuring(function, timeUnit.toNanos(time) / FpsTracker.getFrameTime());
  }

  /**
   * Thực thi hàm trong số frame cho trước.
   * 
   * @param function hàm muốn thực thi
   * @param frame    số frame
   */
  public static TimeCounter callDuring(VoidFunction function, long frame) {
    TimeCounter instance = new TimeCounter();

    instance.function = function;
    instance.frameCount = frame;
    instance.taskType = TaskType.DURING;

    return instance;
  }

  enum TaskType {
    AFTER, DURING
  }
}
