package com.company;

public class Rectangle {

    protected int width;
    protected int height;
    protected Vec2 position;
    protected Vec2 velocity;

    public Rectangle(int width, int height, Vec2 position, Vec2 velocity) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.velocity = velocity;
    }
}
