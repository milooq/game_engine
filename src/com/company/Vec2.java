package com.company;

import java.util.concurrent.ThreadLocalRandom;

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
    static public int randomDirection(){
        int _v = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        return (_v == 0) ? 1 : -1;
    }
    static public Vec2 randomVec(int min, int max){
        int x = ThreadLocalRandom.current().nextInt(min, max + 1);
        int y = ThreadLocalRandom.current().nextInt(min, max + 1);
        return new Vec2(x,y);
    }

    @Override
    public String toString(){
        return "(" + x +", "+y+")";
    }
}
