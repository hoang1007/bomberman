package uet.gryffindor.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundController {
  private String path;
  private Media sound;
  private MediaPlayer mediaPlayer;

  /**
   * Constructor
   * @param path gang dẫn file âm thanh mp3.
   */
  public SoundController(String path) {
    this.path = path;
    this.initSound();
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Media getSound() {
    return sound;
  }

  public MediaPlayer getMediaPlayer() {
    return mediaPlayer;
  }

  /**
   * Hàm khởi tạo âm thanh.
   *
   */
  public void initSound() {
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
