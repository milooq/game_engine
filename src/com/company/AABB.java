package com.company;

public class AABB {

    private Vec2 min;
    private Vec2 max;

    public AABB(Vec2 min, Vec2 max) {
        this.min = min;
        this.max = max;
    }

    public AABB (int x1, int y1, int x2, int y2){
        this.min = new Vec2(x1 ,y1);
        this.max = new Vec2(x2, y2);
    }

    public static  boolean isIntersect(AABB a, AABB b){
        if (a.max.getX() < b.min.getX() || a.min.getX() > b.max.getX()) return false;
        if (a.max.getY() < b.min.getY() || a.min.getY() > b.max.getY()) return false;

        return true;
    }

    @Override
    public String toString(){
        return "min: " + min.toString() + "\n" + "max: " + max.toString();
    }
}
