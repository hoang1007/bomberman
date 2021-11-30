package uet.gryffindor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.Stage;
import uet.gryffindor.sound.SoundInGame;

import java.io.File;
import java.io.IOException;

/** JavaFX App. */
public class GameApplication extends Application {
  private static Scene scene;
  private static SoundInGame soundInGame;

  @Override
  public void start(Stage stage) throws IOException {
    //scene = new Scene(loadFXML("main"));
    scene = new Scene(loadFXML("ingame"));
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
