package uet.gryffindor.autopilot;

import java.util.HashMap;
import java.util.List;

import uet.gryffindor.game.Game;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.object.dynamics.Bomber;
import uet.gryffindor.game.object.statics.Wall;
import uet.gryffindor.util.OtherUtils;

public class GameEnvironment {
  private Game game;
  public final int width;
  public final int height;

  private HashMap<String, Wall> wallMap = new HashMap<>();
  private Bomber agent;

  public GameEnvironment(Game game) {
    this.game = game;
    height = game.getPlayingMap().getWidth();
    width = game.getPlayingMap().getHeight();

    findWallMap();
  }

  private void findWallMap() {
    game.getPlayingMap().getObjects().forEach(obj -> {
      if (obj instanceof Wall) {
        wallMap.put(obj.position.toString(), (Wall) obj);
      } else if (obj instanceof Bomber) {
        agent = (Bomber) obj;
      }
    });
  }

  public HashMap<String, Wall> getWallMap() {
    return this.wallMap;
  }

  public <T extends GameObject> List<T> getObject(Class<T> clazz) {
    return OtherUtils.filter(game.getPlayingMap().getObjects(), clazz);
  }

  public Bomber getAgent() {
    return this.agent;
  }
  
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