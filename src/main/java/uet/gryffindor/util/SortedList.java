package uet.gryffindor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SortedList<T extends Comparable<T>> extends ArrayList<T> {
  private Pair<Integer, T> lastElement;

  /**
   * Thêm phần tử vào danh sách sắp xếp.
   *
   * @param value giá trị
   * @return boolean
   */
  @Override
  public boolean add(T value) {
    int index = 0;
    if (this.isEmpty()) {
      super.add(value);
      index = 1;
    } else if (value.compareTo(this.get(this.size() - 1)) >= 0) {
      // if value is greater than tail of list
      // insert to the last
      super.add(value);
      index = this.size() - 1;
    } else if (value.compareTo(this.get(0)) < 0) {
      // if value is smaller than head of list
      // insert to the head
      super.add(0, value);
      index = 0;
    } else {
      super.add(this.get(this.size() - 1));

      for (int i = this.size() - 2; i >= 0; i--) {
        if (value.compareTo(this.get(i)) < 0) {
          this.set(i + 1, this.get(i));
        } else {
          this.set(i + 1, value);
          index = i + 1;
          break;
        }
      }
    }

    lastElement = Pair.of(index, value);
    return true;
  }

  /**
   * Thêm phần tử vào cuối danh sách. Sử dụng khi không muốn thêm phần tử theo thứ tự.
   *
   * @param value giá trị
   * @return boolean
   */
  public boolean push(T value) {
    return super.add(value);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    c.forEach(item -> this.add(item));

    return true;
  }

  /** Trả về phần tử cuối cùng được thêm vào trong danh sách. */
  public Pair<Integer, T> getLastElement() {
    return this.lastElement;
  }

  public void sort() {
    Collections.sort(this);
  }
}
