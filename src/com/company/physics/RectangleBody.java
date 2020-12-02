package com.company.physics;

public abstract class RectangleBody extends Body {

    protected int width;
    protected int height;

    public abstract void update();

    public RectangleBody(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
