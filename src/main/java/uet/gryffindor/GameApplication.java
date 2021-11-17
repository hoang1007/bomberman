package uet.gryffindor;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.gryffindor.util.ExecuteFunction;

/**
 * JavaFX App.
 */
public class GameApplication extends Application {
  private static Scene scene;
  private static ExecuteFunction func;

  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFXML("main"));
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> func.invoke());
  }

  public static void setRoot(String fxml) {
    try {
      scene.setRoot(loadFXML(fxml));
    } catch (IOException e) {
      System.out.println("Can not load resource from fxml: " + e.getMessage());
    }
  }

  public static void onExit(ExecuteFunction func) {
    GameApplication.func = func;
  }

  private static Parent loadFXML(String fxml) throws IOException {
    return FXMLLoader.load(GameApplication.class.getResource(fxml + ".fxml"));
  }

  // public static void main(String[] args) {
  //   launch(args);
  // }
}