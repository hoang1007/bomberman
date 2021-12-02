//GameSound.getIstance().stop();
//    GameSound.getIstance().getAudio(GameSound.LOSE).play();
package uet.gryffindor.sound;
import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;

public class SoundInGame {
  public static SoundInGame instance;

  public static final String MENU = "menu.wav";
  public static final String PLAYGAME = "playgame.wav";
  public static final String BOMB = "newbomb.wav";
  public static final String BOMBER_DIE = "bomber_die.wav";
  public static final String BOMBER_DieDRINK = "bomDrink.wav";
  public static final String MONSTER_DIE = "monster_die.wav";
  public static final String BONG_BANG = "bomb_bang.wav";
  public static final String ITEM = "item.wav";
  public static final String WIN = "win.wav";
  public static final String LOSE = "lose.mid";
  public static final String FOOT = "foot.wav";
  private HashMap<String, AudioClip> audioMap;

  public SoundInGame() {
    audioMap = new HashMap<>();
    loadAllAudio();
  }

  public static SoundInGame getIstance() {
    if (instance == null) {
      instance = new SoundInGame();
    }
    return instance;
  }

  public void loadAllAudio() {
    putAudio(MENU);
    putAudio(PLAYGAME);
    putAudio(BOMB);
    putAudio(MONSTER_DIE);
    putAudio(BOMBER_DieDRINK);
    putAudio(BOMBER_DIE);
    putAudio(BONG_BANG);
    putAudio(ITEM);
    putAudio(WIN);
    putAudio(LOSE);
    putAudio(FOOT);
  }

  public void stop() {
    getAudio(MENU).stop();
    getAudio(PLAYGAME).stop();
    getAudio(BOMB).stop();
    getAudio(MONSTER_DIE).stop();
    getAudio(BOMBER_DieDRINK).stop();
    getAudio(BOMBER_DIE).stop();
    getAudio(BONG_BANG).stop();
    getAudio(ITEM).stop();
    getAudio(WIN).stop();
    getAudio(LOSE).stop();
  }

  public void putAudio(String name) {
    AudioClip auClip = Applet.newAudioClip(SoundInGame.class.getResource(name));
    audioMap.put(name, auClip);
  }

  public AudioClip getAudio(String name) {
    return audioMap.get(name);
  }
}

/*
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static uet.oop.bomberman.audio.MyAudioPlayer.Loopable.NONELOOP;

public class MyAudioPlayer implements Runnable {

    // Tên file các audio
    public static final String BACKGROUND_MUSIC = "bg";
    public static final String PLACE_BOMB = "place_bomb";
    public static final String POWER_UP = "power_up";
    public static final String EXPLOSION = "explosion";
    public static final String DEAD = "dead";

    private Clip clip;

    public enum Loopable {
        NONELOOP,
        LOOP;
    }

    // Mặc định không phát lại
    private Loopable loopable = NONELOOP;

    public MyAudioPlayer(String fileName) {
        String path = "/audio/" + fileName + ".wav";

        try {
            URL defaultSound = getClass().getResource(path);
            AudioInputStream sound = AudioSystem.getAudioInputStream(defaultSound);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }

    public Loopable getLoopable() {
        return loopable;
    }

    public void setLoopable(Loopable loopable) {
        this.loopable = loopable;
    }

    public void play(){
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

    @Override
    public void run() {
        switch (loopable) {
            case LOOP:
                this.loop();
                break;
            case NONELOOP:
                this.play();
                break;
        }
    }
}

 */