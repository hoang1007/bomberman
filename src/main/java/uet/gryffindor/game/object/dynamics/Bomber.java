package uet.gryffindor.game.object.dynamics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.DynamicObject;
import uet.gryffindor.game.object.statics.items.BombItem;
import uet.gryffindor.game.object.statics.items.FlameItem;
import uet.gryffindor.game.object.statics.items.HeartItem;
import uet.gryffindor.game.object.statics.items.Item;
import uet.gryffindor.game.object.statics.items.SpeedItem;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.AnimateTexture;

public class Bomber extends DynamicObject {
  private int heart;
  private DoubleProperty speed;

  private boolean isBlocked = false;
  private Vector2D oldPosition;

  private int numberOfBombs;
  private int bombDropped;
  private long delay;

  private List<Long> sinceDropping;
  private HashMap<String, Long> itemsTime;

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

    itemsTime = new HashMap<>();
    itemsTime.put("SpeedItem", (long) 0);
    itemsTime.put("FlameItem", (long) 0);
    itemsTime.put("BombItem", (long) 0);
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

    for (Map.Entry<String, Long> e : itemsTime.entrySet()) {
      if (e.getValue() != 0) {
        if (System.currentTimeMillis() - e.getValue() >= Item.effectDuration) {
          System.out.println("RESET<" + e.getKey() + ">");
          itemsTime.replace(e.getKey(), (long) 0);
          if (e.getKey().equals("SpeedItem")) {
            speed.setValue(8.f);
          } else if (e.getKey().equals("FlameItem")) {
            Bomb.explosionRadius = 1;
          } else if (e.getKey().equals("BombItem")) {
            numberOfBombs = 1;
          }
        }
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
        this.position.y -= speed.get();
        texture.changeTo("up");
        break;
      case DOWN:
        this.position.y += speed.get();
        texture.changeTo("down");
        break;
      case RIGHT:
        this.position.x += speed.get();
        texture.changeTo("right");
        break;
      case LEFT:
        this.position.x -= speed.get();
        texture.changeTo("left");
        break;
      case SPACE:
        if (bombDropped < numberOfBombs && System.currentTimeMillis() - delay >= 100) {
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
      if (this.collider.getOverlapArea(that) < this.dimension.x * this.dimension.y) {
        // nếu bomber va chạm với vật thể tĩnh
        // khôi phục vị trí trước khi va chạm
        position = oldPosition.smooth(this.dimension.x, 0.3);
        // gắn nhãn bị chặn
        isBlocked = true;
      }
    } else if (that.gameObject instanceof Item) {
      if (that.gameObject instanceof BombItem) {
        numberOfBombs++;
        itemsTime.replace("BombItem", System.currentTimeMillis());
      } else if (that.gameObject instanceof SpeedItem) {
        speed.setValue(speed.getValue() + 4.f);
        itemsTime.replace("SpeedItem", System.currentTimeMillis());
      } else if (that.gameObject instanceof FlameItem) {
        Bomb.explosionRadius *= 2;
        itemsTime.replace("FlameItem", System.currentTimeMillis());
      } else if (that.gameObject instanceof HeartItem) {
        System.out.println("EAT HEART");
        heart++;
      }
      that.gameObject.destroy();
    }
    // else if (that.gameObject instanceof Enemy) {
    // dead();
    // } else if (that.gameObject instanceof Explosion) {
    // dead();
    // }
  }

  public void dead() {
    isBlocked = true;
    texture.changeTo("dead");
    heart--;
    TimeCounter.callAfter(this::destroy, texture.getDuration("dead"));
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      isBlocked = false;
    }
  }
}