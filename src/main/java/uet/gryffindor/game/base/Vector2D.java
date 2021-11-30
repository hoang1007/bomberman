package uet.gryffindor.game.base;

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

  public void setValue(Vector2D that) {
    this.x = that.x;
    this.y = that.y;
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
      throw new ArithmeticException("Division by zero");
    }

    return new Vector2D(this.x / that.x, this.y / that.y);
  }

  public Vector2D multiply(double factor) {
    return new Vector2D(this.x * factor, this.y * factor);
  }

  public boolean equals(Object o) {
    if (o instanceof Vector2D) {
      Vector2D that = (Vector2D) o;

      return that.x == this.x && that.y == this.y;
    }

    return false;
  }

  public int hashCode() {
    return this.toString().hashCode();
  }

  public Vector2D clone() {
    return new Vector2D(this.x, this.y);
  }

  /**
   * Làm tròn x, y với factor cho trước.
   * @param unit đơn vị làm tròn
   * @param factor độ làm tròn
   * @return vector copy đã được làm tròn của vector cũ
   */
  public Vector2D smooth(double unit, double factor) {
    double x = Math.round(this.x / unit) * unit;
    double y = Math.round(this.y / unit) * unit;
    Vector2D result = this.clone();

    double delta = unit * factor;
    if (Math.abs(x - this.x) <= delta && Math.abs(y - this.y) <= delta) {
      result.setValue(x, y);
    }

    return result;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}