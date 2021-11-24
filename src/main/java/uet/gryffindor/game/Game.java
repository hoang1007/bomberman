package uet.gryffindor.game;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.gryffindor.GameApplication;
import uet.gryffindor.autopilot.EpsilonGreedyPolicy;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.engine.BaseService;
import uet.gryffindor.game.engine.Camera;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.Texture;

public class Game {
  private AnimationTimer timer;
  private Map playingMap;
  private Camera camera;
  private GraphicsContext context;
  private EpsilonGreedyPolicy policy;

  public Game(Canvas canvas) {
    FpsTracker.setFps(30);
    context = canvas.getGraphicsContext2D();
    camera = new Camera(canvas);

    policy = new EpsilonGreedyPolicy(false);
    GameApplication.onExit(policy::save);

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

  public void start() {
    this.setMap(Map.getByLevel(1));
    policy.initialize(this);

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
    camera.setRange(new Vector2D(map.getWidth(), map.getHeight()).multiply(Sprite.DEFAULT_SIZE));
    GameObject.setMap(map);
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
