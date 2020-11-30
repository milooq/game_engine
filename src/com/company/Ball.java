package com.company;

import java.awt.*;

public class Ball extends Circle {
    public Ball(Vec2 position, int diameter, Vec2 velocity) {
        super(Animator.CoordNormalToBad(position), diameter, Animator.VelocityConvert(velocity));
    }

    public void draw(Graphics g){
        g.drawOval(position.getX() - radius/2, position.getY() - radius/2, radius, radius);
//        g.setColor(Color.RED);
//        g.drawOval(position.getX(), position.getY(), 1, 1);
//        g.drawOval(position.getX() - getRadius()/2, position.getY(), 2 , 2);
//        g.setColor(Color.BLACK);
    }

    void addVelocity(Vec2 v){
        this.velocity.add(v);
    }

    public void update(){
        position.add(velocity);
    }

    public Vec2 getPos(){
        return position;
    }

    public int getRadius(){
        return radius ;
    }

    public void invXVel(){
        velocity.setX(-velocity.getX());
    }

    public void invYVel(){
        velocity.setY(-velocity.getY());
    }

    public void jump(int jmp) { position.setX(position.getX() + jmp);}

    public void checkTopCollision() {
        if(position.getY() - radius/2 <= 30){
            invYVel();
        }
    }

    public void checkBottomCollision() {
        if(position.getY() + radius/2 >= Window.height){
            invYVel();
        }
    }

    public enum eOutOfBounce {NONE, LEFT, RIGHT};

    public eOutOfBounce OutOfBonce(){
        if((position.getX() + radius/2 > Window.width)){
            return eOutOfBounce.RIGHT;
        }
        if((position.getX() - radius/2 < 0)){
            return  eOutOfBounce.LEFT;
        }
        return eOutOfBounce.NONE;
    }
}
