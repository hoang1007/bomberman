package uet.gryffindor.util;

import java.util.ArrayList;
import java.util.List;

public class OtherUtils {
  @SuppressWarnings("unchecked")
  public static <T> List<T> filter(List<?> list, Class<T> type) {
    List<T> result = new ArrayList<>();

    list.forEach(o -> {
      if (type.isAssignableFrom(o.getClass())) {
        result.add((T) o);
      }
    });

    return result;
  }

  public static <T extends Comparable<? super T>> Pair<Integer, T> max(T[] values) {
    T max = values[0];
    int maxId = 0;

    for (int i = 1; i < values.length; i++) {
      if (max.compareTo(values[i]) < 0) {
        max = values[i];
        maxId = i;
      }
    }

    return Pair.of(maxId, max);
  }
}
