package uet.gryffindor.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;

public class Game {
  private AnimationTimer timer;
  private GraphicsContext context;
  private Map playingMap;
  
  public Game(GraphicsContext context) {
    this.context = context;
    FpsTracker.setFps(60);
    
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
    this.setMap(Map.getByLevel(1));

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
    GameObject.clear();
    timer.stop();
  }

  public void setMap(Map map) {
    playingMap = map;
    GameObject.clear();
    GameObject.objects.addAll(map.getObjects());

    System.out.println("New map");
  }

  public Map getPlayingMap() {
    return this.playingMap;
  }

  public void nextLevel() {
    int level = playingMap != null ? playingMap.getLevel() : 1;
    setMap(Map.getByLevel(level));
  }
}
