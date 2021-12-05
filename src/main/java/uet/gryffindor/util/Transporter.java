package uet.gryffindor.util;

import java.util.HashMap;

public class Transporter {
    public static final Transporter INSTANCE = new Transporter();

    private HashMap<String, Object> data = new HashMap<>();

    /**
     * Lấy dữ liệu theo kiểu dữ liệu
     * @param <T> kiểu dữ liệu
     * @param key
     * @param type
     * @return dữ liệu nếu có.
     */
    public <T> T get(String key, Class<T> type) {
        Object value = data.get(key);

        if (value == null || !type.isInstance(value)) {
            return null;
        } else {
            return type.cast(value);
        }
    }

    public Object getRaw(String key) {
        return data.get(key);
    }

    public void put(String key, Object value, boolean unique) {
        if (unique && data.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate key: " + key);
        } else {
            data.put(key, value);
        }
    }
}
