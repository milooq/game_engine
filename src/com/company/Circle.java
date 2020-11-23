package com.company;

public class Circle {

    protected Vec2 position;
    protected int radius;
    protected Vec2 velocity;

    public Circle(Vec2 position, int radius, Vec2 velocity) {
        this.position = position;
        this.radius = radius;
        this.velocity = velocity;
    }

    @Override
    public String toString(){ return "r: " + radius + "\n" + "pos: " + position.toString(); }
}
