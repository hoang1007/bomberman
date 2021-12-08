package uet.gryffindor.game;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.engine.BaseService;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;
import uet.gryffindor.game.map.Frozen;
import uet.gryffindor.game.map.LargeDungeon;
import uet.gryffindor.game.map.Map;
import uet.gryffindor.game.map.TinyDungeon;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.graphic.texture.Texture;
import uet.gryffindor.util.VoidFunction;

public class Game {
  private AnimationTimer timer;
  private Map playingMap;
  private Camera camera;
  private GraphicsContext context;
  private Config config;
  public static boolean pause = false;

  private IntegerProperty score;
  private VoidFunction displayInfo;

  /**
   * Khởi tạo game.
   * @param canvas khung canvas
   */
  public Game(Canvas canvas) {
    FpsTracker.setFps(30);
    context = canvas.getGraphicsContext2D();
    camera = new Camera(canvas);
    score = new SimpleIntegerProperty();
    
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

  /**
   * Khởi tạo game.
   * @param canvas khung canvas
   * @param config config của game
   */
  public Game(Canvas canvas, Config config) {
    FpsTracker.setFps(30);
    context = canvas.getGraphicsContext2D();
    camera = new Camera(canvas);
    score = new SimpleIntegerProperty();
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

  /** Bắt đầu game loop. */
  public void start() {
    if (playingMap == null) {
      this.setMap(new LargeDungeon());
    } else {
      this.setMap(nextLevel());
    }

    pause = false;
    BaseService.clear();
    playingMap.init();
    timer.start();
    displayInfo.invoke();
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

  private void setMap(Map map) {
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

  private Map nextLevel() {
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
      default:
        nextMap = new TinyDungeon();
    }

    return nextMap;
  }

  public void destroy() {
    timer.stop();
    Manager.INSTANCE.scoreLogging(score.get());
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public void addScore(int reward) {
    score.set(score.get() + reward);
  }

  public int getScore() {
    return score.get();
  }

  /** Liên kết các thông tin muốn hiển thị. */
  public void bindDisplayInfo(Label scoreLabel, Label levelLabel, Label heartLabel) {
    this.displayInfo = () -> {
      try {
        scoreLabel.textProperty().bind(score.asString());
        levelLabel.setText(Integer.toString(playingMap.getLevel()));
        heartLabel.textProperty().bind(
            playingMap.getObject(Bomber.class).getHeartProperty().asString());
      } catch (NullPointerException e) {
        System.out.println("Labels must not be null.");
      }
    };
  }

  public void stopTime() {
    timer.stop();
  }
}
