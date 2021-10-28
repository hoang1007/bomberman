package com.gryffindor.base;

/**
 * Lớp vector hỗ trợ các phép toán trên vector
 */
public class Vector2D {
  public double x;
  public double y;

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Vector2D zero() {
    return new Vector2D(0, 0);
  }

  public static Vector2D one() {
    return new Vector2D(1, 1);
  }

  public void setValue(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2D add(Vector2D that) {
    return new Vector2D(this.x + that.x, this.y + that.y);
  }

  public Vector2D subtract(Vector2D that) {
    return new Vector2D(this.x - that.x, this.y - that.y);
  }

  public Vector2D multiply(Vector2D that) {
    return new Vector2D(this.x * that.x, this.y * that.y);
  }

  public Vector2D divide(Vector2D that) {
    if (that.x == 0 || that.y == 0) {
      return zero();
    }

    return new Vector2D(this.x / that.x, this.y / that.y);
  }

  public Vector2D multiply(double factor) {
    return new Vector2D(this.x * factor, this.y * factor);
  }

  public Vector2D clone() {
    return new Vector2D(this.x, this.y);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
