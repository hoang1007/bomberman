package uet.gryffindor.game.object.statics.items;

import javafx.scene.paint.Color;
import uet.gryffindor.graphic.sprite.Sprite;
import uet.gryffindor.graphic.texture.RectTexture;
import uet.gryffindor.graphic.texture.SpriteTexture;
import uet.gryffindor.graphic.texture.Texture;

public class BombItem extends Item {
  private SpriteTexture texture;

  public BombItem() {
    start();

    long effectDuration = 100;
    super.setEffectDuration(effectDuration);
  }

  @Override
  public void start() {
    // texture = new RectTexture(Color.AZURE, this);
  }

  @Override
  public void update() {

  }

  @Override
  public Texture getTexture() {
    return this.texture;
  }

}
