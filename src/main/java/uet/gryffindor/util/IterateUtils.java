package uet.gryffindor.util;

import java.util.List;

public class IterateUtils {
    public static <T> T getItem(List<?> iterable, Class<T> type) {
        for (Object item : iterable) {
            if (type.isInstance(item)) {
                return type.cast(item);
            }
        }

        return null;
    }
}
