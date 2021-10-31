package uet.gryffindor.util;

import java.util.ArrayList;

public class SortedList<T extends Comparable<T>> extends ArrayList<T> {
  /**
   * Thêm phần tử vào danh sách sắp xếp.
   *
   * @param value
   * @return boolean
   */
  @Override
  public boolean add(T value) {
    if (this.isEmpty()) {
      super.add(value);
    } else if (value.compareTo(this.get(this.size() - 1)) >= 0) {
      // if value is greater than tail of list
      // insert to the last
      super.add(value);
    } else if (value.compareTo(this.get(0)) < 0) { // if value is smaller than head of list
      // insert to the head
      super.add(0, value);
    } else {
      super.add(this.get(this.size() - 1));

      for (int i = this.size() - 2; i >= 1; i--) {
        if (value.compareTo(this.get(i)) < 0) {
          this.set(i + 1, this.get(i));
        } else {
          this.set(i + 1, value);
          break;
        }
      }
    }

    return true;
  }
}