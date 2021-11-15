package uet.gryffindor.game;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.BaseService;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;
import uet.gryffindor.graphic.texture.Texture;

public class Game {
  private AnimationTimer timer;
  private Map playingMap;
  private Camera camera;
  private GraphicsContext context;
  
  public Game(Canvas canvas) {
    FpsTracker.setFps(30);
    camera = new Camera(new Vector2D(canvas.getWidth(), canvas.getHeight()));
    context = canvas.getGraphicsContext2D();

    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (FpsTracker.isNextFrame(now)) {
          BaseService.run();
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
    List<GameObject> objects = playingMap.getObjects();
    
    int currentSize = objects.size();
    for (int i = 0; i < objects.size(); i++) {
      objects.get(i).update();

      // cập nhật cho trường hợp update có hủy object
      int updateSize = objects.size();

      if (currentSize > updateSize) {
        i -= (currentSize - updateSize);
        currentSize = updateSize;
      }
    }
  }

  private void render() {
    context.clearRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());

    playingMap.getObjects().forEach(obj -> {
      Texture t = obj.getTexture();

      if (t != null) {
        t.render(context, camera);
      }
    });
  }

  public void setMap(Map map) {
    playingMap = map;
    GameObject.setMap(map);

    System.out.println("New map");
  }

  public Camera getCamera() {
    return this.camera;
  }

  public Map getPlayingMap() {
    return this.playingMap;
  }

  public void nextLevel() {
    int level = playingMap != null ? playingMap.getLevel() : 1;
    setMap(Map.getByLevel(level));
  }
}
