package uet.gryffindor.base;

/**
 * Giúp sắp xếp các game object theo một trật tự render.
 * <p> {@link #BACKGROUND} được ưu tiên render đầu tiên.
 * <p> {@link #MIDGROUND} được render thứ hai.
 * <p> {@link #FOREGROUND} được render cuối cùng.
 */
public enum OrderedLayer {
  BACKGROUND,
  MIDGROUND,
  FOREGROUND
}
