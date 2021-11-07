package uet.gryffindor;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App.
 */
public class Game extends Application {
  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFXML("start"));
    stage.setScene(scene);
    stage.show();
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    return FXMLLoader.load(Game.class.getResource(fxml + ".fxml"));
  }

  public static void main(String[] args) {
    launch(args);
  }
}