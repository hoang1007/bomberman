package uet.gryffindor.game;

public class Manager {
  public static Manager INSTANCE = new Manager();

  private Game game;

  public void setGame(Game game) {
    this.game = game;
  }

  public Game getGame() {
    return this.game;
  }
}
