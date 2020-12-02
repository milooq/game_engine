package com.company.physics;

public abstract class CircleBody extends Body {

    protected int radius;

    public abstract void update();

    public CircleBody(int radius) {
        this.radius = radius;
    }
}
