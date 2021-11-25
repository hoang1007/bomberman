package uet.gryffindor.autopilot;

import java.util.HashMap;
import java.util.List;

import uet.gryffindor.game.Game;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.util.OtherUtils;

public class GameEnvironment {
  private Game game;
  public final int width;
  public final int height;

  private HashMap<Vector2D, Wall> wallMap = new HashMap<>();
  private Bomber agent;

  /** Initialize the game environment. */
  public GameEnvironment(Game game) {
    this.game = game;
    height = game.getPlayingMap().getWidth();
    width = game.getPlayingMap().getHeight();

    findWallMap();
  }

  private void findWallMap() {
    game.getPlayingMap().getObjects().forEach(obj -> {
      if (obj instanceof Wall) {
        wallMap.put(obj.position, (Wall) obj);
      } else if (obj instanceof Bomber) {
        agent = (Bomber) obj;
      }
    });
  }

  public HashMap<Vector2D, Wall> getWallMap() {
    return this.wallMap;
  }

  public <T extends GameObject> List<T> getObject(Class<T> clazz) {
    return OtherUtils.filter(game.getPlayingMap().getObjects(), clazz);
  }

  /** Get nearest object to player. */
  public <T extends GameObject> T getNearestObject(Class<T> clazz) {
    T nearestObj = null;
    double minDis = Double.MAX_VALUE;
    for (T obj : this.getObject(clazz)) {
      double dis = Vector2D.euclideanDistance(agent.position, obj.position);

      if (minDis > dis) {
        minDis = dis;
        nearestObj = obj;
      }
    }
    
    return nearestObj;
  }

  public Bomber getAgent() {
    return this.agent;
  }
  
  /** Restart the game. */
  public void restart() {
    game.start();
    game.getPlayingMap().getObjects().forEach(obj -> {
      if (obj instanceof Bomber) {
        agent = (Bomber) obj;
      }
    });
  }

  public Game getGame() {
    return this.game;
  }
}