package uet.gryffindor.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;
import uet.gryffindor.game.object.Bomber;
import uet.gryffindor.game.object.Grass;
import uet.gryffindor.game.object.Portal;

public class Game {
  private AnimationTimer timer;
  private GraphicsContext context;
  
  public Game(GraphicsContext context) {
    this.context = context;
    
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (FpsTracker.isNextFrame(now)) {
          update();
          Collider.checkCollision();
          render();
        }
      }
    };
  }

  public void start() {
    // add Object
    GameObject.objects.add(new Bomber());
    GameObject.objects.add(new Portal());
    GameObject.objects.add(new Grass());

    timer.start();
  }

  private void update() {
    int currentSize = GameObject.objects.size();
    for (int i = 0; i < GameObject.objects.size(); i++) {
      GameObject.objects.get(i).update();

      // cập nhật cho trường hợp update có hủy object
      int updateSize = GameObject.objects.size();

      if (currentSize > updateSize) {
        i = i - (currentSize - updateSize);
        currentSize = updateSize;
      }
    }
  }

  private void render() {
    context.clearRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
    GameObject.objects.forEach(obj -> obj.render(context));
  }

  public void clear() {
    GameObject.objects.clear();
    timer.stop();
  }
}
