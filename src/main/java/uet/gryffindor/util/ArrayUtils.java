package uet.gryffindor.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class ArrayUtils {
  public static <T> T[] concat(T[] a, T[] b) {
    @SuppressWarnings("unchecked")
    T[] ret = Stream.concat(Arrays.stream(a), Arrays.stream(b))
        .toArray(size -> (T[]) Array.newInstance(a.getClass().getComponentType(), size));

    return ret;
  }
}
