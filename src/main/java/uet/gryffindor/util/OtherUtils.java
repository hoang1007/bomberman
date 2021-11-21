package uet.gryffindor.util;

import java.util.ArrayList;
import java.util.List;

public class OtherUtils {
  @SuppressWarnings("unchecked")
  public static <T> List<T> filter(List<?> list, Class<T> type) {
    List<T> result = new ArrayList<>();

    list.forEach(
        o -> {
          if (type.isAssignableFrom(o.getClass())) {
            result.add((T) o);
          }
        });

    return result;
  }
}
