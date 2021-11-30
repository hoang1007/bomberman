package uet.gryffindor.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;

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
    try {
    sound = new Media(new File(path).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);
    } catch (Exception e) {
      System.out.println("Error: ");
    }
  }

  public void playAudio() {
    mediaPlayer.play();
  }

  public void repeatAudio() {
    mediaPlayer.setOnRepeat(new Runnable() {
      @Override
      public void run() {

      }
    });
  }

  public void pauseAudio() {
    mediaPlayer.pause();
  }

  public void setVolume(double volume) {
    mediaPlayer.setVolume(volume);
  }

  public double getVolume() {
    return mediaPlayer.getVolume();
  }
}


//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import java.io.File;
//import java.net.URL;
//import javax.sound.sampled.AudioInputStream;
//import sun.applet.Main;
//import javax.sound.sampled.*;
//
//public class Sound {
//  public static void play(String sound) {
//    new Thread(new Runnable() {
//      public void run() {
//        try {
//          Clip clip = AudioSystem.getClip();
//          AudioInputStream inputStream = AudioSystem.getAudioInputStream(
//              Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
//          clip.open(inputStream);
//          clip.start();
//        } catch (Exception e) {
//          System.err.println(e.getMessage());
//        }
//      }
//    }).start();
//
//  }
//  public static void stop(String sound){
//    new Thread(new Runnable() {
//      public void run() {
//        try {
//          Clip clip = AudioSystem.getClip();
//          AudioInputStream inputStream = AudioSystem.getAudioInputStream(
//              Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
//          clip.open(inputStream);
//          clip.stop();
//        } catch (Exception e) {
//          System.err.println(e.getMessage());
//        }
//      }
//    }).start();
//  }
//}
