package uet.gryffindor.sound;

import java.util.HashMap;
public class SoundController {
  public static SoundController INSTANCE = new SoundController();

  public static final String MENU = "MainMenu";
  public static final String MENU_OVER = "MenuOver";
  public static final String PLAYGAME = "SoundTrack";
  public static final String BOMB_NEW = "NewBom";
  public static final String BOMB_BROKEN = "BomBroken";
  public static final String ENEMY_DIE = "EnemyDie";
  public static final String BOMBER_DIE = "monster_die.wav";
  public static final String BONG_BANG = "bomb_bang.wav";
  public static final String ITEM = "Item";
  public static final String WIN = "win.wav";
  public static final String LOSE = "lose.mid";
  public static final String FOOT = "Foot";

  private HashMap<String, SoundInGame> soundList;


  public SoundController() {
      soundList = new HashMap<>();
      loadAll();
  }

  public static SoundController getINSTANCE() {
    if (INSTANCE == null) {
      INSTANCE = new SoundController();
    }
    return INSTANCE;
  }

  public void loadAll() {
    soundList.put(MENU, new SoundInGame(MENU));
    soundList.put(MENU_OVER, new SoundInGame(MENU_OVER));
    soundList.put(PLAYGAME, new SoundInGame(PLAYGAME));
    //soundList.put(BOMBER_DIE, new SoundInGame(BOMBER_DIE));
    soundList.put(ENEMY_DIE, new SoundInGame(ENEMY_DIE));
    soundList.put(BOMB_NEW, new SoundInGame(BOMB_NEW));
    soundList.put(BOMB_BROKEN, new SoundInGame(BOMB_BROKEN));
    soundList.put(ITEM, new SoundInGame(ITEM));
    soundList.put(FOOT, new SoundInGame(FOOT));
  }

  public SoundInGame getSound(String type) {
    return soundList.get(type);
  }

  public void stopAll() {
    soundList.get(MENU).stop();
    soundList.get(MENU_OVER).stop();
    soundList.get(PLAYGAME).stop();
    soundList.get(ENEMY_DIE).stop();
    soundList.get(BOMB_NEW).stop();
    soundList.get(BOMB_BROKEN).stop();
    soundList.get(ITEM).stop();
    soundList.get(FOOT).stop();
  }
}