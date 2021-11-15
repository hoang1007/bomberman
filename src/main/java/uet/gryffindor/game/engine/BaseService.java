package uet.gryffindor.game.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseService {
  private static List<BaseService> services = new ArrayList<>();
   
  public abstract void update();

  public void destroy() {
    services.remove(this);
  }

  public static void run() {
    int oldSize = services.size();

    for (int i = 0; i < services.size(); i++) {
      services.get(i).update();

      int currentSize = services.size();

      if (currentSize < oldSize) {
        i -= oldSize - currentSize;
        oldSize = currentSize;
      }
    }
  }
}
