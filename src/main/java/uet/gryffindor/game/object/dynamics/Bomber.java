package uet.gryffindor.game.object.dynamics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Game;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.FpsTracker;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.sound.SoundController;

public class Bomber extends DynamicObject {
  private IntegerProperty heart;
  private DoubleProperty speed;

  private Vector2D firstPosition;
  private Vector2D oldPosition;
  private boolean shieldAvail = true;

  private int numberOfBombs;
  private int bombDropped;
  private long delay;

  private List<Long> sinceDropping;
  private List<GameObject> blockedBy;

  @Override
  public void start() {
    heart = new SimpleIntegerProperty(3);
    int bomberId = Manager.INSTANCE.getGame().getConfig().getBomberId();
    var sprites = bomberId == 1 ? Sprite.player : Sprite.blackPlayer;
    this.setTexture(new AnimateTexture(this, 3, sprites));
    Manager.INSTANCE.getGame().getCamera().setFocusOn(this);
    speed = new SimpleDoubleProperty(200f);

    orderedLayer = OrderedLayer.MIDGROUND;
    oldPosition = position.clone();
    firstPosition = position.clone();

    numberOfBombs = 1;
    bombDropped = 0;
    sinceDropping = new ArrayList<>();
    blockedBy = new ArrayList<>();

    shieldAvail = true;
    TimeCounter.callAfter(() -> shieldAvail = false, 2, TimeUnit.SECONDS);
  }

  @Override
  public void update() {
    for (int i = 0; i < sinceDropping.size(); i++) {
      if (System.currentTimeMillis() - sinceDropping.get(i) >= Bomb.time + Explosion.time * 2) {
        sinceDropping.remove(sinceDropping.get(i));
        i--;
      }
    }

    if (sinceDropping.isEmpty()) {
      bombDropped = 0;
    }

    if (blockedBy.isEmpty()) {
      oldPosition = position.clone();
      move();
    }
  }

  private void move() {
    switch (Input.INSTANCE.getCode()) {
      case UP:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.y -= speed.get() * FpsTracker.fixedDeltaTime();
        texture.changeTo("up");
        break;
      case DOWN:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.y += speed.get() * FpsTracker.fixedDeltaTime();
        texture.changeTo("down");
        break;
      case RIGHT:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.x += speed.get() * FpsTracker.fixedDeltaTime();
        texture.changeTo("right");
        break;
      case LEFT:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.x -= speed.get() * FpsTracker.fixedDeltaTime();
        texture.changeTo("left");
        break;
      case SPACE:
        if (bombDropped < numberOfBombs && System.currentTimeMillis() - delay >= 100) {
          SoundController.INSTANCE.getSound(SoundController.BOMB_NEW).play(); // âm thanh đặt bom.
          bombDropped++;
          Bomb bomb = new Bomb();
          bomb.position.setValue(this.position.clone().smooth(Sprite.DEFAULT_SIZE, 1));
          GameObject.addObject(bomb);
          sinceDropping.add(System.currentTimeMillis());
          delay = System.currentTimeMillis();
        }
        break;
      default:
        texture.pause();
        break;
    }
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      // ngoại lệ đặt bomb
      if (this.collider.getOverlapArea(that) < .5 * this.dimension.x * this.dimension.y) {
        // nếu bomber va chạm với vật thể tĩnh
        // khôi phục vị trí trước khi va chạm
        position = oldPosition.smooth(this.dimension.x, 0.3);
        // gắn nhãn bị chặn
        blockedBy.add(that.gameObject);
      }
    } else if (that.gameObject instanceof Enemy) {
      if (!shieldAvail) {
        dead();
      }
    } else if (that.gameObject instanceof Explosion) {
      if (!shieldAvail) {
        dead();
      }
    }
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      blockedBy.remove(that.gameObject);
    }
  }

  private void dead() {
    heart.set(heart.get() - 1);
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.BOMBER_DIE).play(); // âm thanh chết.
    Game.pause = true;
    texture.changeTo("dead");

    if (heart.get() > 0) {
      TimeCounter.callAfter(() -> {
        SoundController.INSTANCE.stopAll();
        SoundController.INSTANCE.getSound(SoundController.PLAYGAME).play(); // âm thanh chết.
        this.position.setValue(firstPosition);
        Game.pause = false;
      }, 1, TimeUnit.SECONDS);
      return;
    }

    TimeCounter.callAfter(this::destroy, texture.getDuration("dead"));
    TimeCounter.callAfter(() -> {
      Manager.INSTANCE.getGame().destroy();
      GameApplication.setRoot("MenuOver");
    }, 1, TimeUnit.SECONDS);
  }

  public double getSpeed() {
    return speed.get();
  }

  public void setSpeed(double speed) {
    this.speed.set(speed);
  }

  public int getBombCount() {
    return this.numberOfBombs;
  }

  public void addHeart() {
    heart.set(heart.get() + 1);
  }

  public IntegerProperty getHeartProperty() {
    return this.heart;
  }

  public void setBombsCount(int bombCount) {
    this.numberOfBombs = bombCount;
  }
}