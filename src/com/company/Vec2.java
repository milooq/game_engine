package com.company;

public class Vec2 {

    private int x;
    private int y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void add(Vec2 b){
        this.x += b.x;
        this.y += b.y;
    }

    public static Vec2 sum(Vec2 a, Vec2 b){
        int x = a.x+ b.x;
        int y = a.y+ b.y;
        return new Vec2(x, y) ;
    }

    public void mul(int alpha){
        x*=alpha;
        y*=alpha;
    }

    public static Vec2 mul(Vec2 v, int alpha){
        v.mul(alpha);
        return v;
    }

    @Override
    public String toString(){
        return "(" + x +", "+y+")";
    }
}
