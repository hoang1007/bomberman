package uet.gryffindor.game.object;

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

    numberOfBombs = 1;
    bombsDropped = 0;

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
      addBomb();
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

  public void addBomb() {
    // nếu số bomb đã thả nhỏ hơn tổng số bomb
    if (bombsDropped < numberOfBombs) {
      Bomb newBomb = new Bomb();
      newBomb.setPosition(this.position);
      listBombs.add(newBomb);
      objects.add(newBomb);
      bombsDropped++;
    }
  }

  public void removeBombs() {
    for (int i = 0; i < listBombs.size(); i++) {
      if (listBombs.get(i).isExplored()) {
        listBombs.remove(listBombs.get(i));
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