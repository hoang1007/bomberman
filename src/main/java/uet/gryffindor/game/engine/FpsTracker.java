package uet.gryffindor.game.engine;

public class FpsTracker {
  private static int fps = 100;
  private static long nanoTimePerFrame = 1_000_000_000 / fps;
  private static long lastFrameStamp = 0;

  public static void setFps(int fps) {
    FpsTracker.fps = fps;

    nanoTimePerFrame = 1_000_000_000 / fps;
  }

  public static int getFps() {
    return fps;
  }

  /**
   * Xét xem mốc thời gian này có vượt qua thời gian của frame hiện tại hay không.
   *
   * @param now
   * @return true nếu mốc thời gian ở một frame mới
   */
  public static boolean isNextFrame(long now) {
    if (now - lastFrameStamp >= nanoTimePerFrame) {
      lastFrameStamp = now;
      return true;
    }

    return false;
  }

  /**
   * Thời gian của một frame.
   *
   * @return Thời gian của một frame bằng nanoseconds.
   */
  public static long getFrameTime() {
    return nanoTimePerFrame;
  }

  /**
   * Trả về mốc thời gian cập nhật cuối cùng.
   *
   * @return mốc thời gian cập nhật cuối cùng
   */
  public static long getLastFrameStamp() {
    return lastFrameStamp;
  }

  public static double fixedDeltaTime() {
    return 1f / fps;
  }
}
