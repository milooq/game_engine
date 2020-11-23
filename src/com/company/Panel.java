package com.company;

import java.awt.*;

public class Panel extends Rectangle {


    public Panel(int width, int height, Vec2 position, Vec2 velocity) {
        super(width, height, Animator.CoordNormalToBad(position),Animator.VelocityConvert(velocity));
    }

    public void draw(Graphics g){
        g.drawRect(position.getX(), position.getY(), width, height);
    }

    void setVelocity(Vec2 v){
        this.velocity = Animator.VelocityConvert(v);
    }

    public void update(){
        position.add(velocity);
    }

    public void checkRightCollision(Ball b){
        int ypos = b.position.getY() + b.getRadius();
        int xpos = b.position.getX() + b.getRadius();
        if( ypos >= position.getY() && ypos <= (position.getY() + height) ){
            if(xpos >= position.getX()){
                b.invXVel();
                b.invYVel();
            }
        }
    }

    public void checkLeftCollision(Ball b){
        int yCenterPos = b.position.getY() + b.getRadius();
        int xCenterPos = b.position.getX() + b.getRadius();
        if( yCenterPos >= position.getY() && yCenterPos <= (position.getY() + height) ){
            if(xCenterPos <= position.getX() + width){
                b.invXVel();
                b.invYVel();
            }
        }
    }
}
