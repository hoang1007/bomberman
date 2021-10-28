package com.gryffindor.services;

public class FpsTracker {
  private static int fps = 100;
  private static double nanoTimePerFrame = 1e9 / fps;
  private static long lastFrameStamp = 0;

  public static void setFps(int fps) {
    FpsTracker.fps = fps;

    nanoTimePerFrame = (1 / fps) * 1e9;
  }

  public static boolean isNextFrame(long now) {
    if (now - lastFrameStamp >= nanoTimePerFrame) {
      lastFrameStamp = now;
      return true;
    }

    return false;
  }
}
