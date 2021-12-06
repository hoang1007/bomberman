package uet.gryffindor.util;

import uet.gryffindor.game.base.Vector2D;

public class Geometry {
  /**
   * Khoảng cách hai đầu của vector theo euclid.
   * @param a vector a
   * @param b vector b
   * @return
   */
  public static double euclideanDistance(Vector2D a, Vector2D b) {
    double deltaX = a.x - b.x;
    double deltaY = a.y - b.y;

    return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }

  /**
   * Khoảng cách hai đầu của vector theo manhattan.
   * @param a vector a
   * @param b vector b
   * @return
   */
  public static double manhattanDistance(Vector2D a, Vector2D b) {
    double deltaX = a.x - b.x;
    double deltaY = a.y - b.y;

    return Math.abs(deltaX) + Math.abs(deltaY);
  }

  /**
   * Vị trí và kích thước của hình chữ nhật bao quanh.
   *
   * @param a vector a
   * @param b vector b
   * @return pair of (position, dimension)
   */
  public static Pair<Vector2D, Vector2D> unionRect(Vector2D a, Vector2D b) {
    Vector2D position = new Vector2D(Math.min(a.x, b.x), Math.min(a.y, b.y));
    Vector2D dimension = new Vector2D(Math.abs(a.x - b.x), Math.abs(a.y - b.y));

    return Pair.of(position, dimension);
  }
}
