package uet.gryffindor.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundController {
  private String path;
  private Media sound;
  private MediaPlayer mediaPlayer;

  /**
   * Hàm khởi tạo âm thanh.
   * @param path đường dẫn file âm thanh mp3.
   */
  public void initSound(String path) {
    sound = new Media(new File(path).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);
  }

  public void playAudio() {
    mediaPlayer.play();
  }

  public void pauseAudio() {
    mediaPlayer.pause();
  }

}
