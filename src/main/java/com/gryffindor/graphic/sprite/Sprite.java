package com.gryffindor.graphic.sprite;

/**
 * Class này lưu trữ thông tin pixel của 1 sprite
 */
public class Sprite {

    public static Sprite player_stand = new Sprite(11, 5, 49, 72, SpriteSheet.player);

    public static int DEFAULT_SIZE = 35; // width = height của 1 sprite
    private double x; // tọa độ x so với ảnh cha
    private double y; // tọa độ y so với ảnh cha
    private double sWidth; // chiều rộng ảnh con
    private double sHeight;// chiều cao ảnh con
    private SpriteSheet spriteSheet; // lớp chứa ảnh cha

    public Sprite(double _x, double _y, double sWidth, double sHeight, SpriteSheet _spriteSheet) {
        this.x = _x;
        this.y = _y;
        this.sWidth = sWidth;
        this.sHeight = sHeight;
        this.spriteSheet = _spriteSheet;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return sWidth;
    }

    public double getHeight() {
        return sHeight;
    }

}
