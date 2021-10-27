package com.gryffindor.graphic.sprite;

import javafx.scene.image.Image;

/**
 * Class này lưu trữ ảnh gốc, load các thứ cần trong Sprite.java
 */
public class SpriteSheet {

    public static SpriteSheet player = new SpriteSheet("/img/player.png");

    private String path;
    private Image img;

    public SpriteSheet(String path) {
        this.path = path;
        img = new Image(getClass().getResource(path).toExternalForm());

    }

    public Image getImage() {
        return img;
    }

    public double getWidth() {
        return img.getWidth();
    }

    public double getHeight() {
        return img.getHeight();
    }

}
