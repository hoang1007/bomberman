package uet.gryffindor.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.BaseService;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.Texture;
import uet.gryffindor.scenes.MainSceneController;
import uet.gryffindor.util.SortedList;

import java.util.List;

public class Game {
  private AnimationTimer timer;
  private Map playingMap;
  private Camera camera;
  private GraphicsContext context;
  private Config config;
  public static boolean pause = false;

  public Game(Canvas canvas) {
    FpsTracker.setFps(30);
    context = canvas.getGraphicsContext2D();
    camera = new Camera(canvas);

    timer = new AnimationTimer() {

      @Override
      public void handle(long now) {
        if (FpsTracker.isNextFrame(now)) {
          BaseService.run();
          update();
          Collider.checkCollision(playingMap.getObjects());
          render();
        }
      }
    };
  }

  public Game(Canvas canvas, Config config) {
    FpsTracker.setFps(30);
    context = canvas.getGraphicsContext2D();
    camera = new Camera(canvas);
    this.config = config;

    timer = new AnimationTimer() {

      @Override
      public void handle(long now) {
        if (FpsTracker.isNextFrame(now)) {
          BaseService.run();
          if (!pause) {
            update();
            Collider.checkCollision(playingMap.getObjects());
          }
          render();
        }
      }
    };
  }

  public void start() {
    if (playingMap == null) {
      this.setMap(Map.getByLevel(1));
    } else {
      this.setMap(Map.getByLevel(playingMap.getLevel() + 1));
    }
    MainSceneController.level = playingMap.getLevel();
    pause = false;
    SortedList<GameObject> objects = playingMap.getObjects();

    // Gọi phương thức khởi tạo thuộc tính
    // Bao gồm cả trường hợp kích thước của list thay đổi
    for (int i = 0; i < objects.size(); i++) {
      int oldSize = objects.size();
      objects.get(i).start();
      int curSize = objects.size();

      if (curSize > oldSize) {
        var obj = objects.getLastElement();
        if (obj.first <= i) {
          i += 1;
        }
      }
    }

    playingMap.getObjects().sort();
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

    playingMap
        .getObjects()
        .forEach(
            obj -> {
              Texture t = obj.getTexture();

              if (t != null) {
                t.render(context, camera);
              }
            });
  }

  public void setMap(Map map) {
    playingMap = map;
    camera.setRange(new Vector2D(map.getWidth(), map.getHeight()));
    GameObject.setMap(map);

    System.out.println("New map " + this.getClass());
  }

  public Camera getCamera() {
    return this.camera;
  }

  public Map getPlayingMap() {
    return this.playingMap;
  }

  public AnimationTimer getTime() {
    return this.timer;
  }

  public void nextLevel() {
    int level = playingMap != null ? playingMap.getLevel() : 1;
    setMap(Map.getByLevel(level));
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }
}
