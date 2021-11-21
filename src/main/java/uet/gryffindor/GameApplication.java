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
public class GameApplication extends Application {
  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFXML("main"));
    stage.setScene(scene);
    stage.show();
  }

  public static void setRoot(String fxml) {
    try {
      scene.setRoot(loadFXML(fxml));
    } catch (IOException e) {
      System.out.println("Can not load resource from fxml: " + e.getMessage());
    }
  }

  private static Parent loadFXML(String fxml) throws IOException {
    return FXMLLoader.load(GameApplication.class.getResource(fxml + ".fxml"));
  }

  public static void main(String[] args) {
    launch(args);
  }
}