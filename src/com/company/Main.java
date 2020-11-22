package com.company;

public class Main {

    public static void main(String[] args) {
        Vec2 a = new Vec2(1, 1);
        Vec2 b = new Vec2(0, 8);
        Vec2 c = Vec2.sum(a, b);
        System.out.println(a);
        System.out.println(c);
    }
}
