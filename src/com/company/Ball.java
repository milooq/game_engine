package com.company;

import java.awt.*;

public class Ball extends Circle {
    public Ball(Vec2 position, int radius, Vec2 velocity) {
        super(Animator.CoordNormalToBad(position), radius, Animator.VelocityConvert(velocity));
    }

    public void draw(Graphics g){
        g.drawOval(position.getX() + radius/2, position.getY() + radius/2, radius, radius);
        g.setColor(Color.RED);
        g.drawOval(position.getX() + radius, position.getY() + radius, 1, 1);
        g.setColor(Color.BLACK);
    }

    void setVelocity(Vec2 v){
        this.velocity = Animator.VelocityConvert(v);
    }

    public void update(){
        position.add(velocity);
    }

    public Vec2 getPos(){
        return position;
    }

    public int getRadius(){
        return radius;
    }

    public void invXVel(){
        velocity.setX(-velocity.getX());
    }

    public void invYVel(){
        velocity.setY(-velocity.getY());
    }

    public void checkTopCollision() {
        if(position.getY() - radius <= 0){
            invYVel();
        }
    }

    public void checkBottomCollision() {
        if(position.getY() + radius >= Window.height){
            invYVel();
        }
    }
}
