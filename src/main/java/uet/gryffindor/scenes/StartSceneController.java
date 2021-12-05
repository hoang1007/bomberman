package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Config;
import uet.gryffindor.sound.SoundController;
import uet.gryffindor.util.Transporter;

public class StartSceneController {
  private Config config = new Config();

  @FXML
  private Button bomberButton;
  @FXML
  private HBox selectionBar;

  public void initialize() {
    selectionBar.managedProperty().bind(selectionBar.visibleProperty());

    bomberButton.setOnAction(e -> {
      selectionBar.setVisible(true);
    });
  }

  @FXML
  private void startGame() {
    Transporter.INSTANCE.put("config", config, false);
    // GameApplication.setRoot("main");
    GameApplication.setRoot("ingame");
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.PLAYGAME).loop();
  }

  @FXML
  public void hightScore() {
    System.out.println(this.getClass());
    System.out.println("ẤN ĐÚNG RỒI ĐẤY. CHOI NGU MÀ CÒN DOI XEM ĐIỂM CAO.");
    SoundController.INSTANCE.stopAll();
  }

  @FXML
  public void selectionBomber1() {
    config.setBomberId(1);
    selectionBar.setVisible(false);
  }

  @FXML
  public void selectionBomber2 () {
    config.setBomberId(2);
    selectionBar.setVisible(false);
  }

  @FXML
  public void zoomInOnHover(MouseEvent e) {
    ImageView view = (ImageView) e.getSource();

    double width = view.getFitWidth();
    double height = view.getFitHeight();

    view.setFitWidth(1.5 * width);
    view.setFitHeight(1.5 * height);
  }

  @FXML
  public void zoomOutOnHover(MouseEvent e) {
    ImageView view = (ImageView) e.getSource();

    double width = view.getFitWidth();
    double height = view.getFitHeight();

    view.setFitWidth(width / 1.5);
    view.setFitHeight(height / 1.5);
  }

  @FXML
  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }


}
