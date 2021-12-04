package uet.gryffindor.game.object.dynamics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import uet.gryffindor.GameApplication;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.dynamics.enemy.Enemy;
import uet.gryffindor.game.object.statics.items.BombItem;
import uet.gryffindor.game.object.statics.items.FlameItem;
import uet.gryffindor.game.object.statics.items.HeartItem;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.game.object.statics.items.SpeedItem;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;
import uet.gryffindor.sound.SoundController;

public class Bomber extends DynamicObject {
  private int heart;
  private DoubleProperty speed;

  private boolean isBlocked = false;
  private Vector2D oldPosition;

  private int numberOfBombs;
  private int bombDropped;
  private long delay;

  private List<Long> sinceDropping;
  private List<Item> effectItems;

  @Override
  public void start() {
    heart = 1;
    this.setTexture(new AnimateTexture(this, 3, Sprite.blackPlayer));
    Manager.INSTANCE.getGame().getCamera().setFocusOn(this);
    speed = new SimpleDoubleProperty(8f);

    orderedLayer = OrderedLayer.MIDGROUND;
    oldPosition = position.clone();

    numberOfBombs = 1;
    bombDropped = 0;
    sinceDropping = new ArrayList<>();
    effectItems = new ArrayList<>();

  }

  @Override
  public void update() {
    System.out.println("Heart: " + heart);
    System.out.println("Speed: " + speed);
    System.out.println("bomb radius: " + Bomb.explosionRadius);
    System.out.println("NUMBER of Bombs: " + numberOfBombs);

    for (int i = 0; i < sinceDropping.size(); i++) {
      if (System.currentTimeMillis() - sinceDropping.get(i) >= Bomb.time) {
        sinceDropping.remove(sinceDropping.get(i));
        i--;
      }
    }

    for (int i = 0; i < effectItems.size(); i++) {
      Item item = effectItems.get(i);
      if (item.timeOut()) {
        if (item instanceof BombItem) {
          numberOfBombs -= BombItem.power;
        } else if (item instanceof SpeedItem) {
          speed.setValue(speed.getValue() - SpeedItem.power);
        } else if (item instanceof FlameItem) {
          Bomb.explosionRadius -= FlameItem.power;
        }
        effectItems.remove(item);
        i--;
      }

    }

    if (sinceDropping.isEmpty()) {
      bombDropped = 0;
    }

    if (!isBlocked) {
      oldPosition = position.clone();
      move();
    }
  }

  private void move() {
    switch (Input.INSTANCE.getCode()) {
      case UP:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.y -= speed.get();
        texture.changeTo("up");
        break;
      case DOWN:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.y += speed.get();
        texture.changeTo("down");
        break;
      case RIGHT:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.x += speed.get();
        texture.changeTo("right");
        break;
      case LEFT:
        SoundController.INSTANCE.getSound(SoundController.FOOT).play();
        this.position.x -= speed.get();
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
    if (that.gameObject instanceof Unmovable && !(that.gameObject instanceof Bomb)) {
      // ngoại lệ đặt bomb
      if (this.collider.getOverlapArea(that) < .5 * this.dimension.x * this.dimension.y) {
        // nếu bomber va chạm với vật thể tĩnh
        // khôi phục vị trí trước khi va chạm
        position = oldPosition.smooth(this.dimension.x, 0.3);
        // gắn nhãn bị chặn
        isBlocked = true;
      }
    } else if (that.gameObject instanceof Item) {
      SoundController.INSTANCE.getSound(SoundController.ITEM).play(); // âm thanh ăn item.
      if (that.gameObject instanceof BombItem) {
        numberOfBombs += BombItem.power;
      } else if (that.gameObject instanceof SpeedItem) {
        speed.setValue(speed.getValue() + SpeedItem.power);
      } else if (that.gameObject instanceof FlameItem) {
        Bomb.explosionRadius += FlameItem.power;
      } else if (that.gameObject instanceof HeartItem) {
        heart += HeartItem.power;
      }

      if (!(that.gameObject instanceof HeartItem)) {
        effectItems.add((Item) that.gameObject);
      }
      ((Item) that.gameObject).startedCounting();
      that.gameObject.destroy();
    }
    else if (that.gameObject instanceof Enemy) {
      dead();
    } else if (that.gameObject instanceof Explosion) {
      dead();
    }
  }

  public void dead() {
    isBlocked = true;
    texture.changeTo("dead");
    heart--;
    SoundController.INSTANCE.stopAll();
    SoundController.INSTANCE.getSound(SoundController.BOMBER_DIE).play(); // âm thanh chết.
    TimeCounter.callAfter(this::destroy, texture.getDuration("dead"));
    //TimeCounter.callAfter(GameApplication.setRoot("MenuOver"), 1000, TimeUnit.SECONDS);
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      isBlocked = false;
    }
  }
}