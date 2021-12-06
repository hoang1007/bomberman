package uet.gryffindor.scenes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.sound.SoundController;
import javafx.scene.control.Label;


import java.awt.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class WinSceneController {

  @FXML private Label outScore;




//  public static void ReadFromFile() {
//    // url file highScore
//    String url = "src/main/resources/uet/gryffindor/hightScores.txt";
//    // Đọc dữ liệu từ File với BufferedReader.
//    FileInputStream fileInputStream = null;
//    BufferedReader bufferedReader = null;
//    int i = 0;
//    try {
//      fileInputStream = new FileInputStream(url);
//      bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//      String line = bufferedReader.readLine();
//
//      while (line != null) {
//
//
//          String[] inLine = line.split("-");
//          nameList[i] = inLine[0];
//          //scoreList[i] = Integer.parseInt(inLine[1]);
//          scoreList[i] = inLine[1];
//
//        line = bufferedReader.readLine();;
//        i++;
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    } finally {
//      // Đóng file.
//      try {
//        bufferedReader.close();
//        fileInputStream.close();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }

  @FXML
  public void initialize() {
    setScore();
    SoundController.INSTANCE.getSound(SoundController.WIN_BG).loop();
  }

  public void setScore() {
    outScore.setText(String.valueOf(MainSceneController.score));
  }

  public void backToMenu() {
    MainSceneController.game.destroy();
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    SoundController.INSTANCE.getSound(SoundController.MENU).loop();
    GameApplication.setRoot("menu");
  }

  public void quitGame() {
    SoundController.INSTANCE.getSound(SoundController.CLICK).play();
    Platform.exit();
    System.exit(0);
  }

}
