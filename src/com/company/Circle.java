package com.company;

public class Circle {

    private Vec2 position;
    private int radius;

    public Circle(Vec2 position, int radius) {
        this.position = position;
        this.radius = radius;
    }

    public static  boolean isIntersect(Circle a, Circle b){
        int radius = a.radius + b.radius;
        radius *= radius;
        return radius > (a.position.getX() - b.position.getX())*(a.position.getX() - b.position.getX()) +
                        (a.position.getY() - b.position.getY())*(a.position.getY() - b.position.getY());
    }

    @Override
    public String toString(){ return "r: " + radius + "\n" + "pos: " + position.toString(); }
}
