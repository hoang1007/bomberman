package uet.gryffindor;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import uet.gryffindor.sound.SoundController;

/** JavaFX App. */
public class GameApplication extends Application {
  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    // scene = new Scene(loadFXML("WinScene"));
    scene = new Scene(loadFxml("menu"));
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
    stage.setScene(scene);
    stage.show();
  }

  /** Chuyển scene. */
  public static void setRoot(String fxml) {
    try {
      scene.setRoot(loadFxml(fxml));
    } catch (IOException e) {
      System.out.println("Can not load resource from fxml: " + e.getMessage());
    }
  }

  private static Parent loadFxml(String fxml) throws IOException {
    return FXMLLoader.load(GameApplication.class.getResource(fxml + ".fxml"));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
