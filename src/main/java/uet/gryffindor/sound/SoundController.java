package uet.gryffindor.sound;//package uet.gryffindor.sound;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//
//import java.io.File;
//
//import java.io.IOException;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.DataLine;
//import javax.sound.sampled.FloatControl;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.SourceDataLine;
//import javax.sound.sampled.UnsupportedAudioFileException;
//
//class SoundMP3 {
//  private String path;
//  private Media sound;
//  private MediaPlayer mediaPlayer;
//
//  /**
//   * Constructor
//   *
//   * @param path gang dẫn file âm thanh mp3.
//   */
//  public SoundMP3(String path) {
//    this.path = path;
//  }
//
//  public void setPath(String path) {
//    this.path = path;
//  }
//
//  public Media getSound() {
//    return sound;
//  }
//
//  public MediaPlayer getMediaPlayer() {
//    return mediaPlayer;
//  }
//
//  /** Hàm khởi tạo âm thanh. */
//  public void initSound() {
//    try {
//      sound = new Media(new File(path).toURI().toString());
//      mediaPlayer = new MediaPlayer(sound);
//    } catch (Exception e) {
//      System.out.println("Error: ");
//    }
//  }
//
//  public void playAudio() {
//    mediaPlayer.play();
//  }
//
//  public void repeatAudio() {
//    mediaPlayer.setOnRepeat(
//        new Runnable() {
//          @Override
//          public void run() {}
//        });
//  }
//
//  public void pauseAudio() {
//    mediaPlayer.pause();
//  }
//
//  public void setVolume(double volume) {
//    mediaPlayer.setVolume(volume);
//  }
//
//  public double getVolume() {
//    return mediaPlayer.getVolume();
//  }
//}

 import javax.sound.sampled.AudioSystem;
 import javax.sound.sampled.Clip;
 import java.io.File;
 import java.net.URL;
 import javax.sound.sampled.AudioInputStream;
 //import sun.applet.Main;


 public class SoundController {
  public static void play(String sound) {
    new Thread(new Runnable() {
      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("./src/main/resources/uet/gryffindor/sound/" +sound + ".wav"));
              //GameApplication.class.getResourceAsStream("/sound/" + sound + ".wav"));
          clip.open(inputStream);
          clip.start();
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }).start();

  }
  public static void stop(String sound){
    new Thread(new Runnable() {
      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("./src/main/resources/uet/gryffindor/sound/" +sound + ".wav"));
              //GameApplication.class.getResourceAsStream("/sound/" + sound + ".wav"));
          clip.open(inputStream);
          clip.stop();
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }).start();
  }
 }

//class Sound extends Thread {
//
//  private String filename;
//
//  private Position curPosition;
//
//  private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
//
//  enum Position {
//    LEFT, RIGHT, NORMAL
//  };
//
//  public Sound(String wavfile) {
//    filename = wavfile;
//    curPosition = Position.NORMAL;
//  }
//
//  public Sound(String wavfile, Position p) {
//    filename = wavfile;
//    curPosition = p;
//  }
//
//  public void run() {
//
//    while(true)
//    {
//      File soundFile = new File(filename);
//      if (!soundFile.exists()) {
//        System.err.println("Wave file not found: " + filename);
//        return;
//      }
//
//      AudioInputStream audioInputStream = null;
//      try {
//        audioInputStream = AudioSystem.getAudioInputStream(soundFile);
//      } catch (UnsupportedAudioFileException e1) {
//        e1.printStackTrace();
//        return;
//      } catch (IOException e1) {
//        e1.printStackTrace();
//        return;
//      }
//
//      AudioFormat format = audioInputStream.getFormat();
//      SourceDataLine auline = null;
//      DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//
//      try {
//        auline = (SourceDataLine) AudioSystem.getLine(info);
//        auline.open(format);
//      } catch (LineUnavailableException e) {
//        e.printStackTrace();
//        return;
//      } catch (Exception e) {
//        e.printStackTrace();
//        return;
//      }
//
//      if (auline.isControlSupported(FloatControl.Type.PAN)) {
//        FloatControl pan = (FloatControl) auline
//            .getControl(FloatControl.Type.PAN);
//        if (curPosition == Position.RIGHT)
//          pan.setValue(1.0f);
//        else if (curPosition == Position.LEFT)
//          pan.setValue(-1.0f);
//      }
//
//      auline.start();
//      int nBytesRead = 0;
//      byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
//
//      try {
//        while (nBytesRead != -1) {
//          nBytesRead = audioInputStream.read(abData, 0, abData.length);
//          if (nBytesRead >= 0)
//            auline.write(abData, 0, nBytesRead);
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//        return;
//      } finally {
//        auline.drain();
//        auline.close();
//      }
//    }
//  }
//}
//
//public class SoundController {
//  public static Sound music;
//  public static SoundMP3 musicMp3;
//
//  public static void startWav(String s) {
//    music = new Sound(s);
//    music.start();
//  }
//
//  public static void startMp3(String s) {
//    musicMp3 = new SoundMP3(s);
//    musicMp3.initSound();
//    musicMp3.playAudio();
//  }
//
//  //public static void resume
//}
