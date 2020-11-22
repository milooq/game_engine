package com.company;

public class Main {

    public static void main(String[] args) {
        Vec2 a = new Vec2(0, 0);
        Vec2 b = new Vec2(5, 5);
        Vec2 c = Vec2.sum(a, b);
        AABB q = new AABB(a, b);
        AABB w = new AABB(5,5, 7,7);

        Circle s1 = new Circle(new Vec2(0,0), 3);
        Circle s2 = new Circle(new Vec2(2,2), 1);

        System.out.println(AABB.isIntersect(q,w));
        System.out.println(Circle.isIntersect(s1,s2));

        System.out.println(s1);
        System.out.println(q);
    }
}
