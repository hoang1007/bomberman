package uet.gryffindor.autopilot;

import uet.gryffindor.game.Game;

public class GameEnvironment {
  private Game game;
  private int width;
  private int height;

  public GameEnvironment(Game game) {
    this.game = game;
    height = game.getPlayingMap().getRawMap().length;
    width = game.getPlayingMap().getRawMap()[0].length;
  }

  public void restart() {
    game.start();
  }

  public Game getGame() {
    return this.game;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }
}
