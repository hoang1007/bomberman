package uet.gryffindor.game.object.dynamics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import uet.gryffindor.game.Manager;
import uet.gryffindor.game.base.GameObject;
import uet.gryffindor.game.base.OrderedLayer;
import uet.gryffindor.game.base.Status;
import uet.gryffindor.game.base.Vector2D;
import uet.gryffindor.game.behavior.Unmovable;
import uet.gryffindor.game.engine.Collider;
import uet.gryffindor.game.engine.Input;
import uet.gryffindor.game.engine.TimeCounter;
import uet.gryffindor.game.object.statics.Portal;
import uet.gryffindor.graphic.Animator;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class Bomber extends GameObject {
  SpriteTexture texture;
  private List<Bomb> listBombs;

  private Animator leftMove;
  private Animator rightMove;
  private Animator upMove;
  private Animator downMove;
  private Status currentStatus;

  private DoubleProperty speed;

  private boolean isBlocked = false;
  private Vector2D oldPosition;

  private int numberOfBombs; // tổng số bom có thể thả cùng lúc
  private int bombsDropped; // số bomb đã thả
  private long allowToDrop;// tạo độ trễ để tránh ấn liệt trong khoảng thời gian ngắn khi thả bomb

  @Override
  public void start() {
    Manager.INSTANCE.getGame().getCamera().setFocusOn(this);
    speed = new SimpleDoubleProperty(6f);
    texture = new SpriteTexture(Sprite.player_stand, this);

    double rate = 4;
    leftMove = new Animator(rate, Sprite.player_left).bindRate(speed);
    rightMove = new Animator(rate, Sprite.player_right).bindRate(speed);
    upMove = new Animator(rate, Sprite.player_up).bindRate(speed);
    downMove = new Animator(rate, Sprite.player_down).bindRate(speed);
    currentStatus = Status.STAND;

    orderedLayer = OrderedLayer.MIDGROUND;
    oldPosition = position.clone();

    numberOfBombs = 3; // Số bomb tối đa được thả cùng lúc = 1
    bombsDropped = 0; // ban đầu số bomb đã thả = 0

    listBombs = new ArrayList<>();
  }

  @Override
  public void update() {
    if (!isBlocked) {
      oldPosition = position.clone();
      move();
      removeBombs();

    }
  }

  private void move() {
    switch (Input.INSTANCE.getCode()) {
    case UP:
      this.position.y -= speed.get();
      texture.setSprite(upMove.getSprite());
      currentStatus = Status.UP;
      break;

    case DOWN:
      this.position.y += speed.get();
      texture.setSprite(downMove.getSprite());
      currentStatus = Status.DOWN;
      break;

    case RIGHT:
      this.position.x += speed.get();
      texture.setSprite(rightMove.getSprite());
      currentStatus = Status.RIGHT;
      break;

    case LEFT:
      this.position.x -= speed.get();
      texture.setSprite(leftMove.getSprite());
      currentStatus = Status.LEFT;
      break;

    case SPACE:
      if (System.currentTimeMillis() - allowToDrop >= 130) {
        bombsDropped++;
        addBomb();
        System.out.println("list bombs: " + listBombs.size());
      }
      break;

    default:
      switch (currentStatus) {
      case UP:
        texture.setSprite(upMove.getSpriteAt(0));
        break;
      case DOWN:
        texture.setSprite(downMove.getSpriteAt(0));
        break;
      case LEFT:
        texture.setSprite(leftMove.getSpriteAt(0));
        break;
      case RIGHT:
        texture.setSprite(rightMove.getSpriteAt(0));
        break;
      default:

        break;
      }
      break;
    }
  }

  @Override
  public void onCollisionEnter(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      // nếu bomber va chạm với vật thể tĩnh
      // khôi phục vị trí trước khi va chạm
      position = oldPosition.smooth(this.dimension.x);
      // gắn nhãn bị chặn
      isBlocked = true;
    }
  }

  @Override
  public void onCollisionStay(Collider that) {
    double area = collider.getDimension().x * collider.getDimension().y;
    // nếu bomber đi vào trung tâm portal (overlap area > 0.85)
    // chuyển sang map tiếp theo
    if (that.gameObject instanceof Portal && collider.getOverlapArea(that) / area > 0.85) {
      texture.setSprite(Sprite.player_stand);
      isBlocked = true;

      TimeCounter.callAfter(Manager.INSTANCE.getGame()::nextLevel, 1, TimeUnit.SECONDS);
    }
  }

  @Override
  public void onCollisionExit(Collider that) {
    if (that.gameObject instanceof Unmovable) {
      isBlocked = false;
    }
  }

  // nếu số bomb đã thả nhỏ hơn tổng số bomb
  // thì thả được
  public void addBomb() {
    if (bombsDropped <= numberOfBombs) {
      allowToDrop = System.currentTimeMillis();
      Bomb newBomb = new Bomb();
      newBomb.position.setValue(
          (int) ((this.position.x + Sprite.DEFAULT_SIZE / 2) / Sprite.DEFAULT_SIZE) * Sprite.DEFAULT_SIZE,
          (int) ((this.position.y + Sprite.DEFAULT_SIZE / 2) / Sprite.DEFAULT_SIZE) * Sprite.DEFAULT_SIZE);

      System.out.println("bomb-X: " + newBomb.position.x);
      System.out.println("bomb-Y: " + newBomb.position.y);

      listBombs.add(newBomb);
      GameObject.objects.add(newBomb);
    }
  }

  // Xóa bomb khỏi list bomb và objects.
  // Khởi tạo lại số bomb đã thả bombsDropped = 0.
  public void removeBombs() {
    for (int i = 0; i < listBombs.size(); i++) {
      if (listBombs.get(i).isExplored()) {
        listBombs.remove(listBombs.get(i));
        i--;
      }
    }
    if (listBombs.size() == 0) {
      bombsDropped = 0;
    }
  }

  @Override
  public Texture getTexture() {
    return this.texture;
  }

  public void setNumberOfBombs(int numberOfBombs) {
    this.numberOfBombs = numberOfBombs;
  }

}