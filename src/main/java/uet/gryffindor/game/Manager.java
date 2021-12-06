package uet.gryffindor.game;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import uet.gryffindor.GameApplication;

public class Manager {
  public static Manager INSTANCE = new Manager();

  private Game game;
  private int highestScore;

  private Manager() {
    try {
      String raw = Files.readString(Paths.get(
        GameApplication.class.getResource("score.txt").toURI()
      ));

      highestScore = Integer.parseInt(raw);
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      highestScore = 0;
    }
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public Game getGame() {
    return this.game;
  }

  public int getHighestScore() {
    return this.highestScore;
  }

  /**
   * Cập nhật high score.
   * @param score
   */
  public void scoreLogging(int score) {
    if (score > highestScore) {
      highestScore = score;

      try {
        Files.write(Paths.get(
          GameApplication.class.getResource("score.txt").toURI()
        ), Integer.toString(highestScore).getBytes());
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }
}
