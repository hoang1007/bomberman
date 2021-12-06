package uet.gryffindor.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.engine.BaseService;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;
import uet.gryffindor.game.map.LargeDungeon;
import uet.gryffindor.game.map.TinyDungeon;
import uet.gryffindor.game.map.Frozen;
import uet.gryffindor.game.map.Map;
import uet.gryffindor.graphic.texture.Texture;
import uet.gryffindor.scenes.MainSceneController;

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
            render();
          }
        }
      }
    };
  }

  public void start() {
    if (playingMap == null) {
      this.setMap(new TinyDungeon());
    } else {
      this.setMap(nextLevel());
    }
    MainSceneController.level = playingMap.getLevel();
    pause = false;
    playingMap.init();
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
    camera = new Camera(context.getCanvas(), map);
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

  public Map nextLevel() {
    int level = (playingMap != null ? playingMap.getLevel() : 1) + 1;
    Map nextMap = null;

    switch (level) {
      case 1:
        nextMap = new TinyDungeon();
        break;
      case 2:
        nextMap = new LargeDungeon();
        break;
      case 3:
        nextMap = new Frozen();
        break;
    }

    return nextMap;
  }

  public void destroy() {
    timer.stop();
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }
}
